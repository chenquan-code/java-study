package com.cq.netty.netty_01_mode;

import com.cq.netty.netty_01_mode.handler.MyAcceptHandler;
import com.cq.netty.netty_01_mode.handler.MyInHandler;
import com.cq.netty.netty_01_mode.handler.MyInitChannel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author chenquan
 * @Date 2022-11-24 21:28
 * @Description: TODO
 * @Version: 1.0
 **/

public class ServerMode {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("【服务端】启动...");
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        NioServerSocketChannel server = new NioServerSocketChannel();

        group.register(server);

        ChannelPipeline pipeline = server.pipeline();

        // TODO 为什么需要一个MyInitChannel中转，因为不中转的话在多个线程的情况下会报错
//        pipeline.addLast(new MyAcceptHandler(group, new MyInHandler()));
        pipeline.addLast(new MyAcceptHandler(group, new MyInitChannel()));

        ChannelFuture bind = server.bind(new InetSocketAddress("localhost", 9090));

        bind.sync().channel().closeFuture().sync();

        System.out.println("【服务端】结束...");

    }
}
