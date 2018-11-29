package com.oj.judge.service;

import com.oj.judge.pojo.ProblemResult;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:04
 */
public interface JudgeService {


    String compile(HttpSession session,String sourceCode,String type,Integer problemId) throws IOException;

    ProblemResult execute(HttpSession session, String type, Integer problemId) throws IOException;

}
