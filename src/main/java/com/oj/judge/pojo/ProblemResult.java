package com.oj.judge.pojo;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * @author m969130721@163.com
 * @date 18-11-29 下午3:21
 */
public class ProblemResult implements Serializable {
    private Integer id;

    private Integer problemId;

    private Integer userId;

    private TreeMap<Integer,TestCaseResult> resultTreeMap = new TreeMap<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public TreeMap<Integer, TestCaseResult> getResultTreeMap() {
        return resultTreeMap;
    }

    public void setResultTreeMap(TreeMap<Integer, TestCaseResult> resultTreeMap) {
        this.resultTreeMap = resultTreeMap;
    }

}
