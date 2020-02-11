package com.tang.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Scanner;


/**
 * @Auther: yumazhe
 * @Date: 2019/3/29 15:37
 * @Description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:*spring.xml"})
public class PropertiesTest {

    @Value("${dubbo.registry.zookeeper2}")
    private String zookeeper;

    @Test
    public void properties() throws InterruptedException {

        for (; ; ) {
            System.out.println("dubbo.registry.zookeeper2：" + zookeeper);
            Thread.sleep(2000);

        }

//        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void sub() {
        String s = "2019-12-17 00:06:55 ERROR cn.gov.cwl.vlt.service.devices.service.DeviceServiceImpl.activate(91) | t_game_deal_order#T20140186615756016912089158!102!11!19!871!0!110!100!363!781!401866!0!2401!1!1000![J@abd1046!1!100![{\"amount\":0.0$$#%%#,\"jackpotId\":1}]!0!0!10!2019-12-06 11:08:11.225!8306080000401734!1!10000";
        s = s.substring(s.indexOf("#") + 1);
        System.out.println(s);
    }


}
