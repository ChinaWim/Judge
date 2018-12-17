package com.oj.judge.dao;


import com.oj.judge.entity.TestcaseResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestcaseResultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestcaseResult record);

    int insertSelective(TestcaseResult record);

    TestcaseResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TestcaseResult record);

    int updateByPrimaryKey(TestcaseResult record);

    int insertBatch(@Param("testcaseResultList") List<TestcaseResult> testcaseResultList);
}