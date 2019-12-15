package com.tang.web.dao.impl;

import com.tang.web.dao.ITangEnvDao;
import com.tang.web.entities.env.EnvPO;
import com.tang.web.mapper.ITangEnvMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */

@Repository
public class TangEnvDaoImpl implements ITangEnvDao {

    @Autowired
    private ITangEnvMapper envMapper;

    /**
     * 保存应用
     * @param po
     */
    @Override
    public void save(EnvPO po){
        envMapper.save(po);
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
        return envMapper.list(appid, versionid);
    }

}
