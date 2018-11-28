package com.oj.judge.common;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:29
 */
public class CmdConst {

    public static String compileCmd(String type, String dir) {
        switch (type) {
            case "java": {
                if (dir != null && !"".equals(dir)) {
                    return "javac " + dir + "/Main.java";
                } else {
                    return "javac Main.java";
                }
            }
            case "c": {
                if (dir != null && !"".equals(dir)) {
                    return "gcc " + dir + "/Main.c -o " + dir + "/C.out";
                } else {
                    return "gcc Main.c -o C.out";
                }
            }
            case "c++": {
                if (dir != null && !"".equals(dir)) {
                    return "g++ " + dir + "/Main.c++ -o " + dir + "/C++.out";
                } else {
                    return "g++ Main.c++ -o C++.out";
                }
            }
            default: {
                return "";
            }
        }
    }

    public static ProcessBuilder executeCmd(String type, String dir) {
        ProcessBuilder builder = null;
        switch (type) {
            case "java": {
                if (dir != null && !"".equals(dir)) {
                    builder = new ProcessBuilder("java", "-classpath", dir, "Main");
                } else {
                    builder = new ProcessBuilder("java", "Main");
                }
                break;
            }
            case "c": {
                if (dir != null && !"".equals(dir)) {
                    builder = new ProcessBuilder(dir + "/C.out");
                } else {
                    builder = new ProcessBuilder("C.out");
                }
                break;
            }
            case "c++": {
                if (dir != null && !"".equals(dir)) {
                    builder = new ProcessBuilder(dir + "/C++.out");
                } else {
                    builder = new ProcessBuilder("C++.out");
                }
                break;
            }
            case "python": {
                if (dir != null && !"".equals(dir)) {
                    builder = new ProcessBuilder("java", "-classpath", dir, "Main");
                } else {
                    builder = new ProcessBuilder("java", "Main");
                }
                break;
            }
            default: {
                builder = null;
                break;
            }
        }
        //设置自动清空流
        builder.redirectErrorStream(true);
        return builder;
    }


}
