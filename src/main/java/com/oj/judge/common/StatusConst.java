package com.oj.judge.common;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:58
 */
public enum StatusConst {
    COMPILE_SUCCESS(0, "编译成功"),

    COMPILE_ERROR(-1,"编译错误"),

    ACCEPTED(1,"通过"),

    PRESENTATION_ERROR(-2,"格式错误"),

    WRONG_ANSWER(-3,"答案不对"),

    TIME_LIMIT_EXCEEDED(-4,"超时"),

    MEMORY_LIMIT_EXCEEDED(-5,"内存超过限制"),

    RUNTIME_ERROR(-6,"运行时错误"),

    OUTPUT_LIMIT_EXCEEDED(-7,"输出文件超限"),

    SYSTEM_ERROR(-8,"系统错误");

    StatusConst(Integer status,String desc) {
        this.status = status;
        this.desc = desc;
    }
    private Integer status;

    private String desc;

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
