package com.oj.judge.jobs;

import com.oj.judge.common.JudgeStatusEnum;
import com.oj.judge.entity.Problem;
import com.oj.judge.entity.ProblemResult;
import com.oj.judge.entity.TestcaseResult;
import com.oj.judge.utils.StreamUtil;
import com.oj.judge.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Stream;

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
        if (problemResult.getResultMap() == null) {
            problemResult.setResultMap(new ConcurrentSkipListMap<>());
        }
        Map<Integer, TestcaseResult> resultMap = problemResult.getResultMap();
        String inputFileName = inputFile.getName();
        Integer testCaseNum = Integer.parseInt(inputFileName.substring(0, inputFileName.lastIndexOf(".")));

        //输入测试样例
        OutputStream outputStream = process.getOutputStream();
        StreamUtil.setInPut(outputStream, inputFile.getPath());

        //测试样例结果
        TestcaseResult testcaseResult = new TestcaseResult();
        testcaseResult.setCreateTime(new Date());
        testcaseResult.setNum(testCaseNum);
        testcaseResult.setProReId(problemResult.getId());

        //计算输出时间和内存任务
        FutureTask<TestcaseResult> task = new FutureTask<>(new TestcaseOutputTask(process, testcaseResult));
        new Thread(task).start();

        try {
            //计算时间，等待题目秒数 + 500毫秒
            testcaseResult = task.get(problem.getTime() + 500, TimeUnit.MILLISECONDS);
            if (!JudgeStatusEnum.RUNTIME_ERROR.getStatus().equals(testcaseResult.getStatus())) {
                //答案校验
                File outputFile = new File(outputFileDirPath + "/" + inputFileName);
                checkAnswer(problem, outputFile, testcaseResult);
            }
            resultMap.put(testCaseNum, testcaseResult);
        } catch (TimeoutException e) {
            logger.error(JudgeStatusEnum.TIME_LIMIT_EXCEEDED.getDesc());

            process.destroyForcibly();
            testcaseResult.setStatus(JudgeStatusEnum.TIME_LIMIT_EXCEEDED.getStatus());
            resultMap.put(testCaseNum, testcaseResult);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            testcaseResult.setStatus(JudgeStatusEnum.RUNTIME_ERROR.getStatus());
            resultMap.put(testCaseNum, testcaseResult);
        } finally {
            //关闭子进程
            Stream<ProcessHandle> descendants = process.descendants();
            descendants.forEach(p -> p.destroyForcibly());
            countDownLatch.countDown();
        }
    }


    /**
     *  tip: windows下文本以\r\n结尾，linux以\n结尾,mac \r 结尾
     * @param problem
     * @param outputFile
     * @param testcaseResult
     */
    private void checkAnswer(Problem problem, File outputFile, TestcaseResult testcaseResult) {
        try {
            if (problem.getTime() < testcaseResult.getTime()) {
                testcaseResult.setStatus(JudgeStatusEnum.TIME_LIMIT_EXCEEDED.getStatus());
                return;
            }
            if (problem.getMemory() < testcaseResult.getMemory()) {
                testcaseResult.setStatus(JudgeStatusEnum.MEMORY_LIMIT_EXCEEDED.getStatus());
                return;
            }
            String answerOutPut = StreamUtil.getOutPut(new FileInputStream(outputFile));
            String userOutput = testcaseResult.getUserOutput();

            //去除最右端多余字符
            answerOutPut = StringUtil.rTrim(answerOutPut);
            userOutput = StringUtil.rTrim(userOutput);

            String formatAnswerOutput = formatString(answerOutPut);
            String formatUserOutput = formatString(userOutput);

            if (answerOutPut.equals(userOutput)) {
                testcaseResult.setStatus(JudgeStatusEnum.ACCEPTED.getStatus());
            } else {
                if (formatUserOutput.equals(formatAnswerOutput)) {
                    testcaseResult.setStatus(JudgeStatusEnum.PRESENTATION_ERROR.getStatus());
                } else {
                    testcaseResult.setStatus(JudgeStatusEnum.WRONG_ANSWER.getStatus());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            testcaseResult.setStatus(JudgeStatusEnum.RUNTIME_ERROR.getStatus());
            logger.error(e.getMessage());
        }
    }


    private static String formatString(String string) {
        string = string.replace(" ", "");
        string = string.replace("\n", "");
        string = string.replace("\r", "");
        string = string.replace("\t", "");
        return string;
    }

}
