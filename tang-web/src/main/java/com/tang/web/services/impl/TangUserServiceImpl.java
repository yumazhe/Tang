package com.tang.web.services.impl;

import com.tang.web.dao.ITangUserDao;
import com.tang.web.services.ITangUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yuma on 2019/12/13.
 */
@Service
public class TangUserServiceImpl implements ITangUserService {

    @Autowired
    private ITangUserDao tangUserDao;
    /**
     * 根据账户获取密码
     *
     * @param name
     * @return
     */
    @Override
    public String getUserPasswordByName(String name) {
        return tangUserDao.getUserPasswordByName(name);
    }
}
