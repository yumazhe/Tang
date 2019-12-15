package com.tang.web.dao.impl;

import com.tang.web.dao.ITangUserDao;
import com.tang.web.mapper.ITangUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by yuma on 2019/12/13.
 */
@Repository
public class TangUserDaoImpl implements ITangUserDao {

    @Autowired
    private ITangUserMapper userMapper;
    /**
     * 根据账户获取密码
     *
     * @param name
     * @return
     */
    @Override
    public String getUserPasswordByName(String name) {
        return userMapper.getUserPasswordByName(name);
    }
}
