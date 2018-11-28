package com.oj.judge.utils;

import java.io.*;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午7:39
 */
public class StreamUtil {


    public static String getOutPut(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length = 0;
        StringBuilder result = new StringBuilder("");
        if (inputStream != null) {
            while ((length = inputStream.read(buffer)) != -1) {
                result.append(new String(buffer, 0, length));
            }
        }
        inputStream.close();
        return result.toString();
    }


    public static void setInPut(OutputStream outputStream, String inputFilePath) throws IOException {
        
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        FileInputStream fileInputStream = new FileInputStream(inputFilePath);
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = fileInputStream.read(buffer)) != -1) {
            bufferedOutputStream.write(buffer, 0, length);
            bufferedOutputStream.flush();
        }
        fileInputStream.close();
        bufferedOutputStream.close();
    }
}
