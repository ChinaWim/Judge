package com.oj.judge.service.impl;

import com.oj.judge.common.CmdConst;
import com.oj.judge.common.StatusConst;
import com.oj.judge.controller.JudgeController;
import com.oj.judge.pojo.Problem;
import com.oj.judge.pojo.ProblemResult;
import com.oj.judge.service.JudgeService;
import com.oj.judge.task.OutputTask;
import com.oj.judge.task.TestCaseInputTask;
import com.oj.judge.utils.FileUtil;
import com.oj.judge.utils.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:04
 */
@Service
public class JudgeServiceImpl implements JudgeService {


    private static Logger logger = LoggerFactory.getLogger(JudgeServiceImpl.class);

    @Value("${file.dir}")
    private String fileDirPath;

    private static Runtime runtime = Runtime.getRuntime();


    //当一个用户提交同一个题目很多次答案时候，由于并发问题，可能在执行编译的时候，文件夹被另一个线程删除了，发出NotFoundException
    //执行程序同样存在这个问题
    //解决办法一：uuid文件，只删除文件，不删除文件夹
    //解决办法二：限制用户提交同一个题目５秒一次
    @Override
    public String compile(HttpSession session, String sourceCode, String type, Integer problemId) throws IOException {
        String username = session.getId();
        String problemDirPath = fileDirPath + "/" + problemId;
        String userDirPath = problemDirPath + "/" + username;

        FileUtil.saveFile(sourceCode, userDirPath + "/Main." + type);
        Process process = null;
        process = runtime.exec(CmdConst.compileCmd(type, userDirPath));
        String result = StreamUtil.getOutPut(process.getErrorStream());
        //todo update database
        if (result == null || "".equals(result)) {
            return StatusConst.COMPILE_SUCCESS.getDesc();
        } else {
            FileUtil.deleteFile(userDirPath);
        }
        return result;
    }

    @Override
    public ProblemResult execute(HttpSession session, String type, Integer problemId) throws IOException {
        String username = session.getId();
        String problemDirPath = fileDirPath + "/" + problemId;
        String inputFileDirPath = problemDirPath + "/input";
        String outputFileDirPath = problemDirPath + "/output";
        String userDirPath = problemDirPath + "/" + username;
        //demo
        Problem problem = new Problem();
        problem.setMemory(1000L);
        problem.setTime(3000L);
        problem.setQuestion("1+1");


        ProblemResult result = new ProblemResult();
        //执行输入和输出
        File inputFileDir = new File(inputFileDirPath);
        File[] inputFiles = inputFileDir.listFiles();

        CountDownLatch countDownLatch = new CountDownLatch(inputFiles.length);
        ExecutorService executorService = Executors.newFixedThreadPool(inputFiles.length);
        for (File inputFile : inputFiles) {
            ProcessBuilder builder = CmdConst.executeCmd(type, userDirPath);
            Process process = builder.start();
            TestCaseInputTask testCaseInputTask = new TestCaseInputTask(problem,inputFile,outputFileDirPath, process, result, countDownLatch);
            executorService.execute(testCaseInputTask);
        }
        try {
            executorService.shutdown();
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        FileUtil.deleteFile(userDirPath);
        return result;
    }


}
