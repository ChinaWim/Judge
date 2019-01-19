package com.oj.judge.consumer;

import com.oj.judge.common.JudgeStatusEnum;
import com.oj.judge.entity.ProblemResult;
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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

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
        pushConsumer.setNamesrvAddr(environment.getProperty("rocketmq.nameserver"));
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
            logger.info("{},判题机关闭了",Thread.currentThread().getName());
        }
    }


    public void judge() {
        pushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt messageExt : list) {
                    try {
                        String body = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        logger.info("{},消费body为{}:",Thread.currentThread().getName(),body);
                        ProblemResult problemResult = JsonUtil.string2Obj(body, ProblemResult.class);

                        //处理重复消费问题
                        problemResult = problemService.getProblemResult(problemResult.getId());
                        if (!JudgeStatusEnum.QUEUING.getStatus().equals(problemResult.getStatus())) {
                            continue;
                        }
                        //update compiling
                        problemResult.setStatus(JudgeStatusEnum.COMPILING.getStatus());
                        problemService.updateProblemResult(problemResult);

                        //执行编译
                        List<String> resultList = judgeService.compile(problemResult.getUserId(), problemResult.getSourceCode(),
                                problemResult.getType(), problemResult.getProblemId());
                        String compileResult = resultList.get(0);
                        String userDirPath = resultList.get(1);

                        if (JudgeStatusEnum.COMPILE_SUCCESS.getDesc().equals(compileResult)) {
                            //运行
                            judgeService.execute(problemResult, userDirPath);
                        } else {
                            //update compile error
                            problemResult.setStatus(JudgeStatusEnum.COMPILE_ERROR.getStatus());
                            problemResult.setErrorMsg(compileResult);
                            problemService.updateProblemResult(problemResult);
                            //add count
                            problemService.addProblemCount(problemResult.getProblemId(), JudgeStatusEnum.COMPILE_ERROR);
                            userService.addCount(problemResult.getUserId(), JudgeStatusEnum.COMPILE_ERROR);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("RocketMQ消费异常,{}", e.getMessage());
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
