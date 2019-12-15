package com.tang.web.entities.app;

import java.util.Date;

/**
 * Created by yuma on 2019/12/9.
 */
public class AppPO {

    private Integer appId;
    private String appName;
    private String appDescription;
    private Date createTime;
    private Date updateTime;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
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

    @Override
    public String toString() {
        return "AppPO{" + "appId='" + appId + '\'' + ", appName='" + appName + '\'' + ", appDescription='" + appDescription + '\'' + ", createTime=" + createTime + ", updateTime=" + updateTime + '}';
    }

    public AppVO toVO() {
        AppVO vo = new AppVO();
        vo.setId(this.appId);
        vo.setName(this.appName);
        vo.setDescription(this.appDescription);

        return vo;
    }
}
