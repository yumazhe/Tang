package com.tang.web.common;

/**
 * Created by yuma on 2019/10/26.
 */
public class ResultBody {
    private int code = 0;
    private String msg = "success";
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultBody{" + "code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
    }
}
