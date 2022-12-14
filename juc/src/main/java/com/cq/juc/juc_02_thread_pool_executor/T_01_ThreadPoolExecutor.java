package com.cq.juc.juc_02_thread_pool_executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author chenquan
 * @Date 2022-12-04 23:43
 * @Description: TODO
 * @Version: 1.0
 **/

public class T_01_ThreadPoolExecutor {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread());
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,
                2,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                new ThreadPoolExecutor.CallerRunsPolicy());

        pool.execute(new Say("hello", 10));
        pool.execute(new Say("world", 10));
        pool.execute(new Say("hi", 10));
    }

}