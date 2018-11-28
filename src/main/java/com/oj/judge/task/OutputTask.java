package com.oj.judge.task;

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
public class OutputTask implements Callable {

    private Process process;

    public OutputTask(Process process) {
        this.process = process;

    }

    @Override
    public Map call() throws Exception {
        HashMap<String,Object> result = new HashMap<>();
        Instant startTime = Instant.now();
        String outPut = StreamUtil.getOutPut(process.getInputStream());
        Instant endTime = Instant.now();
        result.put("time",Duration.between(startTime,endTime).toMillis());
        result.put("result",outPut);
        return result;
    }

}


