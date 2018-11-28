package com.oj.judge.controller;

import com.oj.judge.common.StatusConst;
import com.oj.judge.service.JudgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    private JudgeService executeService;

    static private ExecutorService executorService =
            new ThreadPoolExecutor(30, 80, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(80));

    @RequestMapping("/submit")
    public Object submit(HttpSession session, String sourceCode, String type, Integer problemId) throws IOException {
        if (sourceCode == null || "".equals(sourceCode) || type == null || "".equals(type) || problemId == null) {
            return "输入不能为空";
        }
        String result = executeService.compile(session, sourceCode, type, problemId);
        if (result.equals(StatusConst.COMPILE_SUCCESS)) {
            Future<?> future = executorService.submit(() -> {
                try {
                    List<Map> resultList = executeService.execute(session, type, problemId);
                    for (int i = 0; i < resultList.size(); i++) {
                        logger.info("第 " + (i + 1) + "組");
                        Map<String,Object> map = resultList.get(i);
                        logger.info("时间为： "+map.get("time"));
                        logger.info("输出为： "+map.get("result"));
                        logger.info("-----------------------------------------------------");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            });
        }
        return result;
    }


}
