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

    /**
     * 解析配置文件路径
     * classpath:env/user-${CONFIG_ENV}.properties
     *
     * @param path
     * @return
     */
    public static String parseStringValue(String path) {
        logger.debug("the setting file is: {}", path);

        StringBuilder result = new StringBuilder(path);

        // 解析：classpath:env/user-${CONFIG_ENV}.properties
        // 寻找 "${" 的位置
        int startIndex = path.indexOf(TangConstant.DEFAULT_PLACEHOLDER_PREFIX);

        // 寻找 } 的位置
        int endIndex = findPlaceholderEndIndex(result, startIndex);

        if (startIndex > 0 && endIndex > startIndex) {
            // 设置了环境变量，需要解析
            logger.debug("env param location's index is : [{},{}].", startIndex, endIndex);

            // 获取环境变量名
            String env = path.substring(startIndex + TangConstant.DEFAULT_PLACEHOLDER_PREFIX.length(), endIndex);
            // 获取环境变量属性值
            String act = resolvePlaceholder(env);
            logger.debug("the env is : {}={}.", env, act);

            if (act == null || act.trim().length() == 0) {
                throw new RuntimeException("jvm does not set " + env);
            }

            //替换
            path = path.substring(0, startIndex) + act + path.substring(endIndex + TangConstant.DEFAULT_PLACEHOLDER_SUFFIX.length());

            logger.debug("the setting file changed: {}", path);
        } else {
            // 没有设置环境变量，无需解析
            logger.debug("no set config env.");
        }


        return path;
    }

    /**
     * 寻找 } 的位置
     *
     * @param buf
     * @param startIndex
     * @return
     */
    private static int findPlaceholderEndIndex(CharSequence buf, int startIndex) {

        int len = buf.length();
        while (startIndex < len) {
            if (StringUtils.substringMatch(buf, startIndex, TangConstant.DEFAULT_PLACEHOLDER_SUFFIX)) {
                return startIndex;
            }
            startIndex++;
        }
        return -1;
    }

    /**
     * 获取环境变量属性值
     *
     * @param placeholderName
     * @return
     */
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
