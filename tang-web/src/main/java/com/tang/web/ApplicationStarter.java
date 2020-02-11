package com.tang.web;

import com.tang.core.config.TangConfig;
import com.tang.core.config.TangConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.tang.web"})
public class ApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
        System.out.println("---------Tang web start success!----------");

        init();
    }

    /**
     * 初始化环境
     */
    public static void init(){
        // TODO 初始化zk配置
        TangConfig.zookeeperHost = "127.0.0.1:2181";
        TangConfig.zookeeperTimeout = 5000;


    }

}