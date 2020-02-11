package com.tang.web.services;


import com.tang.web.entities.config.ConfigBO;
import com.tang.web.entities.config.ConfigPO;

import java.util.List;

/**
 * Created by yuma on 2019/12/2.
 */
public interface ITangConfigService {

    /**
     * 保存应用
     *
     * @param bo
     * @return
     */
    int save(ConfigBO bo);

    /**
     * 查询应用列表
     *
     * @param appid
     * @param versionid
     * @param envid
     * @return
     */
    List<ConfigPO> list(Integer appid, Integer versionid, Integer envid);

    /**
     * 获取配置详情
     *
     * @param id
     * @return
     */
    ConfigPO queryById(Integer id);

    /**
     * 更新配置信息
     *
     * @param id
     * @param content
     */
    void update(Integer id, String content);

    /**
     * 根据id获取配置属性名称
     *
     * @param id
     * @return
     */
    String findConfigNameById(Integer id);

}
