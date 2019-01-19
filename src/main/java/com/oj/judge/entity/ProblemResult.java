package com.oj.judge.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ProblemResult implements Serializable {

    private static final long serialVersionUID = 1198749854563595000L;

    private Integer id;

    private Integer userId;

    private Integer problemId;

    private Integer compId;

    private Integer status;

    private String type;

    private Long time;

    private Long memory;

    private String errorMsg;

    private String sourceCode;

    private Date createTime;

    private Date updateTime;

    private ConcurrentSkipListMap<Integer, TestcaseResult> resultMap = new ConcurrentSkipListMap<>();

    public ProblemResult(Integer id, Integer userId, Integer problemId, Integer compId, Integer status, String type, Long time, Long memory, String errorMsg, String sourceCode, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.problemId = problemId;
        this.compId = compId;
        this.status = status;
        this.type = type;
        this.time = time;
        this.memory = memory;
        this.errorMsg = errorMsg;
        this.sourceCode = sourceCode;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ProblemResult() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getMemory() {
        return memory;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode == null ? null : sourceCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Map<Integer, TestcaseResult> getResultMap() {
        return resultMap;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setResultMap(ConcurrentSkipListMap<Integer, TestcaseResult> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public String toString() {
        return "ProblemResult{" +
                "id=" + id +
                ", userId=" + userId +
                ", problemId=" + problemId +
                ", compId=" + compId +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", memory=" + memory +
                ", errorMsg='" + errorMsg + '\'' +
                ", sourceCode='" + sourceCode + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", resultMap=" + resultMap +
                '}';
    }
}