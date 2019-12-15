package com.tang.web.entities.version;

/**
 * Created by yuma on 2019/12/9.
 */
public class VersionBO {

    private String name;
    private String description;
    private String appid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Override
    public String toString() {
        return "VersionBO{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", appid=" + appid + '}';
    }

    public VersionPO toPO() {
        VersionPO po = new VersionPO();
        po.setVersionName(this.name);
        po.setVersionDescription(this.description);
        po.setAppId(Integer.valueOf(this.appid));
        return po;
    }
}
