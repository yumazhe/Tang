package com.tang.web.services;


import com.tang.web.entities.version.VersionBO;
import com.tang.web.entities.version.VersionPO;

import java.util.List;

/**
 * Created by yuma on 2019/12/2.
 */
public interface ITangVersionService {

    /**
     * 保存应用
     * @param bo
     * @return
     */
    int save(VersionBO bo);

    /**
     * 查询应用列表
     * @return
     * @param id
     */
    List<VersionPO> list(Integer id);

    /**
     * 根据id获取版本名称
     *
     * @param version
     * @return
     */
    String findVersionNameById(Integer version);
}
