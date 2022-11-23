package com.cq.io.io_04_socket_multiplexing_group;

/**
 * @Author chenquan
 * @Date 2022-11-23 17:41
 * @Description: TODO 主线程
 * @Version: 1.0
 **/

public class Main {
    public static void main(String[] args) {

        // TODO 启动两个boss线程，用来绑定两个端口，注册 Listen
        SelectorThreadGroup boss = new SelectorThreadGroup(2);
        // TODO 启动三个works线程，用来注册Client
        SelectorThreadGroup works = new SelectorThreadGroup(3);
        // TODO 把工作线程注册到boss线程上
        boss.setWork(works);
        // TODO 绑定两个端口
        boss.bind(9090);
        boss.bind(7070);
    }
}
