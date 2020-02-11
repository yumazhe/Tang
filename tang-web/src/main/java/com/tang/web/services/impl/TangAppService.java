package com.tang.web.services.impl;

import com.tang.web.dao.ITangAppDao;
import com.tang.web.entities.app.AppBO;
import com.tang.web.entities.app.AppPO;
import com.tang.web.services.ITangAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuma on 2019/12/2.
 */
@Service
public class TangAppService implements ITangAppService {

    @Autowired
    private ITangAppDao appDao;
    /**
     * 保存应用
     *
     * @param bo
     * @return
     */
    @Override
    public int save(AppBO bo) {
        AppPO po  = bo.toPO();
        appDao.save(po);
        return po.getAppId();
    }

    /**
     * 查询应用列表
     *
     * @return
     */
    @Override
    public List<AppPO> list() {

        return appDao.list();
    }

    /**
     * 根据id获取应用名称
     *
     * @param appid
     * @return
     */
    @Override
    public String findAppNameById(Integer appid) {
        return appDao.findAppNameById(appid);
    }
}
