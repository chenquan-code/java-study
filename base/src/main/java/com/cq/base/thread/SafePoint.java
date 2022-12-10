package com.cq.base.thread;
/**
 * @Author chenquan
 * @Description
 * @Date 2022-10-27 9:27
 * 设置save point 间隔时间 5s
 * -XX:+UnlockDiagnosticVMOptions -XX:GuaranteedSafepointInterval=5000
 * 打印gc信息
 * -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 *
**/

public class SafePoint {

    static long counter;

    public static void main(String[] args) throws Exception {

        new Thread(new R1(),"T1").start();;
        new Thread(new R2(),"T2").start();
        System.out.println("Main....");

    }

    public static class R1 implements Runnable {

        @Override
        public void run() {
            System.out.println("线程1开始执行...");
            while (true) {
                System.out.println("线程1执行中...");
                try {
                    // safepoint默认间隔时间是1s,所以这里让他睡眠1s，就直接进入savepoint了
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

            }
        }
    }

    public static class R2 implements Runnable {

        @Override
        public void run() {
            System.out.println("线程2开始执行...");
            for (int i = 0; i < 100000000; i++) {
                for (int j = 0; j < 1000; j++) {
                    counter += i % 33;
                    counter += i % 333;
                }
//                m();
            }
            System.out.println("线程2执行结束...");
        }
    }

    public static void m(){
        Thread.currentThread().getId();
    }
}

