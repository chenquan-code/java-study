package com.cq.netty.netty_01_mode.handler;

import io.netty.channel.*;

/**
 * @Author chenquan
 * @Date 2022-11-24 21:40
 * @Description: TODO 为了不让用户写代码时频繁写@ChannelHandler.Sharable注解，就写了一个统一的，不做业务处理的channel
 * 每个线程调用过来之后都会帮你new 一个 新的 MyInHandler，这样就不会存在多线程的问题
 * 这里可以优化一下，MyInHandler可以用户自己传进来
 * @Version: 1.0
 **/

@ChannelHandler.Sharable
public class MyInitChannel extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel client = ctx.channel();
        ChannelPipeline p = client.pipeline();
        p.addLast(new MyInHandler());
        ctx.pipeline().remove(this);
    }
}
