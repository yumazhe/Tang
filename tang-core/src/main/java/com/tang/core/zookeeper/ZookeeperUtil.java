package com.tang.core.zookeeper;

import com.tang.core.callback.CallBack;
import com.tang.core.config.TangConfig;
import com.tang.core.exceptions.TangException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by yuma on 2019/12/9.
 */
public enum ZookeeperUtil {
    /**
     * 实例
     */
    INSTANCE;

    private Logger logger = null;

    private ZooKeeper server;
    private ZKWatcher watcher;

    // 是否需要监听器
    private boolean isWatcher = TangConfig.isWatcher;

    /**
     * 创建对象并初始化数据
     */
    ZookeeperUtil() {
        logger = LoggerFactory.getLogger(ZookeeperUtil.class);
        // 连接zookeeper服务
        if (server == null) {
            if (TangConfig.zookeeperHost == null) {
                throw new TangException("zk host must not be null.");
            }

            // 建立连接
            try {
                // 创建监听器
                if (isWatcher) {
                    watcher = new ZKWatcher();
                    server = new ZooKeeper(TangConfig.zookeeperHost, TangConfig.zookeeperTimeout, watcher);
                    watcher.setServer(server);

                } else {
                    server = new ZooKeeper(TangConfig.zookeeperHost, TangConfig.zookeeperTimeout, null);

                }
                // 设置监听服务
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.debug("create zk client. host: [" + TangConfig.zookeeperHost + "], timeout: [" + TangConfig.zookeeperTimeout + "]");
        }

    }

    /**
     * 存储数据
     *
     * @param path  节点路径
     * @param value 节点数据
     */
    public void write(String path, String value) throws KeeperException, InterruptedException {
        Stat stat = server.exists(path, isWatcher);
        if (stat == null) {
            create(path);
        }
        // 设置数据
        server.setData(path, value.getBytes(), stat.getVersion());
    }

    /**
     * 存储数据
     *
     * @param path  节点路径
     * @param value 节点数据
     */
    public void write(String path, byte[] value) throws KeeperException, InterruptedException {
        Stat stat = server.exists(path, isWatcher);
        if (stat == null) {
            create(path);
        }
        // 设置数据
        server.setData(path, value, stat.getVersion());
    }

    /**
     * 读取节点数据
     *
     * @param path
     * @return
     */
    public String read(String path) throws KeeperException, InterruptedException {
        // 判断节点是否存在
        Stat stat = server.exists(path, isWatcher);
        if (stat != null) {
            byte[] data = server.getData(path, isWatcher, null);
            return new String(data);
        } else {
            throw new TangException("the path[" + path + "] is not exist.");
        }
    }

    /**
     * 获取子节点列表
     *
     * @param parent
     * @return
     */
    public List<String> children(String parent) throws KeeperException, InterruptedException {
        List<String> nodes = server.getChildren(parent, isWatcher);

        if (nodes == null) {
            return Collections.EMPTY_LIST;

        } else {
            return nodes;
        }

    }

    /**
     * 判断并判断是否需要创建节点
     *
     * @param path 节点路径
     */
    public void create(String path) throws KeeperException, InterruptedException {
        if (path == null || path.trim().length() == 0) {
            return;
        }

        // 判断节点是否存在
        Stat stat = server.exists(path, isWatcher);

        if (stat == null) {
            int endIndex = path.lastIndexOf("/");
            if (endIndex > -1) {
                String parent = path.substring(0, endIndex);
                create(parent);
            }
            if (path == null || path.trim().length() == 0) {
                return;
            }
            server.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    /**
     * 注册回调事件处理器
     *
     * @param callback
     */
    public void register(CallBack callback) {
        watcher.setCallback(callback);
    }

}
