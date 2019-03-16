package com.oj.judge.service;

import com.oj.judge.response.ServerResponse;
import com.oj.judge.common.JudgeStatusEnum;
import com.oj.judge.entity.Problem;
import com.oj.judge.entity.ProblemResult;
import com.oj.judge.entity.TestcaseResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author m969130721@163.com
 * @date 18-12-17 下午1:44
 */
public interface ProblemService {

    Problem getProblemById(Integer problemId);

    ProblemResult getProblemResultById(Integer problemResultId);

    ProblemResult getProblemResultByRunNum(String runNum);

    ServerResponse insertProblemResult(ProblemResult result);

    ServerResponse updateProblemById(Problem problem);

    ServerResponse updateProblemResultStatusById(Integer problemResultId, Integer status);

    ServerResponse updateProblemResultById(ProblemResult problemResult);

    ServerResponse addProblemCountById(Integer problemId, JudgeStatusEnum statusConst);

    ServerResponse insertBatchTestcaseResult(List<TestcaseResult> testcaseResultList);

    int countProblemResult(Integer userId,Integer problemId,Integer status);

    ServerResponse addCompScoreById(Integer compScore,Integer problemResultId);

    Integer getTotalScoreById( Integer userId, Integer compId);
}
