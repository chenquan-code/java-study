package com.cq.io.io_03_socket_multiplexing;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author chenquan
 * @Date 2022-11-22 1:20
 * @Description: TODO 测试 单线程 方式实现多路复用器
 * 添加写事件的处理
 * 实现的功能：将响应内容放到写事件去处理，定制化回复客户端发送的内容
 * <p>
 * 由于OP_WRITE事件的调用逻辑是send-queue队列是否为空，
 * @Version: 1.0
 **/

public class SocketMultiplexingV2 {

    private ServerSocketChannel server = null;
    private Selector selector = null;
    private Integer port = 9090;


    public static void main(String[] args) {
        SocketMultiplexingV2 v1 = new SocketMultiplexingV2();
        v1.start();
    }

    public void start() {
        initServer();
        System.out.println("服务已启动...");
        try {
            while (true) {
                Set<SelectionKey> keys = selector.keys();
                System.out.println("【所有】selectionKeys " + keys.size() + " 个");
                /**
                 *  1. select,poll: 调内核的 select（fd4）  poll(fd4)
                 *  2. epoll:  read(3, "\312\376\272\276\0\0\0004\0\t\7\0\7\7\0\10\1\0\tinterrupt\1\0\25("..., 162) = 162
                 *             epoll_ctl(7, EPOLL_CTL_ADD, 4, {EPOLLIN, {u32=4, u64=140033113718788}}) = 0
                 *             epoll_wait(7,
                 */
                // TODO 这里会不断循环进入 epoll_wait (阻塞)，可以设置超时事件（非阻塞）
                while (selector.select(1000) > 0) {
                    // TODO 拿到有状态的集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    System.out.println("【有状态】selectionKeys" + selectionKeys.size() + " 个");
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        // TODO 根据不同的类型处理
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
//                            key.cancel();
                            readHandler(key);
                        } else if (key.isWritable()) {
//                            key.cancel();
                            writeHandler(key);
                        }
                        System.out.println("【有状态】selectionKeys轮询完毕！");
                    }
                }
                System.out.println("【所有】selectionKeys循环完毕，继续进行下次循环...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * OP_WRITE事件触发条件是 send-queue 是不是空闲的，是空闲的就会回调我们的writeHandler
     * 所以这里如果不执行key.cancel就会一直不断被调用
     * 解决方案就是执行完writeHandler后设置selector只关心OP_READ事件
     *
     * @param key
     */
    private void writeHandler(SelectionKey key) {
        System.out.println("【执行writeHandler】...");
        try {
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buff = (ByteBuffer) key.attachment();
            buff.flip();
            while (buff.hasRemaining()) {
                client.write(buff);
            }
            buff.clear();
            // TODO 解决被循环调起
            key.interestOps(SelectionKey.OP_READ);
//            key.cancel();
//            client.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void readHandler(SelectionKey key) {
        System.out.println("【执行readHandler】...");
        try {
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buff = (ByteBuffer) key.attachment();
            System.out.println("收到读取事件...");
            buff.clear();
            while (true) {
                int read = client.read(buff);
                if (read > 0) {
                    buff.flip();
                    byte[] bytes = new byte[buff.limit()];
                    buff.get(bytes);
                    System.out.println("收到客户端" + client.getRemoteAddress() + "发送的数据：" + new String(bytes));
                    buff.clear();
                    // TODO 定制化回复数据，并注册到 OP_WRITE 事件，并由 OP_WRITE 事件处理器处理
                    buff.put(("<hello>:" + Arrays.toString(bytes)).getBytes(StandardCharsets.UTF_8));
                    client.register(key.selector(), SelectionKey.OP_WRITE, buff);
                } else if (read == 0) {
                    break;
                } else {
                    client.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void acceptHandler(SelectionKey key) {
        System.out.println("【执行acceptHandler】...");
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        try {
            SocketChannel client = channel.accept();
            client.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // TODO elect，poll：jvm里开辟一个数组 fd7 放进去
            // TODO epoll：  epoll_ctl(7, EPOLL_CTL_ADD, 4, {EPOLLIN, {u32=4, u64=140415365808132}}) = 0
            // TODO         epoll_wait(7,
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("接收到新客户端：" + client.getRemoteAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            selector = Selector.open();
            // TODO 上来先注册一下OP_ACCEPT事件，不然后面客户端就连不进来了
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
