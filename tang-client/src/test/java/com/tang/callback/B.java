package com.tang.callback;

/**
 * Created by yuma on 2020/2/5.
 */
public class B {

    public B(final Callback callback) {
        callback.syncHandler();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callback.asyncHandler();
            }
        }.start();
    }
}
