package com.cq.io.io_03_socket_multiplexing;

/**
 * @Author chenquan
 * @Date 2022-11-23 2:36
 * @Description: TODO
 * @Version: 1.0
 **/

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIO {
    public static final int SIZE = 1024;
    void start(int port) {
        ByteBuffer buffer = ByteBuffer.allocate(SIZE);
        try {
            ServerSocketChannel s = ServerSocketChannel.open();
            ServerSocket socket = s.socket();
            Selector selector = Selector.open();
            InetSocketAddress addr = new InetSocketAddress(port);
            socket.bind(addr);
            s.configureBlocking(false);
            s.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                int selCount = selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    System.out.println("select Count:" + selCount);
                    SelectionKey key = (SelectionKey) keys.next();
                    keys.remove();
                    if (key.isAcceptable()) {
                        System.out.println("isacc...");
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        System.out.println("readable..");
                        SocketChannel sc = (SocketChannel) key.channel();
                        try {

                            int count = sc.read(buffer);
                            System.out.println("Read byte:" + count);
                            //log(buffer);
                            if(count==SIZE){
                                //如果count等于缓冲区大小，则说明此条消息没读完
                                //需要设计复杂点的消息结构

                            }else
                            if (count == -1) {
                                //当客户端断开连接时会触发read事件，并且可以读到-1，这时关闭通道
                                sc.close();
                                key.cancel();
                                continue;
                            }

                        } catch (Exception e) {
                            // TODO: handle exception
                            sc.close();
                            key.cancel();
                            System.out.println("close!");
                            continue;
                        }
                        buffer.flip();
                        ByteBuffer bb = buffer.duplicate();
                        key.attach(bb);
                        buffer.clear();
                        // sc.register(selector,
                        // SelectionKey.OP_WRITE|SelectionKey.OP_READ,bb);
                        //等价于
                        key.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                        //通常不会注册写事件，除非写的内容巨大，注册写事件后注意取消

                    } else if (key.isWritable()) {
                        System.out.println("writable..");
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer att = (ByteBuffer) key.attachment();
                        if (att.hasRemaining()) {
                            int count = sc.write(att);
                            System.out.println("write:" + count + " byte,hasRemain:" + att.hasRemaining());
                        } else {
                            //写完后取消可写事件，仅监听可读事件
                            key.interestOps(SelectionKey.OP_READ);
                        }
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void log(ByteBuffer buf){
        try {
            System.out.println(new String(buf.array(),0,buf.limit(),"utf8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new NIO().start(9090);
    }
}
