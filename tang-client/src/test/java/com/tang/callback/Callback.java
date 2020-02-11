package com.tang.callback;

/**
 * Created by yuma on 2020/2/5.
 */
public interface Callback {

    /**
     * 同步处理
     */
    public void syncHandler();

    /**
     * 异步处理
     */
    public void asyncHandler();
}
