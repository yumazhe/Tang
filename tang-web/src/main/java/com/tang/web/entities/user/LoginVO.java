package com.tang.web.entities.user;

import java.util.Date;

/**
 * Created by yuma on 2019/12/13.
 */
public class LoginVO {

    private String name;
    private String token;
    // 过期时间戳
    private long time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
