package com.oj.judge.entity;

import java.io.Serializable;
import java.util.Date;

public class TestcaseResult implements Serializable {
    private Integer id;

    private Integer proReId;

    private Integer num;

    private String input;

    private String output;

    private String userOutput;

    private Long time;

    private Long memory;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public TestcaseResult(Integer id, Integer proReId, Integer num, String input, String output, String userOutput, Long time, Long memory, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.proReId = proReId;
        this.num = num;
        this.input = input;
        this.output = output;
        this.userOutput = userOutput;
        this.time = time;
        this.memory = memory;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public TestcaseResult() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProReId() {
        return proReId;
    }

    public void setProReId(Integer proReId) {
        this.proReId = proReId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input == null ? null : input.trim();
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output == null ? null : output.trim();
    }

    public String getUserOutput() {
        return userOutput;
    }

    public void setUserOutput(String userOutput) {
        this.userOutput = userOutput == null ? null : userOutput.trim();
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "TestcaseResult{" +
                "id=" + id +
                ", proReId=" + proReId +
                ", num=" + num +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", userOutput='" + userOutput + '\'' +
                ", time=" + time +
                ", memory=" + memory +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}