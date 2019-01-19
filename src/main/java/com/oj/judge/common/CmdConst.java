package com.oj.judge.common;

/**
 *
 * @author m969130721@163.com
 * @date 18-11-27 下午3:29
 */
public class CmdConst {


    /**
     * 获取编译脚本命令
     * @param type
     * @param dir
     * @return
     */
    public static String compileCmd(String type, String dir) {

        if (LanguageEnum.JAVA8.getType().equals(type)) {
            if (dir != null && !"".equals(dir)) {
                return "javac " + dir + "/Main.java";
            } else {
                return "javac Main.java";
            }
        } else if (LanguageEnum.C.getType().equals(type)) {
            if (dir != null && !"".equals(dir)) {
                return "gcc " + dir + "/Main.c -o " + dir + "/C.out";
            } else {
                return "gcc Main.c -o C.out";
            }
        } else if (LanguageEnum.CPP.getType().equals(type)) {
            if (dir != null && !"".equals(dir)) {
                return "g++ " + dir + "/Main.c++ -o " + dir + "/C++.out";
            } else {
                return "g++ Main.c++ -o C++.out";
            }
        } else {
            return null;
        }
    }


    /**
     * 获取运行脚本实例ProcessBuilder
     * @param type
     * @param dir
     * @return
     */
    public static ProcessBuilder executeCmd(String type, String dir) {
        ProcessBuilder builder = null;

        if (LanguageEnum.JAVA8.getType().equals(type)) {
            if (dir != null && !"".equals(dir)) {
                builder = new ProcessBuilder("java", "-classpath", dir, "Main");
            } else {
                builder = new ProcessBuilder("java", "Main");
            }
        } else if (LanguageEnum.C.getType().equals(type)) {
            if (dir != null && !"".equals(dir)) {
                builder = new ProcessBuilder(dir + "/C.out");
            } else {
                builder = new ProcessBuilder("C.out");
            }
        } else if (LanguageEnum.CPP.getType().equals(type)) {
            if (dir != null && !"".equals(dir)) {
                builder = new ProcessBuilder(dir + "/C++.out");
            } else {
                builder = new ProcessBuilder("C++.out");
            }
        } else if (LanguageEnum.PYTHON2.getType().equals(type)) {
            if (dir != null && !"".equals(dir)) {
                builder = new ProcessBuilder("python", dir + "/Main.py");
            } else {
                builder = new ProcessBuilder("python", "Main.py");
            }
        } else if (LanguageEnum.PYTHON3.getType().equals(type)) {
            if (dir != null && !"".equals(dir)) {
                builder = new ProcessBuilder("python3", dir, "/Main.py3");
            } else {
                builder = new ProcessBuilder("python3", "Main.py3");
            }
        }

        //设置自动清空流
        builder.redirectErrorStream(true);
        return builder;
    }


}
