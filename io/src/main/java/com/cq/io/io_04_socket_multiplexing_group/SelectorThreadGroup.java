package com.cq.io.io_04_socket_multiplexing_group;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author chenquan
 * @Date 2022-11-23 17:41
 * @Description: TODO 维护一组 selectorThread
 * 需求：
 * 1. 初始化 selectorThread 数组 并将每个 selectorThread run 起来
 * 2. 实现 selectorThread 自动选择
 * 3. 实现端口绑定
 * @Version: 1.0
 **/

public class SelectorThreadGroup {

    SelectorThread[] selectors = null;

    ServerSocketChannel server = null;

    SelectorThreadGroup stg = this;

    AtomicInteger atomic = new AtomicInteger(0);

    public SelectorThreadGroup() {};

    public SelectorThreadGroup(int num) {
        selectors = new SelectorThread[num];
        for (int i = 0; i < num; i++) {
            selectors[i] = new SelectorThread();
            new Thread(selectors[i]).start();
        }
    }

    public void bind(int port) {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            // TODO 原本这里要注册的，现在是选一个selectorThread出来，放到它自身维护的queue里，让它自己去绑定
//            server.register(selectors[0], SelectionKey.OP_ACCEPT)
            nextSelector(server);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从 SelectorThread[] 里面选择一个 SelectorThread实例，并将 ServerSocketChannel 或 SocketChannel 传递给 SelectorThread实例，在那边做 Selector 的绑定
     *
     * @param channel
     */
    public void nextSelector(Channel channel) {
        try {
            SelectorThread s = next();
            s.queue.put(channel);
            s.setWork(stg);
            // TODO 如果selector.select()没有设置超时时间，这里就需要wakeup一下，不然那边会一直阻塞住
            s.selector.wakeup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public SelectorThread next() {
        int index = atomic.getAndIncrement() % selectors.length;
        return selectors[index];
    }

    public void setWork(SelectorThreadGroup stg) {
        this.stg = stg;
    }


}
