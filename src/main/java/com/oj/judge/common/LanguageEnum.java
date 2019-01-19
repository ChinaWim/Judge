package com.oj.judge.common;

/**
 * @author m969130721@163.com
 * @date 19-1-18 下午9:09
 */
public enum LanguageEnum {
    /**
     * Java8语言
     */
    JAVA8("Java8", "java"),
    /**
     * C语言
     */
    C("C", "c"),
    /**
     * Ｃ++语言
     */
    CPP("C++", "c++"),
    /**
     * Python2语言
     */
    PYTHON2("Python2", "py"),
    /**
     * Python3语言
     */
    PYTHON3("Python3", "py3");

    private String type;

    private String ext;

    LanguageEnum(String type, String ext) {
        this.type = type;
        this.ext = ext;
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


    public static String getExtByType(String type) {
        for (LanguageEnum languageEnum : LanguageEnum.values()) {
            if (languageEnum.getType().equals(type)) {
                return languageEnum.getExt();
            }
        }
        return null;
    }

}
