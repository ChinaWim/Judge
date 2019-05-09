package com.oj.judge.entity;

import java.io.Serializable;
import java.util.Date;

public class Problem implements Serializable {
    private Integer id;

    private String name;

    private String content;

    private String htmlContent;

    private String inputDesc;

    private String outputDesc;

    private String testcaseInput;

    private String testcaseOutput;

    private Integer level;

    private Integer submitCount;

    private Integer acCount;

    private Integer tleCount;

    private Integer peCount;

    private Integer meCount;

    private Integer reCount;

    private Integer ceCount;

    private Integer waCount;

    private Long time;

    private Long memory;

    private Integer flag;

    private Date createTime;

    private Date updateTime;

    public Problem(Integer id, String name, String content, String htmlContent,String inputDesc, String outputDesc, String testcaseInput, String testcaseOutput, Integer level, Integer submitCount, Integer acCount, Integer tleCount, Integer peCount, Integer meCount, Integer reCount, Integer ceCount, Integer waCount, Long time, Long memory, Integer flag, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.htmlContent = htmlContent;
        this.inputDesc = inputDesc;
        this.outputDesc = outputDesc;
        this.testcaseInput = testcaseInput;
        this.testcaseOutput = testcaseOutput;
        this.level = level;
        this.submitCount = submitCount;
        this.acCount = acCount;
        this.tleCount = tleCount;
        this.peCount = peCount;
        this.meCount = meCount;
        this.reCount = reCount;
        this.ceCount = ceCount;
        this.waCount = waCount;
        this.time = time;
        this.memory = memory;
        this.flag = flag;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Problem() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getInputDesc() {
        return inputDesc;
    }

    public void setInputDesc(String inputDesc) {
        this.inputDesc = inputDesc == null ? null : inputDesc.trim();
    }

    public String getOutputDesc() {
        return outputDesc;
    }

    public void setOutputDesc(String outputDesc) {
        this.outputDesc = outputDesc == null ? null : outputDesc.trim();
    }

    public String getTestcaseInput() {
        return testcaseInput;
    }

    public void setTestcaseInput(String testcaseInput) {
        this.testcaseInput = testcaseInput == null ? null : testcaseInput.trim();
    }

    public String getTestcaseOutput() {
        return testcaseOutput;
    }

    public void setTestcaseOutput(String testcaseOutput) {
        this.testcaseOutput = testcaseOutput == null ? null : testcaseOutput.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(Integer submitCount) {
        this.submitCount = submitCount;
    }

    public Integer getAcCount() {
        return acCount;
    }

    public void setAcCount(Integer acCount) {
        this.acCount = acCount;
    }

    public Integer getTleCount() {
        return tleCount;
    }

    public void setTleCount(Integer tleCount) {
        this.tleCount = tleCount;
    }

    public Integer getPeCount() {
        return peCount;
    }

    public void setPeCount(Integer peCount) {
        this.peCount = peCount;
    }

    public Integer getMeCount() {
        return meCount;
    }

    public void setMeCount(Integer meCount) {
        this.meCount = meCount;
    }

    public Integer getReCount() {
        return reCount;
    }

    public void setReCount(Integer reCount) {
        this.reCount = reCount;
    }

    public Integer getCeCount() {
        return ceCount;
    }

    public void setCeCount(Integer ceCount) {
        this.ceCount = ceCount;
    }

    public Integer getWaCount() {
        return waCount;
    }

    public void setWaCount(Integer waCount) {
        this.waCount = waCount;
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
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

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", inputDesc='" + inputDesc + '\'' +
                ", outputDesc='" + outputDesc + '\'' +
                ", testcaseInput='" + testcaseInput + '\'' +
                ", testcaseOutput='" + testcaseOutput + '\'' +
                ", level=" + level +
                ", submitCount=" + submitCount +
                ", acCount=" + acCount +
                ", tleCount=" + tleCount +
                ", peCount=" + peCount +
                ", meCount=" + meCount +
                ", reCount=" + reCount +
                ", ceCount=" + ceCount +
                ", waCount=" + waCount +
                ", time=" + time +
                ", memory=" + memory +
                ", flag=" + flag +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}