package com.tang.web.dao;

import com.tang.web.entities.version.VersionPO;

import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */
public interface ITangVersionDao {

    /**
     * 保存应用
     * @param po
     */
    void save(VersionPO po);

    /**
     * 查询应用列表
     * @return
     * @param id
     */
    List<VersionPO> list(Integer id);

    /**
     * 根据id获取版本名称
     * @param version
     * @return
     */
    String findVersionNameById(Integer version);
}
