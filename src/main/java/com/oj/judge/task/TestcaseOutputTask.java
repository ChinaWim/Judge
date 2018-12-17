package com.oj.judge.task;

import com.oj.judge.common.StatusConst;
import com.oj.judge.entity.TestcaseResult;
import com.oj.judge.utils.StreamUtil;

import java.io.OutputStream;
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
        String output = StreamUtil.getOutPut(process.getInputStream());
        Instant endTime = Instant.now();

        testcaseResult.setTime(Duration.between(startTime, endTime).toMillis());
        testcaseResult.setUserOutput(output);

        //等待进程执行结束 0代表正常退出
        process.waitFor();
        int exitValue = process.exitValue();
        //todo
        if (exitValue != 0 && exitValue != 137) {
            testcaseResult.setStatus(StatusConst.RUNTIME_ERROR.getStatus());
        }
        return testcaseResult;
    }

}


