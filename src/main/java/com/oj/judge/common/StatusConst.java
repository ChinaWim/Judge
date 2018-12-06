package com.oj.judge.common;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:58
 */
public enum StatusConst {
    //0 wait 1 ac 2 ce 3 pe 4 tle 5 me 6 se 7 re 8 wa 200编译成功
    CS(200, "COMPILE_SUCCESS"),

    WAIT(0,"WAIT"),

    AC(1,"ACCEPTED"),

    CE(2,"COMPILE_ERROR"),

    PE(3,"PRESENTATION_ERROR"),

    TLE(4,"TIME_LIMIT_EXCEEDED"),

    ME(5,"MEMORY_LIMIT_EXCEEDED"),

    SE(6,"SYSTEM_ERROR"),

    RE(7,"RUNTIME_ERROR"),

    WA(8,"Wrong Answer");

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
