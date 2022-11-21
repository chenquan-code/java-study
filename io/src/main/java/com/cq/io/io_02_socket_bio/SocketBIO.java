package com.cq.io.io_02_socket_bio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author chenquan
 * @Date 2022-11-10 23:44
 * @Description: TODO 测试 Socket BIO 的特性
 * 1. 阻塞：accept是阻塞执行的，一直到有客户端连接进来才会进行下一步
 * 2. 这个模型一个线程只能服务一个socket
 * 2. Linux启动并追踪程序：javac SocketBIO.java && strace -ff -o out java SocketBIO
 * @Version: 1.0
 **/

public class SocketBIO {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9090);
        System.out.println("serverSocket（9090）已启动!");
        // 当键盘按下回车后才会执行后面的代码
        System.in.read();
        // TODO 死循环，这样服务端才能一直能接收客户端的请求
        while (true) {
            // TODO 等待客户端的请求，这里会阻塞，所以叫 BIO
            Socket client = serverSocket.accept();
            System.out.println("serverSocket accept success , client port " + client.getPort());
            // TODO 抛出一个线程去执行读写方法，这里是为了让主线程能继续执行 accept 处理后面的客户端请求
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream in = client.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        while (true) {
                            // TODO 这里 readLine 也是一个阻塞行为，也是 BIO 的一个体现
                            System.out.println("正在准备读取一行数据！");

                            String line = reader.readLine();
                            if (line != null) {
                                System.out.println("服务端收到：" + line);
                            } else {
                                client.close();
                                break;
                            }
                            System.out.println("成功读取到一行数据！");
                        }
                        System.out.println("客户端已断开连接：" + client.getPort());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


    }
}
