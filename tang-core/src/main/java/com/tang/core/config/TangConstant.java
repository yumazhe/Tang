package com.tang.core.config;

/**
 * Created by yuma on 2019/12/9.
 */
public class TangConstant {

    /**
     * xml文件扩展名
     */
    public static final String XML_FILE_EXTENSION = ".xml";

    /**
     * 变量占位符前缀
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * 变量占位符后缀
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    /**
     * 默认分隔符
     */
    public static final String DEFAULT_VALUE_SEPARATOR = ":";

    /**
     * 文件路径分隔符
     */
    public static final String default_file_separator = "/";

    /**
     * 默认配置中心的根节点路径
     */
    public static final String default_root_path = "tang";

    /**
     * zookeeper节点的最大值不超过 1M 字节
     */
    public static final int zk_value_max_size = 1 * 1024 * 1024;

}
