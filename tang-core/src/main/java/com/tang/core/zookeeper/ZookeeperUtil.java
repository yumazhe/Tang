package com.tang.core.zookeeper;

import com.tang.core.config.TangConfig;
import com.tang.core.exceptions.TangException;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yuma on 2019/12/9.
 */
public enum ZookeeperUtil {
    /**
     * 实例
     */
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperUtil.class);

    private ZkClient zkClient;

    /**
     * 数据初始化
     */
    ZookeeperUtil(){
        // 连接zookeeper服务
        if(zkClient==null){
            if(TangConfig.zookeeperHost == null){
                throw new TangException("zk host must not be null.");
            }
            System.out.println("create zk client. host: ["+TangConfig.zookeeperHost+"], timeout: ["+TangConfig.zookeeperTimeout+"]");
            // 建立连接
            zkClient = new ZkClient(TangConfig.zookeeperHost, TangConfig.zookeeperTimeout);
        }

    }

    /**
     * 存储数据
     * @param path 节点路径
     * @param value 节点数据
     */
    public void write(String path, String value){
        boolean exist = zkClient.exists(path);
        if(!exist){
            create(path);
        }
        zkClient.writeData(path, value);
    }

    /**
     * 读取节点数据
     * @param path
     * @return
     */
    public String read(String path){
        // 判断节点是否存在
        boolean exist = zkClient.exists(path);
        if(exist){
            String value = zkClient.readData(path);
            return value;
        }else{
            throw new TangException("the path["+path+"] is not exist.");
        }
    }

    /**
     * 判断并判断是否需要创建节点
     * @param path 节点路径
     */
    public void create(String path){
        if(path == null || path.trim().length() == 0) {
            return;
        }

        // 判断节点是否存在
        boolean exist = zkClient.exists(path);

        if(!exist){
            int endIndex = path.lastIndexOf("/");
            if(endIndex > -1){
                String parent = path.substring(0, endIndex);
                create(parent);
            }
            if(path == null || path.trim().length() == 0) {
                return;
            }
            zkClient.create(path, "", CreateMode.PERSISTENT);
        }
    }

}
