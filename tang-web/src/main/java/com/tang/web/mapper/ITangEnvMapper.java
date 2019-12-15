package com.tang.web.mapper;

import com.tang.web.entities.env.EnvPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */
@Mapper
public interface ITangEnvMapper {

    // 保存应用
    void save(EnvPO po);

    // 查询应用列表
    List<EnvPO> list(@Param("aid") Integer appid, @Param("vid") Integer versionid);
}
