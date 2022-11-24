package com.cq.netty.netty_01_mode.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @Author chenquan
 * @Date 2022-11-24 21:28
 * @Description: TODO
 * @Version: 1.0
 **/

public class MyInHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【MyInChannel】Registered... ");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【MyInChannel】Active... ");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        // TODO readCharSequence会移动指针，会导致后面就读不到东西了，所以用 getCharSequence
//        CharSequence charSequence = buf.readCharSequence(buf.readableBytes(), StandardCharsets.UTF_8);
        CharSequence charSequence = buf.getCharSequence(0, buf.readableBytes(), StandardCharsets.UTF_8);
        System.out.println("【MyInChannel】收到消息：" + charSequence);
        ctx.writeAndFlush(charSequence);
    }
}
