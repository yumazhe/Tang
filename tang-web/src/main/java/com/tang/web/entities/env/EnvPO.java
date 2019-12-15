package com.tang.web.entities.env;

import java.util.Date;

/**
 * Created by yuma on 2019/12/9.
 */
public class EnvPO {

    private Integer envId;
    private String envCode;
    private String envDescription;
    private Integer versionId;
    private Integer appId;
    private Date createTime;
    private Date updateTime;

    public Integer getEnvId() {
        return envId;
    }

    public void setEnvId(Integer envId) {
        this.envId = envId;
    }

    public String getEnvCode() {
        return envCode;
    }

    public void setEnvCode(String envCode) {
        this.envCode = envCode;
    }

    public String getEnvDescription() {
        return envDescription;
    }

    public void setEnvDescription(String envDescription) {
        this.envDescription = envDescription;
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

    public EnvVO toVO() {
        EnvVO vo = new EnvVO();
        vo.setId(this.envId);
        vo.setCode(this.envCode);
        vo.setDescription(this.envDescription);
        return vo;
    }
}
