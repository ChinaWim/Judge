package com.oj.judge.utils;

import org.springframework.util.DigestUtils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Ming on 2018/2/9.
 */
public class UUIDUtil {

    /**
     * 盐值
     */
    private static final String salt = "９_asASb)/.12$%$!*553}LDSse.";

    /**
     *获取API UUID 用到MAC、时间戳、随机数等因素
     * @return
     */
    public static String createByAPI36() {
        return UUID.randomUUID().toString();
    }

    /**
     *获取当前时间数字字符串
     * @return
     */
    public static Long createByTime() {
        Long currentTime = System.currentTimeMillis();
        return currentTime + new Random().nextInt(100);
    }

    /**
     * 获取MD5
     * @param string
     * @return
     */
    public static String createMD5(String string) {
        string = salt + string;
        return DigestUtils.md5DigestAsHex(string.getBytes());
    }

}
