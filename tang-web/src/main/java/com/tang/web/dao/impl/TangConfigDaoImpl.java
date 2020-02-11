package com.tang.web.dao.impl;

import com.tang.web.dao.ITangConfigDao;
import com.tang.web.entities.config.ConfigPO;
import com.tang.web.mapper.ITangConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */

@Repository
public class TangConfigDaoImpl implements ITangConfigDao {

    @Autowired
    private ITangConfigMapper configMapper;

    /**
     * 保存配置
     * @param po
     */
    @Override
    public void save(ConfigPO po){
        configMapper.save(po);
    }

    /**
     * 查询配置列表
     *
     * @return
     * @param appid
     * @param versionid
     * @param envid
     */
    @Override
    public List<ConfigPO> list(Integer appid, Integer versionid, Integer envid) {
        return configMapper.list(appid, versionid, envid);
    }

    /**
     * 查询配置详情
     *
     * @param id
     * @return
     */
    @Override
    public ConfigPO queryById(Integer id) {
        return configMapper.queryById(id);
    }

    /**
     * 更新配置详情
     *
     * @param id
     * @param content
     */
    @Override
    public void update(Integer id, String content) {
        configMapper.update(id, content);
    }

    /**
     * 根据id获取配置属性名称
     *
     * @param id
     * @return
     */
    @Override
    public String findConfigNameById(Integer id) {
        return configMapper.findConfigNameById(id);
    }


}
