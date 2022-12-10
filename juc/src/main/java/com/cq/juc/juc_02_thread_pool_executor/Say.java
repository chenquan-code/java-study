package com.cq.juc.juc_02_thread_pool_executor;

/**
 * @Author chenquan
 * @Date 2022-12-11 0:12
 * @Description: TODO
 * @Version: 1.0
 **/

public class Say implements Runnable {
    String text;
    Integer count;

    public Say() {
    }
    public Say(String text, Integer count) {
        this.text = text;
        this.count = count;
    }
    @Override
    public void run() {
        while (count > 0) {
            System.out.println(Thread.currentThread() + "：" + text);
            try {
                Thread.sleep(1000);
                count--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
