package com.tang.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.tang.spring")
public class SpringBeanConfigutionTest {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringBeanConfigutionTest.class);
        SpringBeanTest test = ac.getBean(SpringBeanTest.class);
        for (; ; ) {
            System.out.println(test.name);
            Thread.sleep(2000);
        }
    }
}