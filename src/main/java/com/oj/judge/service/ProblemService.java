package com.oj.judge.service;

import com.oj.judge.response.ServerResponse;
import com.oj.judge.common.StatusConst;
import com.oj.judge.entity.Problem;
import com.oj.judge.entity.ProblemResult;
import com.oj.judge.entity.TestcaseResult;

import java.util.List;

/**
 * @author m969130721@163.com
 * @date 18-12-17 下午1:44
 */
public interface ProblemService {

    Problem getProblemById(Integer problemId);

    ProblemResult getProblemResult(Integer problemResultId);

    ServerResponse insertProblemResult(ProblemResult result);

    ServerResponse updateProblem(Problem problem);

    ServerResponse updateProblemResultStatus(Integer problemResultId, Integer status);

    ServerResponse updateProblemResult(ProblemResult problemResultId);

    ServerResponse addProblemCount(Integer problemId, StatusConst statusConst);

    ServerResponse insertBatchTestcaseResult(List<TestcaseResult> testcaseResultList);

    int countProblemResult(Integer userId,Integer problemId,Integer status);

}
