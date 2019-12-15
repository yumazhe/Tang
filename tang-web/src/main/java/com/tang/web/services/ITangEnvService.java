package com.tang.web.services;


import com.tang.web.entities.env.EnvBO;
import com.tang.web.entities.env.EnvPO;

import java.util.List;

/**
 * Created by yuma on 2019/12/2.
 */
public interface ITangEnvService {

    /**
     * 保存应用
     * @param bo
     * @return
     */
    int save(EnvBO bo);

    /**
     * 查询应用列表
     * @return
     * @param appid
     * @param versionid
     */
    List<EnvPO> list(Integer appid, Integer versionid);
}
