package com.oj.judge.dao;


import com.oj.judge.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int addSolutionCountAndGoldCountAndRating(@Param("userId") Integer userId, @Param("problemId")Integer problemId,
                                              @Param("goldCount")Integer goldCount,@Param("ratingCount")Integer ratingCount);

    int addAcCount(Integer userId);

    int addPeCount(Integer userId);

    int addCeCount(Integer userId);

    int addTleCount(Integer userId);

    int addMeCount(Integer userId);

    int addReCount(Integer userId);

    int addWaCount(Integer userId);
}