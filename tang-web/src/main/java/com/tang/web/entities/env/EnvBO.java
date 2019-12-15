package com.tang.web.entities.env;

import com.tang.web.entities.app.AppPO;

/**
 * Created by yuma on 2019/12/9.
 */
public class EnvBO {
    private String code;
    private String description;
    private String versionid;
    private String appid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "EnvBO{" + "code='" + code + '\'' + ", description='" + description + '\'' + ", versionid='" + versionid + '\'' + ", appid='" + appid + '\'' + '}';
    }

    public EnvPO toPO() {
        EnvPO po = new EnvPO();
        po.setEnvCode(this.code);
        po.setEnvDescription(this.description);
        po.setVersionId(Integer.valueOf(versionid));
        po.setAppId(Integer.valueOf(appid));
        return po;
    }
}
