package com.cq.base.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author chenquan
 * @Date 2022-10-20 18:55
 * @Description 虚引用
 **/

public class T_03_PhantomReference {

    // 用来模拟内存使用过程的队列
    private static final List<Object> LIST = new LinkedList<>();
    // 虚引用被回收时需要存放在该队列来
    private static final ReferenceQueue<P> QUEUE = new ReferenceQueue<>();


    public static void main(String[] args) throws InterruptedException {

        PhantomReference<P> phantomReference = new PhantomReference<>(new P(), QUEUE);


        // 虚引用是不能直接得到的，因此用户是无法直接使用虚引用包装的对象，一般是留给JVM内部使用，如GC线程
        System.out.println(phantomReference.get());

        // byteBuffer分配堆外内存，在清理堆外内存的类Cleaner就用到了虚引用
         ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        // 工作线程，每隔3秒内存占用增加1M
        new Thread(() -> {
            while (true) {
                LIST.add(new byte[1024 * 1024]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println(phantomReference.get());
            }
        }).start();

        // 垃圾回收线程，不断检测虚引用队列是否有对象可以回收
        new Thread(() -> {
            while (true) {
                Reference<? extends P> poll = QUEUE.poll();
                if (poll != null) {
                    System.out.println("--- 虚引用 对象被jvm回收了 ---- " + poll);
                }
            }
        }).start();


    }

    static class P {
        @Override
        protected void finalize() throws Throwable {
            System.out.println("我要被清掉了");
        }
    }
}
