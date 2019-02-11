package com.oj.judge.service.impl;

import com.oj.judge.common.CmdConst;
import com.oj.judge.common.JudgeStatusEnum;
import com.oj.judge.common.LanguageEnum;
import com.oj.judge.entity.Problem;
import com.oj.judge.entity.ProblemResult;
import com.oj.judge.entity.TestcaseResult;
import com.oj.judge.service.JudgeService;
import com.oj.judge.service.ProblemService;
import com.oj.judge.service.UserService;
import com.oj.judge.jobs.TestcaseInputTask;
import com.oj.judge.utils.FileUtil;
import com.oj.judge.utils.StreamUtil;
import com.oj.judge.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Value("${file.server.testcase.dir}")
    private String fileServerTestcaseDir;


    private static Runtime runtime = Runtime.getRuntime();

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserService userService;


    //当一个用户提交同一个题目很多次答案时候，由于并发问题，可能在执行编译的时候，文件夹被另一个线程删除了，发出NotFoundException
    //执行程序同样存在这个问题
    //解决办法一：uuid文件，只删除文件，不删除文件夹
    //解决办法二：限制用户提交同一个题目５秒一次
    //解决办法三：文件夹＋时间戳，推荐 (已选择)

    @Override
    public List<String> compile(Integer userId, String sourceCode, String type, Integer problemId) {
        String problemDirPath = fileServerTestcaseDir + "/" + problemId;
        String userDirPath = problemDirPath + "/" + UUIDUtil.getUUIDByTime();
        String ext = LanguageEnum.getExtByType(type);
        FileUtil.saveFile(sourceCode, userDirPath + "/Main." + ext);

        List<String> result = new ArrayList<>();
        String compileErrorOutput = null;
        try {
            Process process = runtime.exec(CmdConst.compileCmd(type, userDirPath));
            compileErrorOutput = StreamUtil.getOutPut(process.getErrorStream());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            result.add(JudgeStatusEnum.RUNTIME_ERROR.getDesc());
            return result;
        }
        if (compileErrorOutput == null || "".equals(compileErrorOutput)) {
            result.add(JudgeStatusEnum.COMPILE_SUCCESS.getDesc());
            result.add(userDirPath);
        } else {
            result.add(compileErrorOutput);
            result.add(userDirPath);
            FileUtil.deleteFile(userDirPath);
        }
        return result;
    }

    @Override
    public void execute(ProblemResult problemResult, String userDirPath) {
        //update 判题中
        problemService.updateProblemResultStatus(problemResult.getId(), JudgeStatusEnum.JUDGING.getStatus());

        String problemDirPath = fileServerTestcaseDir + "/" + problemResult.getProblemId();
        String inputFileDirPath = problemDirPath + "/input";
        String outputFileDirPath = problemDirPath + "/output";

        Problem problem = problemService.getProblemById(problemResult.getProblemId());
        //AC题目增加的点数
        Integer ratingCount = problem.getLevel() * 10;
        Integer goldCount = problem.getLevel();
        //创建一个新的 题目输出结果实例 去接收testcase结果
//        ProblemResult problemResult = new ProblemResult();
//        problemResult.setId(problemResultId);

        //执行输入和输出
        File inputFileDir = new File(inputFileDirPath);
        File[] inputFiles = inputFileDir.listFiles();
        CountDownLatch countDownLatch = new CountDownLatch(inputFiles.length);
        ExecutorService executorService = Executors.newFixedThreadPool(inputFiles.length);
        try {
            for (File inputFile : inputFiles) {
                ProcessBuilder builder = CmdConst.executeCmd(problemResult.getType(), userDirPath);
                Process process = builder.start();
                TestcaseInputTask testcaseInputTask = new TestcaseInputTask(problem, inputFile,
                        outputFileDirPath, process, problemResult, countDownLatch);
                executorService.execute(testcaseInputTask);
            }
            executorService.shutdown();
            countDownLatch.await();
            //汇总统计
            long maxTime = -1;
            long maxMemory = -1;
            Integer status = null;
            Integer acCount = 0;
            List<TestcaseResult> testcaseResultList = new ArrayList<>();
            Set<Map.Entry<Integer, TestcaseResult>> entrySet = problemResult.getResultMap().entrySet();
            for (Map.Entry<Integer, TestcaseResult> entry : entrySet) {
                Integer testcaseNum = entry.getKey();
                TestcaseResult testcaseResult = entry.getValue();

                if (testcaseResult.getMemory() != null && testcaseResult.getMemory() > maxMemory) {
                    maxMemory = testcaseResult.getMemory();
                }
                if (testcaseResult.getTime() != null && testcaseResult.getTime() > maxTime) {
                    maxTime = testcaseResult.getTime();
                }
                if (JudgeStatusEnum.RUNTIME_ERROR.getStatus().equals(testcaseResult.getStatus())) {
                    status = JudgeStatusEnum.RUNTIME_ERROR.getStatus();
                }
                if (status == null && JudgeStatusEnum.PRESENTATION_ERROR.getStatus().equals(testcaseResult.getStatus())) {
                    status = JudgeStatusEnum.PRESENTATION_ERROR.getStatus();
                }
                if (status == null && JudgeStatusEnum.WRONG_ANSWER.getStatus().equals(testcaseResult.getStatus())) {
                    status = JudgeStatusEnum.WRONG_ANSWER.getStatus();
                }
                if (status == null && JudgeStatusEnum.TIME_LIMIT_EXCEEDED.getStatus().equals(testcaseResult.getStatus())) {
                    status = JudgeStatusEnum.TIME_LIMIT_EXCEEDED.getStatus();
                }
                if (status == null && JudgeStatusEnum.MEMORY_LIMIT_EXCEEDED.getStatus().equals(testcaseResult.getStatus())) {
                    status = JudgeStatusEnum.MEMORY_LIMIT_EXCEEDED.getStatus();
                }
                if (JudgeStatusEnum.ACCEPTED.getStatus().equals(testcaseResult.getStatus())) {
                    acCount++;
                }
                testcaseResultList.add(testcaseResult);
            }

            if (acCount == testcaseResultList.size()) {
                status = JudgeStatusEnum.ACCEPTED.getStatus();
                //user solutionCount
                userService.addSolutionCountAndGoldCountAndRating(problemResult.getUserId(),
                        problemResult.getProblemId(), goldCount, ratingCount);
            }

            //insertBatch testcase
            problemService.insertBatchTestcaseResult(testcaseResultList);
            //update problemResult
            problemResult.setMemory(maxMemory);
            problemResult.setTime(maxTime);
            problemResult.setStatus(status);
            problemService.updateProblemResult(problemResult);

            //add count
            problemService.addProblemCount(problemResult.getProblemId(), JudgeStatusEnum.getStatusConst(status));
            userService.addCount(problemResult.getUserId(), JudgeStatusEnum.getStatusConst(status));


        } catch (Exception e) {
            //执行脚本错误或闭锁中断Exception update database
            problemService.updateProblemResultStatus(problemResult.getId(), JudgeStatusEnum.RUNTIME_ERROR.getStatus());
            logger.error(e.getMessage());
        } finally {
            FileUtil.deleteFile(userDirPath);
        }

    }


}
