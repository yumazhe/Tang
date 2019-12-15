package com.tang.web.services;

/**
 * Created by yuma on 2019/12/13.
 */
public interface ITangUserService {

    /**
     * 根据账户获取密码
     * @param name
     * @return
     */
    String getUserPasswordByName(String name);
}
