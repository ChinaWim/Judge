package com.oj.judge.service.impl;

import com.oj.judge.common.CmdConst;
import com.oj.judge.common.JudgeStatusEnum;
import com.oj.judge.common.LanguageEnum;
import com.oj.judge.entity.Problem;
import com.oj.judge.entity.ProblemResult;
import com.oj.judge.entity.Register;
import com.oj.judge.entity.TestcaseResult;
import com.oj.judge.response.ServerResponse;
import com.oj.judge.service.*;
import com.oj.judge.jobs.TestcaseInputTask;
import com.oj.judge.utils.FileUtil;
import com.oj.judge.utils.StreamUtil;
import com.oj.judge.utils.StringUtil;
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

    @Autowired
    private RegisterService registerService;

    @Autowired
    private CompetitionProblemService competitionProblemService;


    //当一个用户提交同一个题目很多次答案时候，由于并发问题，可能在执行编译的时候，文件夹被另一个线程删除了，发出NotFoundException
    //执行程序同样存在这个问题
    //解决办法一：uuid文件，只删除文件，不删除文件夹
    //解决办法二：限制用户提交同一个题目５秒一次
    //解决办法三：文件夹＋时间戳，推荐 (已选择)

    @Override
    public String compile(ProblemResult problemResult) {
        String problemDirPath = fileServerTestcaseDir + "/" + problemResult.getProblemId();
        String userDirPath = problemDirPath + "/" + UUIDUtil.createByTime();
        String ext = LanguageEnum.getExtByType(problemResult.getType());
        FileUtil.saveFile(problemResult.getSourceCode(), userDirPath + "/Main." + ext);


        if (problemResult.getCompId() != null) {
            //add  submitCount
            registerService.addSubmitCountByCompIdUserId(problemResult.getCompId(), problemResult.getUserId());
            competitionProblemService.addSubmitCountByCompIdProblemId(problemResult.getCompId(), problemResult.getProblemId());
        }

        String compileErrorOutput = null;
        try {
            Process process = runtime.exec(CmdConst.compileCmd(problemResult.getType(), userDirPath));
            compileErrorOutput = StreamUtil.getOutPut(process.getErrorStream());

        } catch (IOException e) {
            e.printStackTrace();
            String message = "".equals(e.getMessage()) ? "IOException" : e.getMessage();
            logger.error(message);
            compileErrorOutput = message;
        }

        if (compileErrorOutput == null || "".equals(compileErrorOutput)) {
            return userDirPath;
        } else {
            //update compile error
            compileErrorOutput = StringUtil.getLimitLengthByString(compileErrorOutput, 1000);
            problemResult.setStatus(JudgeStatusEnum.COMPILE_ERROR.getStatus());
            problemResult.setErrorMsg(compileErrorOutput);
            problemService.updateProblemResultById(problemResult);

            //add count
            problemService.addProblemCountById(problemResult.getProblemId(), JudgeStatusEnum.COMPILE_ERROR);
            userService.addCount(problemResult.getUserId(), JudgeStatusEnum.COMPILE_ERROR);
            FileUtil.deleteFile(userDirPath);
            return null;
        }

    }

    @Override
    public void execute(ProblemResult problemResult, String userDirPath) {
        //update 判题中
        problemService.updateProblemResultStatusById(problemResult.getId(), JudgeStatusEnum.JUDGING.getStatus());

        String problemDirPath = fileServerTestcaseDir + "/" + problemResult.getProblemId();
        String inputFileDirPath = problemDirPath + "/input";
        String outputFileDirPath = problemDirPath + "/output";

        Problem problem = problemService.getProblemById(problemResult.getProblemId());
        //AC题目增加的点数
        Integer ratingCount = problem.getLevel() * 10;
        Integer goldCount = problem.getLevel();

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

            // ac condition
            if (acCount == testcaseResultList.size()) {
                status = JudgeStatusEnum.ACCEPTED.getStatus();
                //user solutionCount
                userService.addSolutionCountAndGoldCountAndRating(problemResult.getUserId(),
                        problemResult.getProblemId(), goldCount, ratingCount);
                // competitionProblem register
                if (problemResult.getCompId() != null) {
                    competitionProblemService.addAcCountByCompIdProblemId(problemResult.getCompId(), problemResult.getProblemId());
                    //register add count
                    registerService.addAcCountByCompIdUserId(problemResult.getCompId(),problemResult.getUserId());
                    registerService.addSolutionCountByProblemIdCompIdUserId(problemResult.getProblemId(), problemResult.getCompId(), problemResult.getUserId());
                }
            }


            //record competition score
           if (problemResult.getCompId() != null) {
                Integer score = competitionProblemService.getScoreByCompIdProblemId(problemResult.getCompId(), problemResult.getProblemId());
                if (score != null) {
                    score = (int) ((acCount * 1.0 / testcaseResultList.size()) * score);
                    problemService.addCompScoreById(score,problemResult.getId());

                    //更新总分
                    Integer totalScore = problemService.getTotalScoreById(problemResult.getUserId(), problemResult.getCompId());
                    registerService.updateScore(totalScore,problemResult.getCompId(),problemResult.getUserId());
                }
            }

            //insertBatch testcase
            problemService.insertBatchTestcaseResult(testcaseResultList);
            //update problemResult
            problemResult.setMemory(maxMemory);
            problemResult.setTime(maxTime);
            problemResult.setStatus(status);
            problemService.updateProblemResultById(problemResult);

            //add count
            problemService.addProblemCountById(problemResult.getProblemId(), JudgeStatusEnum.getStatusConst(status));
            userService.addCount(problemResult.getUserId(), JudgeStatusEnum.getStatusConst(status));


        } catch (Exception e) {
            //执行脚本错误或闭锁中断Exception update database
            String message = StringUtil.getLimitLengthByString(e.getMessage(), 1000);
            problemResult.setErrorMsg(message);
            problemService.updateProblemResultStatusById(problemResult.getId(), JudgeStatusEnum.RUNTIME_ERROR.getStatus());

            logger.error("执行脚本错误或闭锁中断Exception", e);
        } finally {
            FileUtil.deleteFile(userDirPath);
        }

    }


}
