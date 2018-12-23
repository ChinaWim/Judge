package com.oj.judge.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author m969130721@163.com
 * @date 18-12-18 下午9:43
 */
public class Test {

    public static void main(String[] args) throws IOException {
        URL uRl = new URL("http://127.0.0.1:9081/judge/submit");
        for(int i = 0; i < 1000; i++){
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRl.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.connect();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            StringBuilder sb = new StringBuilder();
            sb.append("type=java&");
            sb.append("sourceCode=import java.util.*;\n" +
                    "public class Main{\n" +
                    "\t\n" +
                    "\tpublic static void main(String [] args){\n" +
                    " try{\n" +
                    "     Thread.sleep(1);\n" +
                    "}catch(Exception e){\n" +
                    "}\n" +
                    "}\n" +
                    "}&");
            sb.append("problemId=1000&");
            sb.append("userId=1");
            String string = sb.toString();
            outputStream.write(string.getBytes());
            outputStream.flush();
            outputStream.close();
            System.out.println("=========output============");
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] buffer = new byte[1024];
            int length = inputStream.read(buffer);
            System.out.println(new String(buffer,0,length));
            inputStream.close();
        }

    }
}
