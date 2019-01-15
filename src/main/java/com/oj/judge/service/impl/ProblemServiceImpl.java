package com.oj.judge.service.impl;

import com.oj.judge.response.ServerResponse;
import com.oj.judge.common.StatusConst;
import com.oj.judge.dao.ProblemMapper;
import com.oj.judge.dao.ProblemResultMapper;
import com.oj.judge.dao.TestcaseResultMapper;
import com.oj.judge.entity.Problem;
import com.oj.judge.entity.ProblemResult;
import com.oj.judge.entity.TestcaseResult;
import com.oj.judge.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author m969130721@163.com
 * @date 18-12-17 下午1:44
 */
@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private ProblemResultMapper problemResultMapper;

    @Autowired
    private TestcaseResultMapper testcaseResultMapper;

    @Override
    public Problem getProblemById(Integer problemId) {
        return problemMapper.selectByPrimaryKey(problemId);
    }

    @Override
    public ProblemResult getProblemResult(Integer problemResultId) {
        return problemResultMapper.selectByPrimaryKey(problemResultId);
    }

    @Override
    public ServerResponse insertProblemResult(ProblemResult result) {
        result.setCreateTime(new Date());
        int effect = problemResultMapper.insertSelective(result);
        return effect > 0 ? ServerResponse.success() : ServerResponse.fail();
    }

    /**
     * 更新题目
     *
     * @param problem
     * @return
     */
    @Override
    public ServerResponse updateProblem(Problem problem) {
        int effect = problemMapper.updateByPrimaryKeySelective(problem);
        return effect > 0 ? ServerResponse.success() : ServerResponse.fail();
    }

    /**
     * 更新题目结果
     *
     * @return
     */
    @Override
    public ServerResponse updateProblemResult(ProblemResult problemResultId) {
        int effect = problemResultMapper.updateByPrimaryKeySelective(problemResultId);
        return effect > 0 ? ServerResponse.success() : ServerResponse.fail();
    }


    /**
     * 更新题目状态
     *
     * @return
     */
    @Override
    public ServerResponse updateProblemResultStatus(Integer problemResultId, Integer status) {
        int effect = problemResultMapper.updateProblemResultStatus(problemResultId, status);
        return effect > 0 ? ServerResponse.success() : ServerResponse.fail();
    }

    /**
     * 添加记录数
     *
     * @param problemId
     * @param statusConst
     * @return
     */
    @Override
    public ServerResponse addProblemCount(Integer problemId, StatusConst statusConst) {
        int effect = 0;
        if (Objects.equals(StatusConst.ACCEPTED, statusConst)) {
            effect = problemMapper.addAcCount(problemId);

        } else if (Objects.equals(StatusConst.TIME_LIMIT_EXCEEDED, statusConst)) {
            effect = problemMapper.addTleCount(problemId);

        } else if (Objects.equals(StatusConst.PRESENTATION_ERROR, statusConst)) {
            effect = problemMapper.addPeCount(problemId);

        } else if (Objects.equals(StatusConst.MEMORY_LIMIT_EXCEEDED, statusConst)) {
            effect = problemMapper.addMeCount(problemId);

        } else if (Objects.equals(StatusConst.COMPILE_ERROR, statusConst)) {
            effect = problemMapper.addCeCount(problemId);

        } else if (Objects.equals(StatusConst.RUNTIME_ERROR, statusConst)) {
            effect = problemMapper.addReCount(problemId);

        } else if (Objects.equals(StatusConst.WRONG_ANSWER, statusConst)) {
            effect = problemMapper.addWaCount(problemId);
        }

        return effect > 0 ? ServerResponse.success() : ServerResponse.fail();
    }

    @Override
    public ServerResponse insertBatchTestcaseResult(List<TestcaseResult> testcaseResultList) {
        int effect = testcaseResultMapper.insertBatch(testcaseResultList);
        return effect > 0 ? ServerResponse.success() : ServerResponse.fail();
    }

    @Override
    public int countProblemResult(Integer userId, Integer problemId,Integer status) {
        return problemResultMapper.countByUserIdProblemIdStatus(userId,problemId,status);
    }
}
