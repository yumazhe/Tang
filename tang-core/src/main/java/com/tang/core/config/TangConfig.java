package com.tang.core.config;

import com.tang.core.exceptions.TangException;
import com.tang.core.zookeeper.ZookeeperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by yuma on 2019/12/8.
 * 配置文件详情
 */
public class TangConfig {

    private static final Logger logger = LoggerFactory.getLogger(TangConfig.class);

    /**
     * 文件路径分隔符
     */
    public static final String default_file_separator = "/";

    /**
     * 环境配置
     */
    public static String env;

    /**
     * zookeeper 地址
     */
    public static String zookeeperHost;
    /**
     * zookeeper连接超时。单位：毫秒
     */
    public static int zookeeperTimeout = 3000;

    /**
     * 应用名称
     */
    public static String appName;

    /**
     * 应用版本
     */
    public static String appVersion;

    /**
     * 无需远程读取的配置文件名称，即通过本地配置进行加载。多个文件以,进行分割
     */
    public static String[] ignores;

    /**
     * 重试次数，默认3次
     */
    public static int retryTimes = 3;
    /**
     * 间隔时间，单位：秒。默认5秒
     */
    public static int intervalTime = 5;


    public static void init(Properties props){
        // 设置环境变量
        String env = props.getProperty("env");
        if(env == null || env.trim().length() == 0){
            throw new RuntimeException("the env must not be null");
        }
        TangConfig.env = env;

        // 设置应用名称
        String appName = props.getProperty("app_name");
        if(appName == null || appName.trim().length() == 0){
            throw new RuntimeException("the appName must not be null");
        }
        TangConfig.appName = appName;

        // 设置版本号
        String version = props.getProperty("version");
        if(version == null || version.trim().length() == 0){
            version = "default";
            logger.warn("there is no app_version setting. default value is {}", version);
        }
        TangConfig.appVersion = version;

        // 设置忽略文件
        String ignore = props.getProperty("ignore");
        if(ignore != null && ignore.trim().length() > 0){
            String[] ignores = ignore.split(",");
            TangConfig.ignores = ignores;
        }

        // 设置zookeeper地址
        String zkHost = props.getProperty("zookeeper_host");
        if(zkHost == null || zkHost.trim().length() == 0){
            throw new RuntimeException("the zkHost must not be null");
        }
        TangConfig.zookeeperHost = zkHost;

        // 设置应用名称
        String retris = props.getProperty("retry_times");
        if(retris != null && retris.trim().length() == 0){
            TangConfig.retryTimes = Integer.valueOf(retris);
        }

        // 设置间隔时间
        String interval = props.getProperty("interval_time");
        if(interval != null && interval.trim().length() > 0){
            TangConfig.intervalTime = Integer.valueOf(interval);
        }

        print();
    }

    /**
     * 判断文件是否被忽略
     * @param file
     * @return
     */
    public static boolean ignore(String file){
        if(file == null || file.trim().length() == 0){
            return false;
        }
        if(ignores == null){
            return false;
        }else{
            for(String ignore : ignores){
                if(ignore.equals(file)){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 根据文件名称从远程节点获取数据流
     * @param file
     * @return
     */
    public static InputStream readFromRemote(String file) throws IOException {
        /**
         * zk: 127.0.0.1:2181
         *  root: /
         *          - tang
         *              - app_name
         *                  - version
         *                      - env
         *                          - file : value
         */
        InputStream   in   = null;
        try {

            // 获取zk节点
            String node = TangConfig.generateNode(file);
            logger.debug("the current node is [{}].", node);

            // 获取节点信息
            String value = ZookeeperUtil.INSTANCE.read(node);
            if (value == null || value.trim().length() == 0) {
                return null;
            }

            in = new ByteArrayInputStream(value.getBytes());
            return in;

        }catch (Exception e){
            throw new TangException("can't get the value from ["+file+"].");
        }finally {
            if(in != null){
                in.close();
            }
        }
    }

    /**
     * 根据文件名称生成节点路径:
     *            root: /
     *                    - tang
     *                        - app_name
     *                            - version
     *                                - env
     *                                    - file : value
     *
     * @param file
     * @return
     */
    private static String generateNode(String file) {
        StringBuffer sb = new StringBuffer();
                //根目录
        sb.append(default_file_separator)
            // 中间件
            .append("tang")
            .append(default_file_separator)
            // 应用名称
            .append(appName)
            .append(default_file_separator)
            // 版本号
            .append(appVersion)
            .append(default_file_separator)
            // 环境变量
            .append(env)
            .append(default_file_separator)
            // 文件名称
            .append(file);
        return sb.toString();
    }

    public static void print(){
        logger.debug("环境变量：{}, 应用名称：{}, 版本号：{}, zk地址：{}, 忽略文件：{}, 重试次数: {}次, 间隔时间：{}秒",
                        env,    appName,   appVersion,  zookeeperHost, ignores, retryTimes, intervalTime);
    }
}
