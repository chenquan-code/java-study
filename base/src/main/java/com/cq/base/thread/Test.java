package com.cq.base.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author chenquan
 * @Description
 * @Date 2022-10-27 14:45
 **/

public class Test {
    public static volatile AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws Throwable {

        Runnable runnable = () -> {
            for (int i = 0; i < 10000000; i++) {
                num.getAndAdd(1);
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();

        System.out.println("before sleep");
        Thread.sleep(1000);
        System.out.println("after sleep");

        System.out.println(num);
    }
}
