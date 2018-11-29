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
        TestCaseResult result = new TestCaseResult();
        result.setTime(Duration.between(startTime,endTime).toMillis());
        result.setOutput(output);
        return result;
    }

}


