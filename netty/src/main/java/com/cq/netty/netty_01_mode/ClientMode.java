package com.cq.netty.netty_01_mode;

import com.cq.netty.netty_01_mode.handler.MyInHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author chenquan
 * @Date 2022-11-24 20:16
 * @Description: TODO 使用 Netty API 实现比较原始的 Socket 客户端
 * @Version: 1.0
 **/

public class ClientMode {

    public static void main(String[] args) throws Exception {

        System.out.println("【客户端】启动...");
        // TODO NioEventLoopGroup 底层维护了一组 Selector （多路复用器）
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        // TODO Netty 对 Java NIO SocketChannel 的包装
        NioSocketChannel client = new NioSocketChannel();
        // TODO 相当于 调用了 epoll_ctl(5,ADD,3)
        group.register(client);

        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(new MyInHandler());

        ChannelFuture connect = client.connect(new InetSocketAddress("localhost", 9090));
        // TODO 响应式编程，需要调用sync来同步，不然代码往下走很快就会退出
        ChannelFuture connectSync = connect.sync();

        ByteBuf buf = Unpooled.copiedBuffer("hello\n".getBytes());
        ChannelFuture send = client.writeAndFlush(buf);
        send.sync();

        connectSync.channel().closeFuture().sync();

        System.out.println("【客户端】结束...");

    }


}