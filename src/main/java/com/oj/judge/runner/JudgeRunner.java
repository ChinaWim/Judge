package com.oj.judge.runner;

import com.oj.judge.consumer.JudgeConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author m969130721@163.com
 * @date 19-1-15 下午5:51
 */
@Component
@Order(1)
public class JudgeRunner implements ApplicationRunner {

    @Autowired
    private JudgeConsumer judgeConsumer;

    @Override
    public void run(ApplicationArguments args){
        judgeConsumer.judge();
    }
}
