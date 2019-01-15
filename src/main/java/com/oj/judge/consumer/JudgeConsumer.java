package com.oj.judge.consumer;

import com.oj.judge.common.StatusConst;
import com.oj.judge.entity.Problem;
import com.oj.judge.entity.ProblemResult;
import com.oj.judge.request.Code;
import com.oj.judge.service.JudgeService;
import com.oj.judge.service.ProblemService;
import com.oj.judge.service.UserService;
import com.oj.judge.utils.JsonUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:01
 */
@Component
public class JudgeConsumer {
    private static Logger logger = LoggerFactory.getLogger(JudgeConsumer.class);
    @Autowired
    private JudgeService judgeService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserService userService;

    @Autowired
    private Environment environment;

    private static DefaultMQPushConsumer pushConsumer;

    @PostConstruct
    private void initMQConsumer() throws MQClientException {
        pushConsumer = new DefaultMQPushConsumer(environment.getProperty("rocketmq.consumer.group"));
        pushConsumer.setConsumeThreadMax(Integer.parseInt(environment.getProperty("rocketmq.consumer.max-thread")));
        pushConsumer.setConsumeThreadMin(Integer.parseInt(environment.getProperty("rocketmq.consumer.min-thread")));
        pushConsumer.setMessageModel(MessageModel.CLUSTERING);
        pushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        pushConsumer.subscribe(environment.getProperty("rocketmq.topic"), "*");
    }

    @PreDestroy
    private void shutdownMQConsumer() {
        if (pushConsumer != null) {
            pushConsumer.shutdown();
        }
    }


    public void judge() {
        pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    try {
                        String body = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        Code code = JsonUtil.string2Obj(body, Code.class);
                        //处理重复消费问题
                        ProblemResult problemResult = problemService.getProblemResult(code.getProblemResultId());
                        if (!StatusConst.QUEUING.getStatus().equals(problemResult.getStatus())) {
                            continue;
                        }
                        //update compiling
                        problemResult.setStatus(StatusConst.COMPILING.getStatus());
                        problemService.updateProblemResult(problemResult);

                        int problemResultId = problemResult.getId();
                        //执行编译
                        List<String> resultList = judgeService.compile(code.getUserId(), code.getSourceCode(),
                                code.getType(), code.getProblemId());
                        String compileResult = resultList.get(0);
                        String userDirPath = resultList.get(1);

                        if (StatusConst.COMPILE_SUCCESS.getDesc().equals(compileResult)) {
                            //运行
                            judgeService.execute(code.getUserId(), code.getType(),
                                    code.getProblemId(), problemResultId, userDirPath);
                        } else {
                            //update compile error
                            problemService.updateProblemResultStatus(problemResultId, StatusConst.COMPILE_ERROR.getStatus());
                            //add count
                            problemService.addProblemCount(code.getProblemId(), StatusConst.COMPILE_ERROR);
                            userService.addCount(code.getUserId(), StatusConst.COMPILE_ERROR);
                        }
                    } catch (Exception e) {
                        logger.error("异常,{}", e.getMessage());
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        try {
            pushConsumer.start();
            logger.info("{},判题机已启动", Thread.currentThread().getName());
        } catch (MQClientException e) {
            logger.error("判题机启动异常,{}", e.getErrorMessage());
        }
    }

}
