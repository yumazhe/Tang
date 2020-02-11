package com.tang.web.dao.impl;

import com.tang.web.dao.ITangAppDao;
import com.tang.web.entities.app.AppPO;
import com.tang.web.mapper.ITangAppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */

@Repository
public class TangAppDaoImpl implements ITangAppDao {

    @Autowired
    private ITangAppMapper appMapper;

    /**
     * 保存应用
     * @param po
     * @return
     */
    @Override
    public void save(AppPO po){
        appMapper.save(po);
    }

    /**
     * 查询应用列表
     *
     * @return
     */
    @Override
    public List<AppPO> list() {
        return appMapper.list();
    }

    /**
     * 根据id获取应用名称
     *
     * @param appid
     * @return
     */
    @Override
    public String findAppNameById(Integer appid) {
        return appMapper.findAppNameById(appid);
    }

}
