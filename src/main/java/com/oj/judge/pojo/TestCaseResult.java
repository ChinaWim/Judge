package com.oj.judge.pojo;

import com.oj.judge.common.StatusConst;

import java.io.Serializable;

/**
 * @author m969130721@163.com
 * @date 18-11-29 下午3:03
 */
public class TestCaseResult implements Serializable {
    private Integer id;

    private Long time;

    private Long memory;

    private StatusConst status;

    private String output;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public StatusConst getStatus() {
        return status;
    }

    public void setStatus(StatusConst status) {
        this.status = status;
    }
}
