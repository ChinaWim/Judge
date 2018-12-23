package com.oj.judge.entity;

import java.io.Serializable;
import java.util.Date;

public class SystemConfig implements Serializable {
    private Integer id;

    private String sysKey;

    private String sysValue1;

    private String sysValue2;

    private String content;

    private Date createTime;

    private Date updateTime;

    public SystemConfig(Integer id, String sysKey, String sysValue1, String sysValue2, String content, Date createTime, Date updateTime) {
        this.id = id;
        this.sysKey = sysKey;
        this.sysValue1 = sysValue1;
        this.sysValue2 = sysValue2;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public SystemConfig() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSysKey() {
        return sysKey;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey == null ? null : sysKey.trim();
    }

    public String getSysValue1() {
        return sysValue1;
    }

    public void setSysValue1(String sysValue1) {
        this.sysValue1 = sysValue1 == null ? null : sysValue1.trim();
    }

    public String getSysValue2() {
        return sysValue2;
    }

    public void setSysValue2(String sysValue2) {
        this.sysValue2 = sysValue2 == null ? null : sysValue2.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
        return "SystemConfig{" +
                "id=" + id +
                ", sysKey='" + sysKey + '\'' +
                ", sysValue1='" + sysValue1 + '\'' +
                ", sysValue2='" + sysValue2 + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}