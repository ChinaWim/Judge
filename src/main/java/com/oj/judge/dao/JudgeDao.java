package com.oj.judge.dao;

import java.io.*;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.*;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:05
 */
public class JudgeDao {

    private static ExecutorService executorService =
            new ThreadPoolExecutor(2, 40, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(40));


    public static void main(String[] args) {
        JudgeDao dao = new JudgeDao();
        dao.executorService.execute(() -> {
            System.out.println("hello world");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        dao.executorService.shutdown();
        dao.executorService.execute(() -> {
            System.out.println("hello world");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        dao.executorService.shutdown();
       /* dao.executorService.execute(() -> {
            System.out.println("hello world");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });*/

    }

}
