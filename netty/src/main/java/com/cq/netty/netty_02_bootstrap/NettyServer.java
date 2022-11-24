package com.cq.netty.netty_02_bootstrap;

import com.cq.netty.netty_01_mode.handler.MyInHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Author chenquan
 * @Date 2022-11-24 21:59
 * @Description: TODO
 * @Version: 1.0
 **/

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("【服务端】启动...");
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        ServerBootstrap bootstrap = new ServerBootstrap();
        ChannelFuture bind = bootstrap.group(group, group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new MyInHandler());
                    }
                }).bind(new InetSocketAddress("localhost", 9090));

        bind.sync().channel().closeFuture().sync();
        System.out.println("【服务端】结束...");
    }
}
