package com.tang.web.services.impl;

import com.tang.web.dao.ITangConfigDao;
import com.tang.web.entities.config.ConfigBO;
import com.tang.web.entities.config.ConfigPO;
import com.tang.web.services.ITangAppService;
import com.tang.web.services.ITangConfigService;
import com.tang.web.services.ITangEnvService;
import com.tang.web.services.ITangVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuma on 2019/12/2.
 */
@Service
public class TangConfigService implements ITangConfigService {

    @Autowired
    private ITangConfigDao configDao;


    /**
     * 保存应用
     *
     * @param bo
     * @return
     */
    @Override
    public int save(ConfigBO bo) {
        ConfigPO po = bo.toPO();

        configDao.save(po);
        return po.getConfigId();
    }

    /**
     * 查询应用列表
     *
     * @param appid
     * @param versionid
     * @param envid
     * @return
     */
    @Override
    public List<ConfigPO> list(Integer appid, Integer versionid, Integer envid) {

        return configDao.list(appid, versionid, envid);
    }

    /**
     * 获取配置详情
     *
     * @param id
     * @return
     */
    @Override
    public ConfigPO queryById(Integer id) {
        return configDao.queryById(id);
    }

    /**
     * 更新配置信息
     *
     * @param id
     * @param content
     */
    @Override
    public void update(Integer id, String content) {
        configDao.update(id, content);
    }

    /**
     * 根据id获取配置属性名称
     *
     * @param id
     * @return
     */
    @Override
    public String findConfigNameById(Integer id) {
        return configDao.findConfigNameById(id);
    }

}
