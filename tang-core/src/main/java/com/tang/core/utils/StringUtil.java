package com.tang.core.utils;

import com.tang.core.config.TangConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Created by yuma on 2019/12/9.
 * 字符串解析工具
 */
public class StringUtil {



    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static String parseStringValue(String path) {
        logger.debug("change before: {}", path);

        StringBuilder result = new StringBuilder(path);

        // 寻找 "${" 的位置
        int startIndex = path.indexOf(TangConstant.DEFAULT_PLACEHOLDER_PREFIX);
        logger.debug("the ${'s index is : {}.", startIndex);

        // 寻找 } 的位置
        int endIndex = findPlaceholderEndIndex(result, startIndex);
        logger.debug("the }'s index is : {}.", endIndex);

        String env = path.substring(startIndex+TangConstant.DEFAULT_PLACEHOLDER_PREFIX.length(), endIndex);
        logger.debug("the env is : {}.", env);
        String act = resolvePlaceholder(env);
        logger.debug("the act is : {}.", act);
        if(act == null || act.trim().length() == 0){
            throw new RuntimeException("jvm does not set " + env);
        }

        //替换
        path = path.substring(0, startIndex) + act + path.substring(endIndex+TangConstant.DEFAULT_PLACEHOLDER_SUFFIX.length());
        logger.debug("change after: {}", path);

        return path;
    }

    private static int findPlaceholderEndIndex(CharSequence buf, int startIndex) {

        int len = buf.length();
        while (startIndex < len){
            if(StringUtils.substringMatch(buf, startIndex, TangConstant.DEFAULT_PLACEHOLDER_SUFFIX)){
                return startIndex;
            }
            startIndex++;
        }
        return -1;
    }

    private static String resolvePlaceholder(String placeholderName) {
        try {
            String value = System.getProperty(placeholderName);
            if (value == null) {
                value = System.getenv(placeholderName);
            }

            return value;
        } catch (Throwable throwable) {

            return null;
        }
    }


}
