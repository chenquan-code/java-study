package com.cq.io.io_02_socket_nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

/**
 * @Author chenquan
 * @Date 2022-11-21 21:47
 * @Description: TODO 测试 Socket NIO 特性
 * linux 运行 ： javac SocketNIO.java && strace -ff -o out java SocketNIO
 *
 * 使用一个线程就能处理一批 请求， BIO一个线程只能处理一个 请求
 *
 * @Version: 1.0
 **/

public class SocketNIO {

    private static LinkedList<SocketChannel> clients = new LinkedList<>();
    private static Charset charset = StandardCharsets.UTF_8;

    public static void main(String[] args) throws Exception {
        // Java NIO 提供的新API
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(9090));
        // TODO ServerSocketChannel包含阻塞和非阻塞两种模式，通过以下方法控制
        server.configureBlocking(false); // NONBLOCKING

        // TODO 再以下一个循坏里既做了 accept，又顺便把已经连接的客户端发送的消息消费一遍
        while (true){
            // TODO 由于上面设置了非阻塞模式，所以这里不会阻塞，没有客户端请求就返回 null
            SocketChannel client = server.accept();
            if (client == null){
                System.out.println("本次循环没有客户端连接...");
                Thread.sleep(1000);
            }else {
                // TODO 设置读取也非阻塞，这样服务端线程就不会一直卡在这里等客户端发送内容了
                client.configureBlocking(false);
                System.out.println("收到客户端" + client.socket().getPort());
                clients.add(client);
            }
            // 准备一个4k大小的堆外内存作为缓冲区，用来存放客户端发送过来的消息，如果这个缓存太小会怎样？
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);
            // 循环遍历所有的client，取出数据
            for (SocketChannel c : clients) {
                try {
                    // TODO 由于前面设置了client.configureBlocking(false)，所以这里不会阻塞，没有数据就返回 -1
                    if(c.read(byteBuffer) > 0 ){
                        byteBuffer.flip();
                        byte[] bytes = new byte[byteBuffer.limit()];
                        byteBuffer.get(bytes);
                        System.out.println("读取到客户端"+c.socket().getPort()+"数据："+new String(bytes));
                    }
                    byteBuffer.clear();
                }catch (Exception e){
                    e.printStackTrace();
                    clients.remove(c);
                }
            }
        }

    }
}
