package com.cq.base.thread;

/**
 * @Author chenquan
 * @Date 2022-10-20 23:47
 * @Description 测试 volatile
 *
 * 一个CPU包含多个核，每个核包含 若干寄存器，PC, ALU
 * 我们的程序为一个.exe的静态文件，当双击.exe文件时，会将程序装载到内存中，找到它的Main方法启动
 * 一个程序主要分为数据和指令两部分
 * 每个CPU都会有3级缓存，L1,L2,L3,其中L1,L2是一个核共享，L3是一个CPU共享
 * 这个时候，我们就需要把数据传输到 L3,L2,L1，再到CPU的寄存器，层层递进，把指令传输到PC,这个时候数据和指令都有了，CPU的ALU就负责进行计算，并返回结果
 *
 * 由于缓存的存在，所以CPU不会时时刻刻都去内存中读数据，这样效率太慢了，缓存中有就直接读取缓存数据即可，于是就造成了每个线程都有自己的一个本地副本，导致原本的值修改了，线程本地依然是旧的值
 * 所以就要有一个机制，让内存的值改变了能通知到每个线程重新去读取，这就是缓存一致性协议
 *
 * CPU在设计的时候就已经从硬件层面提供了这个功能，只需要触发某个汇编指令即可，不同的CPU会有所不同
 *
 * System.out.println翻译成汇编指令后就包含了这个触发缓存一致性协议的指令，所以就算不加volatile，这个变量也能正确读到
 *
 * 而加上volatile其实只是会触发一个lock，在这个lock指令里包含了触发缓存一致性协议的指令，所以我们通常使用这个关键字让变量线程可见
 *
 **/

public class HelloVolatile {

    public static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {

        /**
         * 结论：
         * 某个变量使用volatile关键字修饰后代表线程可见，当值发生修改时会通知其他线程从内存中重新读取
         */

        new Thread(() ->{
            while (running){
                // 打开这个注释，变量running就算不加volatile也会更新
//                System.out.println("running");
            }
            System.out.println("程序结束!");
        }).start();

        Thread.sleep(3000);
        running = false;

    }
}
