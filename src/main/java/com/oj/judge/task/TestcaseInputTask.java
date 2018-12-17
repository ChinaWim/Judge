package com.oj.judge.task;

import com.oj.judge.common.StatusConst;
import com.oj.judge.entity.Problem;
import com.oj.judge.entity.ProblemResult;
import com.oj.judge.entity.TestcaseResult;
import com.oj.judge.utils.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.TreeMap;
import java.util.concurrent.*;

/**
 * 测试样例输入任务
 *
 * @author m969130721@163.com
 * @date 18-11-28 下午4:02
 */
public class TestcaseInputTask implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(TestcaseInputTask.class);

    private Problem problem;

    private Process process;

    private ProblemResult problemResult;

    private File inputFile;

    private String outputFileDirPath;

    private CountDownLatch countDownLatch;

    public TestcaseInputTask(Problem problem, File inputFile, String outputFileDirPath, Process process, ProblemResult problemResult, CountDownLatch countDownLatch) {
        this.process = process;
        this.outputFileDirPath = outputFileDirPath;
        this.inputFile = inputFile;
        this.problemResult = problemResult;
        this.countDownLatch = countDownLatch;
        this.problem = problem;
    }


    @Override
    public void run() {
        TreeMap<Integer, TestcaseResult> resultMap = problemResult.getResultTreeMap();
        String fileName = inputFile.getName();
        Integer testCaseNum = Integer.parseInt(fileName.substring(0, fileName.lastIndexOf(".")));

        //输入测试样例
        OutputStream outputStream = process.getOutputStream();
        String input = StreamUtil.setInPut(outputStream, inputFile.getPath());

        //测试样例结果
        TestcaseResult testcaseResult = new TestcaseResult();
        testcaseResult.setNum(testCaseNum);
        testcaseResult.setProReId(problemResult.getId());
        testcaseResult.setInput(input);

        //计算输出时间任务
        FutureTask<TestcaseResult> task = new FutureTask<>(new TestcaseOutputTask(process, testcaseResult));
        new Thread(task).start();

        try {
            //计算时间，等待题目秒数 + 600毫秒
            testcaseResult = task.get(problem.getTime() + 600, TimeUnit.MILLISECONDS);
            //答案校验
            File outputFile = new File(outputFileDirPath + "/" + fileName);
            checkAnswer(problem, outputFile, testcaseResult);

            resultMap.put(testCaseNum, testcaseResult);
        } catch (TimeoutException e) {
            process.destroyForcibly();
            logger.error(StatusConst.TIME_LIMIT_EXCEEDED.getDesc());

            testcaseResult.setStatus(StatusConst.TIME_LIMIT_EXCEEDED.getStatus());
            resultMap.put(testCaseNum, testcaseResult);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());

            testcaseResult.setStatus(StatusConst.SYSTEM_ERROR.getStatus());
            resultMap.put(testCaseNum, testcaseResult);
        } finally {
            countDownLatch.countDown();
        }
    }

    private void checkAnswer(Problem problem, File outputFile, TestcaseResult testcaseResult) {
        try {
            String answerOutPut = StreamUtil.getOutPut(new FileInputStream(outputFile));
            String userOutput = testcaseResult.getUserOutput();
            String formatOutput = formatString(userOutput);
            String formatAnswerOutput = formatString(answerOutPut);
            testcaseResult.setOutput(answerOutPut);

            if (StatusConst.RUNTIME_ERROR.getStatus().equals(testcaseResult.getStatus())) {
                return;
            }
            if (problem.getTime().longValue() < testcaseResult.getTime().longValue()) {
                testcaseResult.setStatus(StatusConst.TIME_LIMIT_EXCEEDED.getStatus());
                return;
            }
            /*if (problem.getMemory().longValue() < testcaseResult.getMemory().longValue()) {
                return;
            }*/
            if (answerOutPut.equals(userOutput)) {
                testcaseResult.setStatus(StatusConst.ACCEPTED.getStatus());
            } else {
                if (formatOutput.equals(formatAnswerOutput)) {
                    testcaseResult.setStatus(StatusConst.PRESENTATION_ERROR.getStatus());
                } else {
                    testcaseResult.setStatus(StatusConst.WRONG_ANSWER.getStatus());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            testcaseResult.setStatus(StatusConst.SYSTEM_ERROR.getStatus());
            logger.error(e.toString());
        }
    }


    private String formatString(String string) {
        string = string.replace(" ", "");
        string = string.replace("   ", "");
        string = string.replace("\n", "");
        string = string.replace("\r", "");
        string = string.replace("\t", "");
        return string;
    }

}
