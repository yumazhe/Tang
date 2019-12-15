package com.tang.web.mapper;

import com.tang.web.entities.config.ConfigPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */
@Mapper
public interface ITangConfigMapper {

    // 保存应用
    void save(ConfigPO po);

    // 查询应用列表
    List<ConfigPO> list(@Param("aid") Integer appid, @Param("vid") Integer versionid, @Param("eid") Integer envid);

    // 查询配置详情
    ConfigPO queryById(@Param("id") Integer id);

    // 更新配置详情
    void update(@Param("id") Integer id, @Param("content") String content);
}
