package com.cq.io.io_01_file_io;

import java.io.*;

/**
 * @Author chenquan
 * @Date 2022-11-10 22:04
 * @Description TODO 测试 File IO 写入性能
 **/

public class FileIO {

    private static String PATH = "./io/data/out.txt";

    private static byte[] DATA = "hello".getBytes();

    public static void main(String[] args) throws Exception {

        if (args.length < 1){
            System.out.println("请输入参数！");
            System.exit(0);
        }
        if(args.length == 2){
            PATH = args[1];
        }

        switch (args[0]) {
            case "0":
                baseFileIO();
                break;
            case "1":
                bufferedFileIO();
                break;
        }

    }

    /**
     * FileOutputStream 和 BufferedOutputStream都是IO包下的 file操作，也就意味着效能比较差
     * BufferedOutputStream包装了FileOutputStream，默认大小是 8192 字节，是一个 byte[]
     * <p>
     * 也就是说，FileOutputStream 是一行一行的写，而 BufferedOutputStream 是再jvm层面攒一批 8k 再写
     * 导致了 FileOutputStream 产生更多的系统调用，每次系统调用都需要经历一次cpu上下文切换，这个过程很耗时
     */

    private static void baseFileIO() throws Exception {
        File file = new File(PATH);
        FileOutputStream out = new FileOutputStream(file);
        while (true) {
            out.write(DATA);
        }
        //TODO flush and close
    }

    private static void bufferedFileIO() throws Exception {
        File file = new File(PATH);
        //
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        while (true) {
            out.write(DATA);
        }
        //TODO flush and close
    }

}
