package com.oj.judge.entity;

import java.io.Serializable;
import java.util.Date;

public class CompetitionProblem implements Serializable {

    private static final long serialVersionUID = 8879907260073536736L;

    private Integer id;

    private Integer compId;

    private Integer problemId;

    private Integer score;

    private Integer acCount;

    private Integer submitCount;

    private Date createTime;

    private Date updateTime;

    public CompetitionProblem(Integer id, Integer compId, Integer problemId, Integer score, Integer acCount, Integer submitCount, Date createTime, Date updateTime) {
        this.id = id;
        this.compId = compId;
        this.problemId = problemId;
        this.score = score;
        this.acCount = acCount;
        this.submitCount = submitCount;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public CompetitionProblem() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getAcCount() {
        return acCount;
    }

    public void setAcCount(Integer acCount) {
        this.acCount = acCount;
    }

    public Integer getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(Integer submitCount) {
        this.submitCount = submitCount;
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


    @Override
    public String toString() {
        return "CompetitionProblem{" +
                "id=" + id +
                ", compId=" + compId +
                ", problemId=" + problemId +
                ", score=" + score +
                ", acCount=" + acCount +
                ", submitCount=" + submitCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}