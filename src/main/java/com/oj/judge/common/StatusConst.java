package com.oj.judge.common;


/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:58
 */
public enum StatusConst {

//     0 编译中　1 ac 2 ce 3 pe 4 tle 5 me 6 se 7 re ８ wa　９队列中 10判题中

    COMPILE_SUCCESS(200, "COMPILE SUCCESS"),

    JUDGING(10, "JUDGING"),

    QUEUING(9, "QUEUING"),

    COMPILING(0, "COMPILING"),

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


    public static StatusConst getStatusConst(Integer status) {
        StatusConst[] statusConstArray = StatusConst.values();
        for (StatusConst statusConst : statusConstArray) {
            if (statusConst.getStatus().equals(status)) {
                return statusConst;
            }
        }
        return null;
    }


}
