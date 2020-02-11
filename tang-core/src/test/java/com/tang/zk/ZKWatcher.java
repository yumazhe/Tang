package com.tang.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;


/**
 * Created by yuma on 2020/2/6.
 * 监听器
 */
public class ZKWatcher implements Watcher {

    private ZooKeeper server;

    public ZKWatcher() {
    }

    public ZKWatcher(ZooKeeper server) {
        this.server = server;
    }

    @Override
    public void process(WatchedEvent event) {

        Event.EventType type = event.getType();
        switch (type) {
            case None:
                System.out.println("没有变化");
                break;
            case NodeCreated:
                System.out.println("节点创建");

                break;
            case NodeDeleted:
                System.out.println("节点删除");

                break;
            case NodeDataChanged:
                String path = event.getPath();
                try {
                    byte[] data = server.getData(path, this, null);
                    System.out.println("节点[" + path + "]数据变化. " + new String(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            case NodeChildrenChanged:
                System.out.println("节点下的子节点变动");

                break;
        }

    }

    public ZooKeeper getServer() {
        return server;
    }

    public void setServer(ZooKeeper server) {
        this.server = server;
    }
}
