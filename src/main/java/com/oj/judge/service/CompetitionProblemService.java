package com.oj.judge.service;

import com.oj.judge.response.ServerResponse;

import java.util.List;

/**
 * @author m969130721@163.com
 * @date 19-2-16 下午3:11
 */
public interface CompetitionProblemService {

    Integer getScoreByCompIdProblemId(Integer compId,Integer problemId);

    ServerResponse addAcCountByCompIdProblemId(Integer compId,Integer problemId);

    ServerResponse addSubmitCountByCompIdProblemId(Integer compId,Integer problemId);

}
