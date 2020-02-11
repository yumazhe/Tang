package com.tang.properties;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * Created by yuma on 2019/12/9.
 */
public class ZookeeperTest {

    private static ZkClient zkClient;
    private static String host = "127.0.0.1:2181";
    private static int timeout = 3000;

    public static void main(String[] args) {
         zkClient = new ZkClient(host, timeout);

        String path = "/tang/disconf_demo/1_0_0_0/local/user-local.properties";
        String value = "dubbo.registry.zookeeper = 192.168.1.1:2181\ndubbo.registry.zookeeper2 = 192.168.1.1:2183";
        delete(path);
        write(path, value);

        read(path);

    }


    public static void delete(String path){
        boolean exist = zkClient.exists(path);
        if(exist){
            zkClient.delete(path);
        }
    }

    public static void write(String path, String value){
        boolean exist = zkClient.exists(path);
        if(!exist){
            create(path);
        }
        zkClient.writeData(path, value);
    }

    public static void read(String path){
        System.out.println(zkClient.readData(path));
    }

    /**
     * 创建节点
     * @param path
     */
    public static void create(String path){
        if(path == null || path.trim().length() == 0) return;

        // 判断节点是否存在
        boolean exist = zkClient.exists(path);

        if(!exist){
            int endIndex = path.lastIndexOf("/");
            if(endIndex > -1){
                String parent = path.substring(0, endIndex);
                create(parent);
            }
            System.out.println("path: "+path);
            if(path == null || path.trim().length() == 0) return;
            zkClient.create(path, "", CreateMode.PERSISTENT);
        }
    }
}
