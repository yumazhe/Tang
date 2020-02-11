package com.tang.web.dao;

import com.tang.web.entities.env.EnvPO;

import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */
public interface ITangEnvDao {

    /**
     * 保存应用
     * @param po
     */
    void save(EnvPO po);

    /**
     * 查询应用列表
     * @return
     * @param appid
     * @param versionid
     */
    List<EnvPO> list(Integer appid, Integer versionid);

    /**
     * 根据id获取环境名称
     * @param envid
     * @return
     */
    String findEnvNameById(Integer envid);
}
