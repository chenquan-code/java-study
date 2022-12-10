package com.cq.jdbc.postgres;

/**
 * @Author chenquan
 * @Description
 * @Date 2022/6/21 9:00
 **/

public class PostgresSink {

    public static void main(String[] args) {

        PostgresSinkThread zs = new PostgresSinkThread("张三");
        PostgresSinkThread ls = new PostgresSinkThread("李四");
        PostgresSinkThread ww = new PostgresSinkThread("王五");
        new Thread(zs).start();
        new Thread(ls).start();
        new Thread(ww).start();



    }
}
