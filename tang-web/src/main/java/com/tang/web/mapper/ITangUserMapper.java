package com.tang.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by yuma on 2019/12/13.
 */
@Mapper
public interface ITangUserMapper {

    //根据账户获取密码
    String getUserPasswordByName(@Param("name") String name);

}
