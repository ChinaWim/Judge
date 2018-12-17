package com.oj.judge.dao;

import com.oj.judge.utils.StreamUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 24140(3) 25572(3) 27252(3) 27640 //误差大概在5mb之内
 * 27100(3) 27308　27164　//前
 * 30272 (6)
 * <p>
 * 无睡眠
 *
 * @author m969130721@163.com
 * @date 18-11-27 下午3:05
 */
public class JudgeDao {
    public static void main(String[] args) throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("java -classpath /home/ming/Desktop/ Main");

       Thread thread = new Thread(() -> {
            Long max = 0L;
            while (process.isAlive()) {
                try {
                    String cmd = "cat   /proc/" + getPid(process) + "/status | grep VmRSS";
                    Process ps = runtime.exec(new String[]{"/bin/sh", "-c", cmd});
                    String memory = StreamUtil.getOutPut(ps.getInputStream());
                    Pattern pattern = Pattern.compile("[0-9]* kB");
                    Matcher matcher = pattern.matcher(memory);
                    while (matcher.find()) {
                        String result = matcher.group();
                        result = result.substring(0, result.length() - 3);
                        long aLong = Long.parseLong(result);
                        if (aLong > max) {
                            max = aLong;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("limit:" + max);
        });
        thread.setPriority(10);
        thread.start();
//        System.out.println(getPid(process));
//        Thread.sleep(15000);
        Thread thread1 = new Thread(() -> {
//          StreamUtil.setInPut(process.getOutputStream(), "/home/ming/Music/1004/input/1.txt");
            System.out.println(StreamUtil.getOutPut(process.getInputStream()));



        });
        thread1.setPriority(2);
        thread1.start();
        Thread.sleep(2000);
//        process.destroyForcibly();
        runtime.exec("kill -9 "+process.pid());
        System.out.println(process.isAlive()+"b");
        Stream<ProcessHandle> descendants = process.descendants();
        descendants.forEach(p ->{
            p.destroyForcibly();
        });
        System.out.println(process.isAlive());
        Stream<ProcessHandle> descendants1 = process.descendants();

    }

    public static int getPid(Process process) throws IllegalAccessException, NoSuchFieldException {
        Field field = process.getClass().getDeclaredField("pid");

        field.setAccessible(true);
        int pid = field.getInt(process);
//        pid += 1;
        return pid;
    }
}


