package com.cq.io.io_01_file_io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author chenquan
 * @Date 2022-11-10 22:04
 * @Description TODO 测试 File IO 写入性能
 **/

public class FileIO {

    private static String PATH = "/home/study/java-study/out.txt";

    private static byte[] DATA = "hello".getBytes();

    public static void main(String[] args) throws Exception {

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
     *  TODO 测试 FileOutputStream 写入的速度
     * @throws Exception
     */
    private static void baseFileIO() throws Exception {
        File file = new File(PATH);
        FileOutputStream out = new FileOutputStream(file);
        while (true) {
            out.write(DATA);
        }
    }

    /**
     * TODO 测试 BufferedOutputStream 写入速度
     * @throws Exception
     */
    private static void bufferedFileIO() throws Exception {
        File file = new File(PATH);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        while (true) {
            out.write(DATA);
        }
    }
}
