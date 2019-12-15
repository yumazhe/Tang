package com.tang.web.utils;

import com.tang.core.exceptions.TangException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by yuma on 2019/12/10.
 */
public class FileUtil {

    public static String getContent(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new TangException("上传失败，请选择文件");
        }


        // 获取文件
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));
        if (!type.equals(".properties")) {
            throw new TangException("必须为properties文件");
        }

        InputStream in = null;
        try {
            // 解析properties文件
            in = file.getInputStream();
            Properties properties = new Properties();
            properties.load(in);

            StringBuffer sb = new StringBuffer();
            Set<Map.Entry<Object, Object>> set = properties.entrySet();
            for(Map.Entry entry : set){
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
            }
            String content = sb.toString();
            return content;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return null;
    }
}
