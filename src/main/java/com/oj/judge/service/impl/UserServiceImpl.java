package com.oj.judge.service.impl;

import com.oj.judge.common.ServerResult;
import com.oj.judge.common.StatusConst;
import com.oj.judge.dao.RoleMapper;
import com.oj.judge.dao.UserMapper;
import com.oj.judge.dao.UserRoleMapper;
import com.oj.judge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author m969130721@163.com
 * @date 18-12-17 下午1:45
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public ServerResult addCount(Integer userId, StatusConst statusConst) {
        int effect = 0;
        if (Objects.equals(StatusConst.ACCEPTED, statusConst)) {
            effect = userMapper.addAcCount(userId);

        } else if (Objects.equals(StatusConst.TIME_LIMIT_EXCEEDED, statusConst)) {
            effect = userMapper.addTleCount(userId);

        } else if (Objects.equals(StatusConst.PRESENTATION_ERROR, statusConst)) {
            effect = userMapper.addPeCount(userId);

        } else if (Objects.equals(StatusConst.MEMORY_LIMIT_EXCEEDED, statusConst)) {
            effect = userMapper.addMeCount(userId);

        } else if (Objects.equals(StatusConst.COMPILE_ERROR, statusConst)) {
            effect = userMapper.addCeCount(userId);

        } else if (Objects.equals(StatusConst.RUNTIME_ERROR, statusConst)) {
            effect = userMapper.addReCount(userId);

        } else if (Objects.equals(StatusConst.WRONG_ANSWER, statusConst)) {
            effect = userMapper.addWaCount(userId);
        }
        return effect > 0 ? ServerResult.success() : ServerResult.fail();

    }
    @Override
    public ServerResult addSolutionCount(Integer userId) {
        int effect = userMapper.addSolutionCount(userId);
        return effect > 0 ? ServerResult.success() : ServerResult.fail();
    }

    @Override
    public ServerResult addSignCount(Integer userId) {
        int effect = userMapper.addSignCount(userId);
        return effect > 0 ? ServerResult.success() : ServerResult.fail();
    }
}
