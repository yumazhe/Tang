package com.tang.callback;

/**
 * Created by yuma on 2020/2/5.
 */
public class A implements Callback {

    private String name;
    private int age;

    public static void main(String[] args) throws InterruptedException {
        A a = new A();
        new B(a);
        for (; ; ) {
            Thread.sleep(1000);
            System.out.println("name:" + a.name + ", age:" + a.age);
        }
    }

    /**
     * 同步处理
     */
    @Override
    public void syncHandler() {
        this.name = "xiaolang";
        this.age = 18;
    }

    /**
     * 异步处理
     */
    @Override
    public void asyncHandler() {
        this.name = "yuma";
        this.age = 28;
    }
}
