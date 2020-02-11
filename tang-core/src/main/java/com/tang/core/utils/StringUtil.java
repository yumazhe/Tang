package com.tang.core.utils;

import com.tang.core.config.TangConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tang.core.config.TangConstant.default_file_separator;
import static com.tang.core.config.TangConstant.default_root_path;

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


    /**
     * 根据规则生成节点路径
     *
     * @param fileName    配置文件名称
     * @param envName     环境变量名称
     * @param versionName 版本名称
     * @param appName     应用名称
     * @return
     */
    public static String generateNode(String fileName, String envName, String versionName, String appName) {
        StringBuffer sb = new StringBuffer();
        //根目录
        sb.append(default_file_separator)
                // 中间件
                .append(default_root_path).append(default_file_separator)
                // 应用名称
                .append(appName).append(default_file_separator)
                // 版本号
                .append(versionName).append(default_file_separator)
                // 环境变量
                .append(envName).append(default_file_separator)
                // 文件名称
                .append(fileName);
        return sb.toString();
    }

    /**
     * 切割字符串
     *
     * @param content
     * @param offset
     * @return
     */
    public static List<String> split(String content, int offset) {

        final int length = content.length();
        // 索引初始位置
        int index = 0;

        List<String> datas = new ArrayList<>();

        do {
            // 偏移量
            offset = length - index > offset ? offset : (length - index);
            // 截取的字符串内容
            String temp = content.substring(index, index + offset);
            // 添加字符串
            datas.add(temp);

            // 下次的起始位置
            index += offset;

        } while (index < length);

        return datas;
    }

    /**
     * 转换为字符串数值，个数不够左端补零
     *
     * @param num   数值
     * @param count 位数
     * @return
     */
    public static String formatByFillZero(int num, int count) {
        return String.format("%0" + count + "d", num);
    }
}
