package com.oj.judge.service;

import com.oj.judge.response.ServerResponse;

/**
 * @author m969130721@163.com
 * @date 19-2-16 下午4:09
 */
public interface RegisterService {

    ServerResponse addSolutionCountByProblemIdCompIdUserId(Integer problemId,Integer compId, Integer userId);

    ServerResponse addSubmitCountByCompIdUserId(Integer compId, Integer userId);

    ServerResponse updateScore(Integer score,Integer compId, Integer userId);

}
