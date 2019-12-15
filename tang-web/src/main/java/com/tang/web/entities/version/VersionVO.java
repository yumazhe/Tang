package com.tang.web.entities.version;

/**
 * Created by yuma on 2019/12/9.
 */
public class VersionVO {

    private int id;

    private String version;

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
