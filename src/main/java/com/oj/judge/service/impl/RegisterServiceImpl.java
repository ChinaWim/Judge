package com.oj.judge.service.impl;

import com.oj.judge.dao.RegisterMapper;
import com.oj.judge.response.ServerResponse;
import com.oj.judge.service.RegisterService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author m969130721@163.com
 * @date 19-2-16 下午4:09
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterMapper registerMapper;


    @Override
    public ServerResponse addSolutionCountByProblemIdCompIdUserId(Integer problemId,Integer compId, Integer userId) {
        int effect = registerMapper.addSolutionCountByProblemIdCompIdUserId(problemId,compId, userId);
        return effect > 0 ? ServerResponse.success() : ServerResponse.fail();
    }



    @Override
    public ServerResponse addSubmitCountByCompIdUserId(Integer compId, Integer userId) {
        int effect = registerMapper.addSubmitCountByCompIdUserId(compId, userId);
        return effect > 0 ? ServerResponse.success() : ServerResponse.fail();
    }


    @Override
    public ServerResponse addScoreByCompIdUserId(Integer score,Integer compId, Integer userId) {
        int effect = registerMapper.addScoreByCompIdUserId(score,compId, userId);
        return effect > 0 ? ServerResponse.success() : ServerResponse.fail();
    }
}
