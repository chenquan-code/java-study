package com.cq.netty.netty_02_bootstrap;

import com.cq.netty.netty_01_mode.handler.MyInHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @Author chenquan
 * @Date 2022-11-24 21:50
 * @Description: TODO 使用 bootstrap 创建客户端
 * @Version: 1.0
 **/

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("【客户端】启动...");
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        ChannelFuture connect = bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new MyInHandler());
                    }
                })
                .connect(new InetSocketAddress("localhost", 9090));
        Channel client = connect.sync().channel();
        ByteBuf buf = Unpooled.copiedBuffer("hello\n".getBytes(StandardCharsets.UTF_8));
        ChannelFuture write = client.writeAndFlush(buf);
        write.sync();
        client.closeFuture().sync();

        System.out.println("【客户端】启动...");


    }
}
