package com.oj.judge.service;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:04
 */
public interface JudgeService {


    String compile(HttpSession session,String sourceCode,String type,Integer problemId) throws IOException;

    List<Map> execute(HttpSession session, String type, Integer problemId) throws IOException;

    String checkAnswer(Integer problemId);

}
