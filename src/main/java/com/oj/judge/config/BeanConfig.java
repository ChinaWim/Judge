package com.oj.judge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author m969130721@163.com
 * @date 19-1-14 上午12:53
 */
@Configuration
public class BeanConfig {


    @Bean(name = "judgeExecutor")
    public Executor getJudgeExecutor() {
        ExecutorService executorService =
                new ThreadPoolExecutor(32, 40, 10,
                        TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(400));
        return executorService;
    }


}
