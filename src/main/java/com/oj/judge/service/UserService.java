package com.oj.judge.service;

import com.oj.judge.response.ServerResponse;
import com.oj.judge.common.JudgeStatusEnum;

/**
 * @author m969130721@163.com
 * @date 18-12-17 下午1:44
 */
public interface UserService {

    ServerResponse addCount(Integer userId, JudgeStatusEnum statusConst);

    ServerResponse addSolutionCount(Integer userId,Integer problemId);

}
