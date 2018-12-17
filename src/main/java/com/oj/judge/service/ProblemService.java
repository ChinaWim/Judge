package com.oj.judge.service;

import com.oj.judge.common.ServerResult;
import com.oj.judge.common.StatusConst;
import com.oj.judge.dao.ProblemMapper;
import com.oj.judge.entity.Problem;
import com.oj.judge.entity.ProblemResult;
import com.oj.judge.entity.TestcaseResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author m969130721@163.com
 * @date 18-12-17 下午1:44
 */
public interface ProblemService {

    Problem getProblemById(Integer problemId);

    ProblemResult getProblemResult(Integer problemResultId);


    ServerResult insertProblemResult(ProblemResult result);

    ServerResult updateProblem(Problem problem);

    ServerResult updateProblemResultStatus(Integer problemResultId, Integer status);

    ServerResult updateProblemResult(ProblemResult problemResultId);

    ServerResult addProblemCount(Integer problemId, StatusConst statusConst);

    ServerResult insertBatchTestcaseResult(List<TestcaseResult> testcaseResultList);

    int countProblemResult(Integer userId,Integer problemId,Integer status);

}
