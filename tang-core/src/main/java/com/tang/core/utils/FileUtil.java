package com.tang.core.utils;

import com.tang.core.exceptions.TangException;

import java.io.*;

/**
 * Created by yuma on 2020/2/11.
 */
public class FileUtil {

    /**
     * 读取文件内容
     *
     * @param fileName 文件名称
     * @param in       文件流
     * @return
     * @throws IOException
     */
    public static String readFile(String fileName, InputStream in) throws IOException {
        // 获取文件
        String type = fileName.substring(fileName.lastIndexOf("."));
        if (!(type.equals(".properties") || type.equals(".xml") || type.equals(".txt"))) {
            throw new TangException("上传文件必须为xml/properties/txt格式的文本文件");
        }
        Reader reader = null;
        BufferedReader bufferedReader = null;
        try {
            reader = new InputStreamReader(in, "utf-8");

            bufferedReader = new BufferedReader(reader);

            StringBuffer sb = new StringBuffer();
            // 按行读取文件
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String content = sb.toString();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (reader != null) {
                reader.close();
            }

        }
        return null;
    }
}
