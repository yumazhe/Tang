package com.tang.web.entities.config;

import com.tang.web.utils.CommonUtil;

import java.util.Date;

/**
 * Created by yuma on 2019/12/9.
 */
public class ConfigPO {

    private  Integer configId;
    private String configName;
    // 1-字符串， 2-文件
    private Integer configType = 1;
    private String configContent;
    private Integer envId;
    private Integer versionId;
    private Integer appId;
    private Date createTime;
    private Date updateTime;

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public Integer getConfigType() {
        return configType;
    }

    public void setConfigType(Integer configType) {
        this.configType = configType;
    }

    public String getConfigContent() {
        return configContent;
    }

    public void setConfigContent(String configContent) {
        this.configContent = configContent;
    }

    public Integer getEnvId() {
        return envId;
    }

    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public ConfigVO toVO() {
        ConfigVO vo = new ConfigVO();
        vo.setId(this.configId);
        vo.setName(this.configName);
        vo.setContent(this.configContent);
        vo.setType(this.configType == 1 ? "属性" : "文件");
        vo.setTime(CommonUtil.transDate2String(this.updateTime));
        return vo;
    }
}
