package com.oj.judge.pojo;

/**todo
 * @author m969130721@163.com
 * @date 18-11-29 下午4:26
 */

public class Problem {
    private Integer id;

    private String question;

    private Long time;

    private Long memory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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
}
