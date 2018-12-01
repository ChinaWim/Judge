package com.oj.judge.task;

import com.oj.judge.common.StatusConst;
import com.oj.judge.pojo.Problem;
import com.oj.judge.pojo.ProblemResult;
import com.oj.judge.pojo.TestCaseResult;
import com.oj.judge.utils.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.*;

/**
 * @author m969130721@163.com
 * @date 18-11-28 下午4:02
 */
public class TestCaseInputTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(TestCaseInputTask.class);

    private Problem problem;

    private Process process;

    private ProblemResult problemResult;

    private File inputFile;

    private String outputFileDirPath;

    private CountDownLatch countDownLatch;

    public TestCaseInputTask(Problem problem, File inputFile, String outputFileDirPath, Process process, ProblemResult problemResult, CountDownLatch countDownLatch) {
        this.process = process;
        this.outputFileDirPath = outputFileDirPath;
        this.inputFile = inputFile;
        this.problemResult = problemResult;
        this.countDownLatch = countDownLatch;
        this.problem = problem;

    }


    @Override
    public void run() {
        //输入文件
        OutputStream outputStream = process.getOutputStream();
        try {
            StreamUtil.setInPut(outputStream, inputFile.getPath());
        } catch (IOException e) {
            //源文件没有输入流
            logger.error("源文件没有输入流: " + e.getMessage());
        }


        TreeMap<Integer, TestCaseResult> resultMap = problemResult.getResultTreeMap();
        String fileName = inputFile.getName();
        Integer testCaseNum = Integer.parseInt(fileName.substring(0, fileName.lastIndexOf(".")));

        //输出信息的计算任务
        FutureTask<TestCaseResult> task = new FutureTask<>(new OutputTask(process));
        new Thread(task).start();

        //阻塞
        try {
            TestCaseResult testCaseResult = task.get(problem.getTime() + 500, TimeUnit.MILLISECONDS);
            testCaseResult.setId(testCaseNum);
            //checkAnswer
            File outputFile = new File(outputFileDirPath + "/" + fileName);
            checkAnswer(problem, outputFile, testCaseResult);

            resultMap.put(testCaseNum, testCaseResult);
        } catch (TimeoutException e) {
            //超时　update database
            process.destroyForcibly();
            logger.error(StatusConst.TIME_LIMIT_EXCEEDED.getDesc());
            TestCaseResult testCaseResult = new TestCaseResult();
            testCaseResult.setStatus(StatusConst.TIME_LIMIT_EXCEEDED);
            testCaseResult.setId(testCaseNum);
            resultMap.put(testCaseNum, testCaseResult);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            TestCaseResult testCaseResult = new TestCaseResult();
            testCaseResult.setStatus(StatusConst.SYSTEM_ERROR);
            testCaseResult.setId(testCaseNum);
            resultMap.put(testCaseNum, testCaseResult);
        } finally {
            countDownLatch.countDown();
        }
    }

    //todo update database
    private void checkAnswer(Problem problem, File outputFile, TestCaseResult testCaseResult) {
        try {
            if (problem.getTime().longValue() < testCaseResult.getTime().longValue()) {
                testCaseResult.setStatus(StatusConst.TIME_LIMIT_EXCEEDED);
                return;
            }
            /*if (problem.getMemory().longValue() < testCaseResult.getMemory().longValue()) {
                return;
            }*/
            //去掉空格 Tab 回车　其他是一样的说明格式错误,不一样说明答案错误
            String answerOutPut = StreamUtil.getOutPut(new FileInputStream(outputFile));
            String output = testCaseResult.getOutput();
            String formatOutput = formatString(output);
            String formatAnswerOutput = formatString(answerOutPut);

            if (answerOutPut.equals(output)) {
                testCaseResult.setStatus(StatusConst.ACCEPTED);
            } else {
                if (formatOutput.equals(formatAnswerOutput)) {
                    testCaseResult.setStatus(StatusConst.PRESENTATION_ERROR);
                } else {
                    testCaseResult.setStatus(StatusConst.WRONG_ANSWER);
                }
            }
            return;

        } catch (IOException e) {
            e.printStackTrace();
            testCaseResult.setStatus(StatusConst.SYSTEM_ERROR);
            logger.error(e.getMessage());
        }

    }


    private String formatString(String string) {
        string = string.replace(" ", "");
        string = string.replace("   ", "");
        string = string.replace("\n", "");
        string = string.replace("\r", "");
        return string;
    }

}
