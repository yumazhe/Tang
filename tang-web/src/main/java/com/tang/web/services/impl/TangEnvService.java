package com.tang.web.services.impl;

import com.tang.web.dao.ITangEnvDao;
import com.tang.web.entities.env.EnvBO;
import com.tang.web.entities.env.EnvPO;
import com.tang.web.services.ITangEnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuma on 2019/12/2.
 */
@Service
public class TangEnvService implements ITangEnvService {

    @Autowired
    private ITangEnvDao envDao;
    /**
     * 保存应用
     *
     * @param bo
     * @return
     */
    @Override
    public int save(EnvBO bo) {
        EnvPO po  = bo.toPO();

        envDao.save(po);
        return po.getEnvId();
    }

    /**
     * 查询应用列表
     *
     * @return
     * @param appid
     * @param versionid
     */
    @Override
    public List<EnvPO> list(Integer appid, Integer versionid) {

        return envDao.list(appid, versionid);
    }

    /**
     * 根据id获取环境名称
     *
     * @param envid
     * @return
     */
    @Override
    public String findEnvNameById(Integer envid) {
        return envDao.findEnvNameById(envid);
    }
}
