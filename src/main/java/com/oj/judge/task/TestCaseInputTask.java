package com.oj.judge.task;

import com.oj.judge.common.StatusConst;
import com.oj.judge.controller.JudgeController;
import com.oj.judge.utils.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author m969130721@163.com
 * @date 18-11-28 下午4:02
 */
public class TestCaseInputTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(TestCaseInputTask.class);

    private Process process;

    private List<Map> result;

    private File file;

    private CountDownLatch countDownLatch;

    public TestCaseInputTask(File file, Process process, List<Map> result, CountDownLatch countDownLatch) {
        this.process = process;
        this.file = file;
        this.result = result;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void run() {
        //输入文件
        OutputStream outputStream = process.getOutputStream();
        try {
            StreamUtil.setInPut(outputStream, file.getPath());
        } catch (IOException e) {
            //源文件没有输入流
            logger.error(e.getMessage());
        }
        //输出信息
        FutureTask<Map> task = new FutureTask<Map>(new OutputTask(process));
        Thread outPutThread = new Thread(task);
        outPutThread.start();
        Map resultMap = new HashMap();
        //阻塞
        try {
            //todo time limit
            resultMap = task.get(3, TimeUnit.SECONDS);
            result.add(resultMap);
        } catch (TimeoutException e) {
            e.printStackTrace();
            //超时　update database
            process.destroyForcibly();
            logger.error(StatusConst.TIME_LIMIT_EXCEEDED);
            resultMap.put("time", -1);
            resultMap.put("result", StatusConst.TIME_LIMIT_EXCEEDED);
            result.add(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put("time", -1);
            resultMap.put("result", StatusConst.SYSTEM_ERROR);
            result.add(resultMap);
        } finally {
            countDownLatch.countDown();
        }
    }
}
