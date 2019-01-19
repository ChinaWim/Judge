package com.oj.judge.jobs;

import com.oj.judge.common.JudgeStatusEnum;
import com.oj.judge.entity.TestcaseResult;
import com.oj.judge.utils.StreamUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;

/**
 * 程序输出
 *
 * @author m969130721@163.com
 * @date 18-11-27 下午7:20
 */
public class TestcaseOutputTask implements Callable<TestcaseResult> {

    private Process process;

    private TestcaseResult testcaseResult;

    public TestcaseOutputTask(Process process, TestcaseResult testcaseResult) {
        this.process = process;
        this.testcaseResult = testcaseResult;
    }

    @Override
    public TestcaseResult call() throws Exception {

        Instant startTime = Instant.now();
        //阻塞
        String output = StreamUtil.getOutPut(process.getInputStream());
        Instant endTime = Instant.now();
        testcaseResult.setTime(Duration.between(startTime, endTime).toMillis());
        testcaseResult.setUserOutput(output);

        //等待进程执行结束 0代表正常退出
        int exitValue = process.waitFor();
        if (exitValue != 0 && testcaseResult.getStatus() == null) {
            testcaseResult.setStatus(JudgeStatusEnum.RUNTIME_ERROR.getStatus());
        }
        return testcaseResult;
    }

}


