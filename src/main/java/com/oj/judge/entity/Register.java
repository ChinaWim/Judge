package com.oj.judge.entity;

import java.io.Serializable;
import java.util.Date;

public class Register implements Serializable {

    private static final long serialVersionUID = 401827306166537768L;

    private Integer id;

    private Integer userId;

    private Integer compId;

    private Integer solutionCount;

    private Integer submitCount;

    private Integer registerCount;

    private Integer score;

    private Date createTime;

    private Date updateTime;

    public Register(Integer id, Integer userId, Integer compId, Integer solutionCount, Integer submitCount, Integer score, Date createTime, Date updateTime, Integer registerCount) {
        this.id = id;
        this.userId = userId;
        this.compId = compId;
        this.solutionCount = solutionCount;
        this.submitCount = submitCount;
        this.score = score;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.registerCount = registerCount;
    }

    public Register() {
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

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public Integer getSolutionCount() {
        return solutionCount;
    }

    public void setSolutionCount(Integer solutionCount) {
        this.solutionCount = solutionCount;
    }

    public Integer getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(Integer submitCount) {
        this.submitCount = submitCount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getRegisterCount() {
        return registerCount;
    }

    public void setRegisterCount(Integer registerCount) {
        this.registerCount = registerCount;
    }

    @Override
    public String toString() {
        return "Register{" +
                "id=" + id +
                ", userId=" + userId +
                ", compId=" + compId +
                ", solutionCount=" + solutionCount +
                ", submitCount=" + submitCount +
                ", registerCount=" + registerCount +
                ", score=" + score +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}