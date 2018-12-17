package com.oj.judge.dao;


import com.oj.judge.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int addSignCount(Integer userId);

    int addSolutionCount(Integer userId);

    int addAcCount(Integer userId);

    int addPeCount(Integer userId);

    int addCeCount(Integer userId);

    int addTleCount(Integer userId);

    int addMeCount(Integer userId);

    int addReCount(Integer userId);

    int addWaCount(Integer userId);
}