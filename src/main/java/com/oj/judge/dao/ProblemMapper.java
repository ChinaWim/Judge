package com.oj.judge.dao;


import com.oj.judge.entity.Problem;

public interface ProblemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Problem record);

    int insertSelective(Problem record);

    Problem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Problem record);

    int updateByPrimaryKey(Problem record);

    int addAcCount(Integer problemId);

    int addPeCount(Integer problemId);

    int addCeCount(Integer problemId);

    int addTleCount(Integer problemId);

    int addMeCount(Integer problemId);

    int addReCount(Integer problemId);

    int addWaCount(Integer problemId);



}