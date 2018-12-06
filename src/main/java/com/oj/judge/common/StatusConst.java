package com.oj.judge.common;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:58
 */
public enum StatusConst {
    //0 wait 1 ac 2 ce 3 pe 4 tle 5 me 6 se 7 re 8 wa  COMPILE_SUCCESS 200编译成功
    COMPILE_SUCCESS(200, "COMPILE SUCCESS"),

    WAIT(0, "WAIT"),

    ACCEPTED(1, "AC"),

    COMPILE_ERROR(2, "CE"),

    PRESENTATION_ERROR(3, "PE"),

    TIME_LIMIT_EXCEEDED(4, "TLE"),

    MEMORY_LIMIT_EXCEEDED(5, "MLE"),

    SYSTEM_ERROR(6, "SE"),

    RUNTIME_ERROR(7, "RE"),

    WRONG_ANSWER(8, "WA");

    StatusConst(Integer status, String desc) {
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
