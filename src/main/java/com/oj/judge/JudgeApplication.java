package com.oj.judge;

import com.oj.judge.consumer.JudgeConsumer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@SpringBootApplication
@MapperScan("com.oj.judge.dao")
@EnableTransactionManagement
public class JudgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JudgeApplication.class, args);
    }
}
