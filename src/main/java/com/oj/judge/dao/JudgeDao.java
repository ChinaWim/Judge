package com.oj.judge.dao;


import com.oj.judge.utils.StreamUtil;
import com.oj.judge.utils.StringUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    //22060 20952 21572 21252 21724 21192 21480 22404 21932 20544
    // + 1024
    //21252 20228 21276 21496 21832 21764
    public static void main(String[] args) throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        Runtime runtime = Runtime.getRuntime();
        int total = (int)Runtime.getRuntime().totalMemory()/1024;
        int free = (int)Runtime.getRuntime().freeMemory()/1024;
        System.out.println(total - free);
        Process process = runtime.exec("java -classpath /home/ming/Desktop/ Main");
        System.out.println(process.info().user().get());
        String cmd = "cat   /proc/" + process.pid() + "/status | grep VmRSS | tr -cd '[0-9]' ";
       Thread thread = new Thread(() -> {
            Long max = 0L;
           while (process.isAlive()) {
               try {
                   Process countUsedMemoryProcess = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd});
                   String memory = StreamUtil.getOutPut(countUsedMemoryProcess.getInputStream());
                   long aLong = 0;
                   try{
                       aLong = Long.parseLong(memory);
                   }catch (Exception e){
                        continue;
                   }
                   if (aLong > max) {
                       max = aLong;
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
            System.out.println("limit:" + max);
        });
        thread.start();
        thread.setPriority(10);
        Thread.sleep(1500);
        String outPut = StreamUtil.getOutPut(process.getInputStream());
        System.out.println(outPut);

        /*
        thread.start();*/
//        System.out.println(getPid(process));
//        Thread.sleep(15000);
       /* Thread thread1 = new Thread(() -> {
          StreamUtil.setInPut(process.getOutputStream(), "/home/ming/Music/1000/input/1.txt");
            System.out.println("阻塞开始:");
            System.out.println(StreamUtil.getOutPut(process.getInputStream()));
            System.out.println("阻塞结束啦");

        });*/


//        thread1.start();
        int exit = 0;
        try {
            exit = process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(exit);
//        process.destroyForcibly();



    }

    public static int getPid(Process process) throws IllegalAccessException, NoSuchFieldException {
        Field field = process.getClass().getDeclaredField("pid");

        field.setAccessible(true);
        int pid = field.getInt(process);
//        pid += 1;
        return pid;
    }
}


