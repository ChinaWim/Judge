package com.oj.judge.controller;

import com.oj.judge.common.StatusConst;
import com.oj.judge.pojo.ProblemResult;
import com.oj.judge.pojo.TestCaseResult;
import com.oj.judge.service.JudgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

    private static ExecutorService executorService =
            new ThreadPoolExecutor(32, 40, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(40));

    @RequestMapping("/submit")
    public Object submit(HttpSession session, String sourceCode, String type, Integer problemId) throws IOException {
        if (sourceCode == null || "".equals(sourceCode) || type == null || "".equals(type) || problemId == null) {
            return "输入不能为空";
        }
        String result = judgeService.compile(session, sourceCode, type, problemId);
        if (result.equals(StatusConst.COMPILE_SUCCESS.getDesc())) {
            executorService.execute(() -> {
                try {
                    ProblemResult problemResult = judgeService.execute(session, type, problemId);
                    problemResult.getResultTreeMap().entrySet().stream().forEach((entry -> {
                        TestCaseResult testCaseResult = entry.getValue();
                        logger.info("样例num: "+testCaseResult.getId());
                        logger.info("状态: "+testCaseResult.getStatus().getDesc());
                        logger.info("时间: "+testCaseResult.getTime());
                        logger.info("输出: "+testCaseResult.getOutput());
                        logger.info("-------------------------------------------------");
                    }));
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            });
        }
        return result;
    }


}
