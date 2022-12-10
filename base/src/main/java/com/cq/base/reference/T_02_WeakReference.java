package com.cq.base.reference;

import java.lang.ref.WeakReference;

/**
 * @Author chenquan
 * @Date 2022-10-19 23:05
 * @Description 弱引用
 **/

public class T_02_WeakReference {
    public static void main(String[] args) {
        /**
         * 总结：
         * 只要发生GC，不管内存满不满，都回收
         */
        // 设置JVM: -Xms20M -Xmx20M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
        WeakReference<byte[]> weakReference = new WeakReference<>(new byte[1024 * 1024 * 10]);
        System.out.println(String.format("第1次拿weakReference：%s",weakReference.get()));
        System.out.println(String.format("第2次拿weakReference：%s",weakReference.get()));
        // 调用一次GC
        System.gc();
        System.out.println(String.format("第3次拿weakReference：%s",weakReference.get()));

        /**
         * 应用场景：ThreadLocal
         */

        byte[] bytes = new byte[1024 * 1024 * 10];
        ThreadLocal<byte[]> threadLocal = new ThreadLocal<>();

    }
}
