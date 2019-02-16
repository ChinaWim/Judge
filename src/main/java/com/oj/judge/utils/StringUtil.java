package com.oj.judge.utils;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.Arrays;

/**
 * @author m969130721@163.com
 * @date 19-2-13 上午12:25
 */
public class StringUtil {

    public static String lTrim(String original) {
        if (original == null || "".equals(original)) {
            return original;
        }
        char[] originalChars = original.toCharArray();
        if (originalChars[0] > 32) {
            return original;
        }
        int srcPos = 0;
        for (int i = 0; i < originalChars.length; i++) {
            if (originalChars[i] > 32) {
                break;
            }
            srcPos = i;
        }
        int length = originalChars.length - (srcPos + 1);
        char[] newChars = new char[length];
        System.arraycopy(originalChars, srcPos + 1, newChars, 0, length);
        return new String(newChars);
    }

    public static String rTrim(String original) {
        if (original == null || "".equals(original)) {
            return original;
        }
        char[] originalChars = original.toCharArray();
        if (originalChars[original.length() - 1] > 32) {
            return original;
        }
        int count = 0;
        for (int i = originalChars.length - 1; i >= 0  ; i--) {
            if (originalChars[i] > 32) {
                break;
            }
            count++;
        }
        int length = originalChars.length - count;
        char[] newChars = new char[length];
        System.arraycopy(originalChars, 0, newChars, 0, length);
        return new String(newChars);
    }


    /**
     * 限制长度
     * @param string
     * @param maxLength
     * @return
     */
    public static  String getLimitLengthByString(String string,int maxLength){
        if(string == null) {
            return null;
        }
        int length = string.length() > maxLength ? maxLength : string.length();
        return string.substring(0,length);
    }

}
