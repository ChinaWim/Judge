package com.oj.judge.service;

import com.oj.judge.entity.ProblemResult;

import java.util.List;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:04
 */
public interface JudgeService {


    String compile(ProblemResult problemResult);

    void execute(ProblemResult problemResult,String userDirPath);

}
