package com.oj.judge.rest;

import com.oj.judge.common.StatusConst;
import com.oj.judge.entity.ProblemResult;
import com.oj.judge.service.JudgeService;
import com.oj.judge.service.ProblemService;
import com.oj.judge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:01
 */
@RestController
@RequestMapping("/judge")
public class JudgeController {
    private static Logger logger = LoggerFactory.getLogger(JudgeController.class);
    @Autowired
    private JudgeService judgeService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserService userService;

    private static ExecutorService executorService =
            new ThreadPoolExecutor(32, 40, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(400));

    @RequestMapping("/submit")
    public Object submit(Integer userId, Integer compId, String sourceCode, String type, Integer problemId) throws IOException {
        if (sourceCode == null || "".equals(sourceCode) || type == null || "".equals(type) || problemId == null) {
            return "输入参数不能为空";
        }

        //add 编译中
        ProblemResult problemResult = new ProblemResult();
        problemResult.setUserId(userId);
        problemResult.setCompId(compId);
        problemResult.setType(type);
        problemResult.setProblemId(problemId);
        problemResult.setSourceCode(sourceCode);
        problemResult.setStatus(StatusConst.COMPILING.getStatus());
        problemService.insertProblemResult(problemResult);
        int problemResultId = problemResult.getId();

        //执行编译
        List<String> resultList = judgeService.compile(userId, sourceCode, type, problemId);
        String compileResult = resultList.get(0);
        String userDirPath = resultList.get(1);

        if (StatusConst.COMPILE_SUCCESS.getDesc().equals(compileResult)) {
            //update 队列中
            problemService.updateProblemResultStatus(problemResultId, StatusConst.QUEUING.getStatus());
            try {
                executorService.execute(() -> {
                    judgeService.execute(userId, type, problemId,problemResultId,userDirPath);
                });
            } catch (RejectedExecutionException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                return "服务器繁忙请稍等";
            }

        } else {
            //update compile error
            problemService.updateProblemResultStatus(problemResultId, StatusConst.COMPILE_ERROR.getStatus());
            //add count
            problemService.addProblemCount(problemId, StatusConst.COMPILE_ERROR);
            userService.addCount(userId,StatusConst.COMPILE_ERROR);
        }
        return compileResult;
    }


}
