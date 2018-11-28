package com.oj.judge.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午3:37
 */
public class FileUtil {

    public static void saveFile(String content, String path) {
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] bytes = content.getBytes();
            outputStream.write(bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean deleteExeFile(String dir, String codeType) {
        switch (codeType) {
            case "java": {
                return deleteFile(dir + "/" + "Main.class");
            }
            default: {
                return false;
            }
        }
    }


    public static boolean deleteFile(String path) {
        File file = new File(path);
        boolean result = false;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
                if (subFile.isDirectory()) {
                    deleteFile(subFile.getPath());
                } else {
                    result = subFile.delete();
                }
            }
        }
        result = file.delete();
        return result;
    }


}
