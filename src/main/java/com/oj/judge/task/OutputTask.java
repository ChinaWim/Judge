package com.oj.judge.task;

import com.oj.judge.common.StatusConst;
import com.oj.judge.pojo.TestCaseResult;
import com.oj.judge.utils.StreamUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**程序输出
 * @author m969130721@163.com
 * @date 18-11-27 下午7:20
 */
public class OutputTask implements Callable<TestCaseResult> {

    private Process process;

    public OutputTask(Process process) {
        this.process = process;

    }

    @Override
    public TestCaseResult call() throws Exception {

        Instant startTime = Instant.now();
        String output = StreamUtil.getOutPut(process.getInputStream());
        Instant endTime = Instant.now();

        TestCaseResult testCaseResult = new TestCaseResult();
        testCaseResult.setTime(Duration.between(startTime,endTime).toMillis());
        testCaseResult.setOutput(output);

        //等待进程执行结束 0代表正常退出
        process.waitFor();
        int exitValue = process.exitValue();
        if (exitValue != 0) {
            testCaseResult.setStatus(StatusConst.RE);
        }
        return testCaseResult;
    }

}


