package com.tang.web.entities.app;

/**
 * Created by yuma on 2019/12/9.
 */
public class AppVO {

    /**
     * 应用id
     */
    private int id;

    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用描述
     */
    private String description;

    /**
     * 创建时间
     */
    private String create;

    /**
     * 更新时间
     */
    private String update;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    @Override
    public String toString() {
        return "AppVO{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
}
