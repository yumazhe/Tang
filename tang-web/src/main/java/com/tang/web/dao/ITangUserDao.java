package com.tang.web.dao;

/**
 * Created by yuma on 2019/12/13.
 */
public interface ITangUserDao {

    /**
     * 根据账户获取密码
     * @param name
     * @return
     */
    String getUserPasswordByName(String name);
}
