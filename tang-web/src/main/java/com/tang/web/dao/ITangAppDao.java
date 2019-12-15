package com.tang.web.dao;

import com.tang.web.entities.app.AppPO;

import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */
public interface ITangAppDao {

    /**
     * 保存应用
     * @param po
     * @return
     */
    void save(AppPO po);

    /**
     * 查询应用列表
     * @return
     */
    List<AppPO> list();
}
