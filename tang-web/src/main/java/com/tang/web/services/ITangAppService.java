package com.tang.web.services;


import com.tang.web.entities.app.AppBO;
import com.tang.web.entities.app.AppPO;

import java.util.List;

/**
 * Created by yuma on 2019/12/2.
 */
public interface ITangAppService {

    /**
     * 保存应用
     * @param app
     * @return
     */
    int save(AppBO app);

    /**
     * 查询应用列表
     * @return
     */
    List<AppPO> list();

    /**
     * 根据id获取应用名称
     *
     * @param appid
     * @return
     */
    String findAppNameById(Integer appid);
}
