package com.cq.netty.netty_01_mode.handler;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author chenquan
 * @Date 2022-11-24 21:33
 * @Description: TODO
 * @Version: 1.0
 **/

public class MyAcceptHandler extends ChannelInboundHandlerAdapter {

    private final EventLoopGroup selector;
    private final ChannelHandler handler;

    public MyAcceptHandler(EventLoopGroup thread, ChannelHandler channelHandler) {
        this.selector = thread;
        this.handler = channelHandler;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【MyAcceptHandler】 Registered...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SocketChannel client = (SocketChannel) msg;
        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(handler);
        selector.register(client);
    }
}
