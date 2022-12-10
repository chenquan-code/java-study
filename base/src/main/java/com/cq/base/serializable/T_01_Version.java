package com.cq.base.serializable;

import com.cq.base.serializable.util.Reader;
import com.cq.base.serializable.util.Writer;

import java.io.File;


public class T_01_Version {

    public static void main(String[] args) throws Exception {

        // 实例化一个Person对象：张三
        Person zhangsan = new Person("张三", 18, "male");

        // 创建文件
        File file = new File("D:\\tmp\\zhangsan.out");

        // 序列化到磁盘
        Writer.write(zhangsan, file);

        // 反序列化
        Object newzhangsan = Reader.read(file);

        // 输出
        System.out.println(newzhangsan);


    }
}
