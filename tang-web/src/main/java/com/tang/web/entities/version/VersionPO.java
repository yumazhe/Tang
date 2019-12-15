package com.tang.web.entities.version;

import java.util.Date;

/**
 * Created by yuma on 2019/12/9.
 */
public class VersionPO {

    /**
     * 版本号id
     */
    private Integer versionId;
    private String versionName;
    private String versionDescription;
    private Integer appId;
    private Date createTime;
    private Date updateTime;

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
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

    @Override
    public String toString() {
        return "VersionPO{" + "versionId=" + versionId + ", versionName='" + versionName + '\'' + ", versionDescription='" + versionDescription + '\'' + ", appId=" + appId + ", createTime=" + createTime + ", updateTime=" + updateTime + '}';
    }

    public VersionVO toVO() {
        VersionVO vo = new VersionVO();
        vo.setId(Integer.valueOf(this.versionId));
        vo.setVersion(this.versionName);
        vo.setDescription(this.versionDescription);
        return vo;
    }
}
