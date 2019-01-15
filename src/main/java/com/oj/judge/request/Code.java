package com.oj.judge.request;

import java.io.Serializable;

/**
 * @author m969130721@163.com
 * @date 19-1-15 下午5:19
 */
public class Code implements Serializable {

    private static final long serialVersionUID = -201455056342317704L;

    private Integer problemResultId;

    private Integer userId;

    private Integer compId;

    private String sourceCode;

    private String type;

    private Integer problemId;


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

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getProblemResultId() {
        return problemResultId;
    }

    public void setProblemResultId(Integer problemResultId) {
        this.problemResultId = problemResultId;
    }

    @Override
    public String toString() {
        return "Code{" +
                "userId=" + userId +
                ", compId=" + compId +
                ", sourceCode='" + sourceCode + '\'' +
                ", type='" + type + '\'' +
                ", problemId=" + problemId +
                ", problemResultId=" + problemResultId +
                '}';
    }
}
