package com.tang.web.entities.config;

/**
 * Created by yuma on 2019/12/9.
 */
public class ConfigBO {

    /**
     * 属性名称
     */
    private String name;
    /**
     * 属性内容
     */
    private String content;
    /**
     * 环境
     */
    private String envid;
    /**
     * 版本
     */
    private String versionid;
    /**
     * 应用
     */
    private String appid;

    /**
     * 配置类型：1-字符串，2-文件
     */
    private String type = "1";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEnvid() {
        return envid;
    }

    public void setEnvid(String envid) {
        this.envid = envid;
    }

    public String getVersionid() {
        return versionid;
    }

    public void setVersionid(String versionid) {
        this.versionid = versionid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ConfigBO{" + "name='" + name + '\'' + ", content='" + content + '\'' + ", envid=" + envid + ", versionid=" + versionid + ", appid=" + appid + '}';
    }

    public ConfigPO toPO() {
        ConfigPO po = new ConfigPO();
        po.setConfigName(this.name);
        po.setConfigContent(this.content);
        po.setEnvId(Integer.valueOf(this.envid));
        po.setVersionId(Integer.valueOf(this.versionid));
        po.setAppId(Integer.valueOf(this.appid));
        return po;
    }
}
