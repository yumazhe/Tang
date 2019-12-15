package com.tang.web.dao;

import com.tang.web.entities.config.ConfigPO;

import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */
public interface ITangConfigDao {

    /**
     * 保存应用
     * @param po
     */
    void save(ConfigPO po);

    /**
     * 查询应用列表
     * @return
     * @param appid
     * @param versionid
     * @param envid
     */
    List<ConfigPO> list(Integer appid, Integer versionid, Integer envid);

    /**
     * 查询配置详情
     * @param id
     * @return
     */
    ConfigPO queryById(Integer id);

    /**
     * 更新配置详情
     * @param id
     * @param content
     */
    void update(Integer id, String content);
}
