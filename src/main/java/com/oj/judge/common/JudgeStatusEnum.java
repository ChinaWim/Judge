package com.oj.judge.common;


/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:58
 */
public enum JudgeStatusEnum {

// 0 编译中　1 ac 2 ce 3 pe 4 re 5 tle 6 me 7 wa　8队列中 9判题中

    COMPILE_SUCCESS(200, "COMPILE SUCCESS"),

    COMPILING(0, "COMPILING"),

    ACCEPTED(1, "AC"),

    COMPILE_ERROR(2, "CE"),

    PRESENTATION_ERROR(3, "PE"),

    RUNTIME_ERROR(4, "RE"),

    TIME_LIMIT_EXCEEDED(5, "TLE"),

    MEMORY_LIMIT_EXCEEDED(6, "MLE"),

    WRONG_ANSWER(7, "WA"),

    QUEUING(8, "QUEUING"),

    JUDGING(9, "JUDGING");


    JudgeStatusEnum(Integer status, String desc) {
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


    public static JudgeStatusEnum getStatusConst(Integer status) {
        JudgeStatusEnum[] statusConstArray = JudgeStatusEnum.values();
        for (JudgeStatusEnum statusConst : statusConstArray) {
            if (statusConst.getStatus().equals(status)) {
                return statusConst;
            }
        }
        return null;
    }


}
