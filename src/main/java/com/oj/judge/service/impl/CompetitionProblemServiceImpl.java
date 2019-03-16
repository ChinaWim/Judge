package com.oj.judge.service.impl;

import com.oj.judge.dao.CompetitionProblemMapper;
import com.oj.judge.response.ServerResponse;
import com.oj.judge.service.CompetitionProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author m969130721@163.com
 * @date 19-2-16 下午4:50
 */
@Service
public class CompetitionProblemServiceImpl implements CompetitionProblemService {
    @Autowired
    private CompetitionProblemMapper competitionProblemMapper;

    @Override
    public Integer getScoreByCompIdProblemId(Integer compId, Integer problemId) {
        if(compId == null || problemId == null){
            return null;
        }
        return competitionProblemMapper.getScoreByCompIdProblemId(compId, problemId);
    }

    @Override
    public ServerResponse addAcCountByCompIdProblemId(Integer compId, Integer problemId) {
        if(compId == null || problemId == null){
            return ServerResponse.fail();
        }
        int effect = competitionProblemMapper.addAcCountByCompIdProblemId(compId, problemId);
        return effect > 0 ? ServerResponse.success() : ServerResponse.fail();
    }

    @Override
    public ServerResponse addSubmitCountByCompIdProblemId(Integer compId, Integer problemId) {
        if(compId == null || problemId == null){
            return ServerResponse.fail();
        }
        int effect = competitionProblemMapper.addSubmitCountByCompIdProblemId(compId, problemId);
        return effect > 0 ? ServerResponse.success() : ServerResponse.fail();
    }
}
