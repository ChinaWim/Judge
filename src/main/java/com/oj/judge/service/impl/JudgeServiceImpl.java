package com.oj.judge.service.impl;

import com.oj.judge.common.CmdConst;
import com.oj.judge.common.StatusConst;
import com.oj.judge.controller.JudgeController;
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
            return StatusConst.COMPILE_SUCCESS;
        } else {
            FileUtil.deleteFile(userDirPath);
        }
        return result;
    }

    @Override
    public List<Map> execute(HttpSession session, String type, Integer problemId) throws IOException {
        String username = session.getId();
        String problemDirPath = fileDirPath + "/" + problemId;
        String inputFileDirPath = problemDirPath + "/input";
        String userDirPath = problemDirPath + "/" + username;

        List<Map> resultList = new ArrayList<>();
        //执行输入，输出
        File inputFileDir = new File(inputFileDirPath);
        File[] inputFiles = inputFileDir.listFiles();
        CountDownLatch countDownLatch = new CountDownLatch(inputFiles.length);

       ExecutorService executorService = Executors.newFixedThreadPool(inputFiles.length);

        for (File file : inputFiles) {
            ProcessBuilder builder = CmdConst.executeCmd(type, userDirPath);
            Process process = builder.start();
            TestCaseInputTask testCaseInputTask = new TestCaseInputTask(file, process, resultList, countDownLatch);
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
        return resultList;
    }

    @Override
    public String checkAnswer(Integer problemId) {

        return "检查成功";
    }


}
