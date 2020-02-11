package com.tang.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * Created by yuma on 2020/2/6.
 */
public class ZKServerDemo {

    /*
     * 主函数
     */
    public static void main(String[] args) throws Exception {

        ZKWatcher watcher = new ZKWatcher();
        // 注册监听
        ZooKeeper server = new ZooKeeper("127.0.0.1:2181", 20000, watcher);
        watcher.setServer(server);

        // 创建 节点
        String path = "/test-watcher";


        // 判断节点是否存在
        Stat stat = server.exists(path, true);

        if (stat == null) {
            System.out.println("节点[" + path + "]不存在. 创建节点");
            server.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        server.setData(path, "1".getBytes(), stat.getVersion());

        for (; ; ) {
            byte[] data = server.getData(path, false, null);
            System.out.println("data: " + new String(data));
            Thread.sleep(2000);
        }

        // 主线程阻塞
//        Thread.sleep(Long.MAX_VALUE);
    }
}
