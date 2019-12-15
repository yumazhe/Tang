package com.tang.web.mapper;

import com.tang.web.entities.app.AppPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */
@Mapper
public interface ITangAppMapper {

    // 保存应用
    void save(AppPO po);

    // 查询应用列表
    List<AppPO> list();
}
