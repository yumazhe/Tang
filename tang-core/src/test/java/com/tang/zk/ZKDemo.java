package com.tang.zk;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * Created by yuma on 2020/2/6.
 */
public class ZKDemo {

    public static void main(String[] args) throws InterruptedException {
        String path = "/test-watcher";

        ZkClient client = new ZkClient("127.0.0.1:2181", 5000);

        //对父节点添加监听子节点变化。
        client.subscribeChildChanges(path, new ChileListenerImpl());
        client.subscribeChildChanges(path, new ChileListenerImpl());

        System.out.println("主线程阻塞...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
