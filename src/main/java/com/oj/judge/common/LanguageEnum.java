package com.oj.judge.common;

import com.oj.judge.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author m969130721@163.com
 * @date 19-1-18 下午9:09
 */
public enum LanguageEnum {
    /**
     * Java8语言
     */
    JAVA8("Java8", "java",true),
    /**
     * C语言
     */
    C("C", "c",true),
    /**
     * Ｃ++语言
     */
    CPP("C++", "c++",true),
    /**
     * Python2语言
     */
    PYTHON2("Python2", "py",false),
    /**
     * Python3语言
     */
    PYTHON3("Python3", "py3",false);

    private String type;

    private String ext;

    private boolean requiredCompile;

    LanguageEnum(String type, String ext,boolean requiredCompile) {
        this.type = type;
        this.ext = ext;
        this.requiredCompile = requiredCompile;
    }

    public boolean isRequiredCompile() {
        return requiredCompile;
    }

    public void setRequiredCompile(boolean requiredCompile) {
        this.requiredCompile = requiredCompile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }



    public static LanguageEnum getEnumByType(String type) {
        for (LanguageEnum languageEnum : LanguageEnum.values()) {
            if (languageEnum.getType().equals(type)) {
                return languageEnum;
            }
        }
        return null;
    }

}
