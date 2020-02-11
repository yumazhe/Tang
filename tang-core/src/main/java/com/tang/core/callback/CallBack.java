package com.tang.core.callback;

import java.io.IOException;

/**
 * Created by yuma on 2020/2/5.
 */
public interface CallBack {

    /**
     * 处理字节数组
     * @param data
     */
    public void doHandler(byte[] data) throws IOException;
}
