package com.tang.zk;

import com.tang.core.zookeeper.ZKWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by yuma on 2020/2/6.
 */
public class ZKTest {

    String path = "/test-watcher";

    ZooKeeper server = null;

    @Before
    public void connect() throws IOException {
        // 注册监听
        server = new ZooKeeper("127.0.0.1:2181", 20000, new ZKWatcher());
    }

    @Test
    public void read() throws KeeperException, InterruptedException {
        // 判断节点是否存在
        Stat stat = server.exists(path, null);

        if (stat == null) {
            System.out.println("节点[" + path + "]不存在");
        } else {
            byte[] data = server.getData(path, false, stat);
            System.out.println(new String(data));
        }
    }

    @Test
    public void creat() throws KeeperException, InterruptedException {
        String new_path = path+"/02";
        server.create(new_path, "2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void update() throws KeeperException, InterruptedException {
        Stat stat = server.exists(path,false);
        server.setData(path, "3".getBytes(), stat.getVersion());
    }

    @Test
    public void children() throws KeeperException, InterruptedException {
        List<String> nodes =  server.getChildren(path, true);
        for(String node : nodes){
            System.out.println(node);
        }
    }
}
