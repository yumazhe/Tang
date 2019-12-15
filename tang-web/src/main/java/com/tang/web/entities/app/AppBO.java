package com.tang.web.entities.app;

/**
 * Created by yuma on 2019/12/9.
 */
public class AppBO {

    /**
     * 应用名称
     */
    private String name;
    /**
     * 应用描述
     */
    private String description;

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

    @Override
    public String toString() {
        return "AppBO{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }

    public AppPO toPO() {
        AppPO po = new AppPO();
        po.setAppName(name);
        po.setAppDescription(description);

        return po;
    }
}
