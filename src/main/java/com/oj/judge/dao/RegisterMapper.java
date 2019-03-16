package com.oj.judge.dao;


import com.oj.judge.entity.Register;
import org.apache.ibatis.annotations.Param;

public interface RegisterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Register record);

    int insertSelective(Register record);

    Register selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Register record);

    int updateByPrimaryKey(Register record);

    int addSolutionCountByProblemIdCompIdUserId(@Param("problemId")Integer problemId,@Param("compId") Integer compId, @Param("userId") Integer userId);

    int addSubmitCountByCompIdUserId(@Param("compId") Integer compId, @Param("userId") Integer userId);

    int updateScoreByCompIdUserId(@Param("score") Integer score,@Param("compId") Integer compId, @Param("userId") Integer userId);

    int addAcCountByCompIdUserId(@Param("compId") Integer compId, @Param("userId") Integer userId);
}