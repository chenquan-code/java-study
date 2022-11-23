package com.cq.io.io_04_socket_multiplexing_group;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author chenquan
 * @Date 2022-11-23 17:40
 * @Description: TODO Selector 工作的实例
 * 实现功能：
 * 1. selector监听事件，并做响应
 * @Version: 1.0
 **/

public class SelectorThread implements Runnable {

    Selector selector = null;

    LinkedBlockingQueue<Channel> queue = new LinkedBlockingQueue<>();

    SelectorThreadGroup stg = null;

    public SelectorThread() {
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "【SelectorThread】已启动...");
        // TODO Loop
        while (true) {
            try {
                // TODO select() 返回可以处理的事件个数
                int select = selector.select(10000);
                System.out.println(Thread.currentThread().getName() + "【SelectorThread】select size " + select + "...");
                // TODO 处理事件
                if (select > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        }
                    }
                }
                // TODO 从队列里面取出 SelectorThreadGroup nextSelector()方法 put 进来的 ServerSocketChannel 或 SocketChannel 并注册响应的事件
                if (!queue.isEmpty()) {
                    Channel channel = queue.take();
                    if (channel instanceof ServerSocketChannel) {
                        ServerSocketChannel server = (ServerSocketChannel) channel;
                        System.out.println(Thread.currentThread().getName() + "【SelectorThread】注册Listen...");
                        server.register(selector, SelectionKey.OP_ACCEPT);
                    } else {
                        SocketChannel client = (SocketChannel) channel;
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        System.out.println(Thread.currentThread().getName() + "【SelectorThread】注册Client...");
                        client.register(selector, SelectionKey.OP_READ, buffer);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    private void readHandler(SelectionKey key) {
        System.out.println(Thread.currentThread().getName() + "【readhandler】...");
        SocketChannel client = null;
        try {
            client = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();
            while (true) {
                int number = client.read(buffer);
                if (number > 0) {
                    buffer.flip();
                    client.write(buffer);
                } else if (number == 0) {
                    break;
                } else if (number == -1) {
                    System.out.println(Thread.currentThread().getName() + "【readhandler】客户端" + client.getRemoteAddress() + "断开连接...");
                    key.cancel();
//                    client.close();
                }
            }
        } catch (Exception e) {
            try {
                System.out.println(Thread.currentThread().getName() + "【readhandler】客户端" + client.getRemoteAddress() + "断开连接...");
                key.cancel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void acceptHandler(SelectionKey key) {
        System.out.println(Thread.currentThread().getName() + "【acceptHandler】...");
        try {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            stg.nextSelector(client);
//            client.register(selector,SelectionKey.OP_READ,buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setWork(SelectorThreadGroup stg) {
        this.stg = stg;
    }
}
