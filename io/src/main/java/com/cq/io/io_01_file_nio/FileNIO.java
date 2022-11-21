package com.cq.io.io_01_file_nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author chenquan
 * @Date 2022-11-21 21:42
 * @Description: TODO
 * @Version: 1.0
 **/

public class FileNIO {

    private static String PATH = "./io/data/out.txt";

    private static byte[] DATA = "hello".getBytes();

    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.out.println("请输入参数！");
            System.exit(0);
        }
        if (args.length == 2) {
            PATH = args[1];
        }

        switch (args[0]) {
            case "0":
                randomAccessFileIO();
                break;
            case "1":
                byteBuffer();
                break;
        }
    }

    /**
     * NIO提供了 RandomAccessFile，
     */

    private static void randomAccessFileIO() throws Exception {
        RandomAccessFile raf = new RandomAccessFile(PATH, "rw");
        raf.write("hello java".getBytes(StandardCharsets.UTF_8));
        System.out.println("写入成功！");
        raf.seek(6);
        raf.write("bigdata".getBytes(StandardCharsets.UTF_8));
        System.out.println("seek成功！");

        System.in.read();

        FileChannel channel = raf.getChannel();
        //TODO mmp 直接在堆外分配，并且跟内核的pagecache直接映射，map.put写数据是不需要经过系统调用,但依然只是将数据写到pagecache，依然可能丢数据
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 4090);
        map.put("MappedByteBuffer...".getBytes(StandardCharsets.UTF_8));
        System.out.println("MappedByteBuffer put成功！");

        // 相当于flush刷写磁盘
//        map.force();
        //TODO flush and close
    }


    /**
     * 切换到读模式
     * Buffer flip();
     *
     * 切换到写模式，不保留未读完数据。
     * Buffer clear();
     *
     * 切换到写模式，保留未读完数据。
     * ByteBuffer compact();
     */

    private static void byteBuffer() {
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        System.out.println(buffer);

        // TODO 写入5个字符
        buffer.put("hello".getBytes(StandardCharsets.UTF_8));
        System.out.println("写入hello：" + buffer);
        // 读出一个字符，注意这样是读不到的
//        byte b1 = buffer.get();
//        System.out.println("读取一个字符：" + b1);
//        System.out.println(buffer);

        // TODO 读出一个字符需要先flip一下，让pos指针移动到第一个字符位置，lim指针移动到pos位置
        buffer.flip();
        byte b2 = buffer.get();
        System.out.println("flip()后读取一个字符：" + b2);
        System.out.println(buffer);

        //TODO 再次写入是需要再次调整一下指针，不然数据就写错位置了

        buffer.compact();
        buffer.put("java".getBytes(StandardCharsets.UTF_8));
        System.out.println("写入java：" + buffer);

        // TODO 读取全部数据
        buffer.flip();
        byte[] bytes = new byte[buffer.limit()];
        buffer.get(bytes);
        System.out.println("读取全部数据：" + new String(bytes));


        buffer.clear();
        System.out.println("清空数据：" + buffer);


    }

}
