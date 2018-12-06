package com.oj.judge.dao;

import com.oj.judge.utils.StreamUtil;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;

import java.awt.image.Kernel;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:05
 */
public class JudgeDao {

    public static void main(String[] args) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("java -classpath /home/ming/Desktop/ Main");
        System.out.println(StreamUtil.getOutPut(process.getInputStream()));
        Instant start = Instant.now();
        process.waitFor();
        Instant end = Instant.now();
        System.out.println(Duration.between(start,end).toMillis());
        /*new Thread(()->{
            try {
                System.out.println(StreamUtil.getOutPut(process.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
*/
//        process.destroyForcibly();
        System.out.println(process.exitValue());


    }

    public static int getPid(Process process) throws IllegalAccessException, NoSuchFieldException {
        Field field = process.getClass().getDeclaredField("pid");

        field.setAccessible(true);
        int pid = field.getInt(process);
//        pid += 1;
        return pid;
    }
}


