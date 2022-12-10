package com.cq.base.reference;

import java.lang.ref.SoftReference;

/**
 * @Author chenquan
 * @Date 2022-10-19 22:16
 * @Description 软引用
 **/

public class T_01_SoftReference {

    public static void main(String[] args) {
        /**
         * 总结：
         * 发生GC时，如果内存满了，就清理掉软引用，如果内存还川充足，就不清理
         */
        // 使用一个软引用对象包装一个字节数组，大小为10M
        SoftReference<byte[]> softReference = new SoftReference<>(new byte[1024 * 1024 * 1]); // 10M
        // softReference  强引用  SoftReference对象，SoftReference对象有一个弱引用 10M的字节数组
        System.out.println(String.format("第1次拿softReference：%s",softReference.get()));
        // 调用一次GC
        System.gc();
        System.out.println(String.format("第2次拿softReference：%s",softReference.get()));

        // 设置JVM内存为10M： -Xms10M -Xmx10M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
        // 由于JVM本身也要占用一部分内存，所以这里设置的10M是不够分配的，会报OutOfMemoryError！

        // 设置JVM内存为15M： -Xms15M -Xmx15M
        // 这个时候再分配一个10M大小的字节数组
        SoftReference<byte[]> softReference2 = new SoftReference<>(new byte[1024 * 1024 * 1]); // 10M
        System.out.println(String.format("第3次拿softReference：%s",softReference.get()));
        System.out.println(String.format("第1次拿softReference2：%s",softReference2.get()));
        // 发现分配第二个10M的字节数组的时候由于内存不够了，JVM把第一个软引用给回收了
        // 那么是不是内存够就不会回收第一个字节数组呢，我们把JVM参数再设大一点： -Xms35M -Xmx35M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
        // 调大之后发现没有回收第一个字节数组了

        /**
         * 应用场景：
         * 常用在缓存中
         */








    }
}
