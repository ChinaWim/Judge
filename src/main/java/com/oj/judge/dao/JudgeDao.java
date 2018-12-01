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

    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException, InterruptedException {
     /*   Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("java -classpath /home/ming/Desktop/ Main");
        new Thread(() -> {
            int p = 100;
            while (p-- > 0) {
                try{
                    String cmd = "cat   /proc/" + getPid(process) + "/status | grep VmRSS";
                    Process ps = runtime.exec(new String[]{"/bin/sh", "-c", cmd});
                    String memory = StreamUtil.getOutPut(ps.getInputStream());
                    Pattern pattern = Pattern.compile("[0-9]* kB");
                    Matcher matcher = pattern.matcher(memory);
                    while (matcher.find()) {
                        System.out.println("----" + matcher.group());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        System.out.println(getPid(process));
        Thread.sleep(15000);
        new Thread(() -> {
            try {
                StreamUtil.setInPut(process.getOutputStream(), "/home/ming/Music/1004/input/1.txt");
                System.out.println(StreamUtil.getOutPut(process.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();*/

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();


        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage(); //椎内存使用情况


        long totalMemorySize = memoryUsage.getInit(); //初始的总内存


        long maxMemorySize = memoryUsage.getMax(); //最大可用内存


        long usedMemorySize = memoryUsage.getUsed(); //已使用的内存
        System.out.println(totalMemorySize);
        System.out.println(maxMemorySize);
        System.out.println(usedMemorySize);

    }

    public static int getPid(Process process) throws IllegalAccessException, NoSuchFieldException {
        Field field = process.getClass().getDeclaredField("pid");

        field.setAccessible(true);
        int pid = field.getInt(process);
//        pid += 1;
        return pid;
    }
}


