package com.oj.judge.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author m969130721@163.com
 * @date 18-11-27 下午7:39
 */
public class StreamUtil {
    private static Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    public static String getOutPut(InputStream inputStream) {
        StringBuilder result = new StringBuilder("");
        try {
            byte[] buffer = new byte[1024];
            int length = 0;
            if (inputStream != null) {
                while ((length = inputStream.read(buffer)) != -1) {
                    result.append(new String(buffer, 0, length));
                }
            }
        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.toString());
                }
            }
        }
        return result.toString();
    }


    public static String setInPut(OutputStream outputStream, String inputFilePath) {
        StringBuilder sb = new StringBuilder();
        BufferedOutputStream bufferedOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            fileInputStream = new FileInputStream(inputFilePath);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = fileInputStream.read(buffer)) != -1) {
                sb.append(new String(buffer,0,length));
                bufferedOutputStream.write(buffer, 0, length);
                bufferedOutputStream.flush();
            }
        } catch (IOException e) {
            logger.error(e.toString());
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        return sb.toString();
    }
}
