package com.tang.web.mapper;

import com.tang.web.entities.version.VersionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */
@Mapper
public interface ITangVersionMapper {

    // 保存版本
    void save(VersionPO po);

    // 查询版本列表
    List<VersionPO> list(@Param("id") Integer id);

}
