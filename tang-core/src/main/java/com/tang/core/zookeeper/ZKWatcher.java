package com.tang.core.zookeeper;

import com.tang.core.callback.CallBack;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Created by yuma on 2020/2/6.
 * 监听器
 */
public class ZKWatcher implements Watcher {
    private static final Logger logger = LoggerFactory.getLogger(ZKWatcher.class);

    private ZooKeeper server;
    private CallBack callback;

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
                logger.debug("没有变化");
                break;
            case NodeCreated:
                logger.debug("节点创建");

                break;
            case NodeDeleted:
                logger.debug("节点删除");

                break;
            case NodeDataChanged:
                String path = event.getPath();
                try {
                    byte[] data = server.getData(path, this, null);
                    logger.debug("节点[" + path + "]数据变化. " + new String(data));
                    callback.doHandler(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            case NodeChildrenChanged:
                logger.debug("节点下的子节点变动");

                break;
        }

    }

    public ZooKeeper getServer() {
        return server;
    }

    public void setServer(ZooKeeper server) {
        this.server = server;
    }

    public CallBack getCallback() {
        return callback;
    }

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }
}
