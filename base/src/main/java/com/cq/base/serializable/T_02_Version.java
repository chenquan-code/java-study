package com.cq.base.serializable;

import com.cq.base.serializable.util.Reader;

import java.io.File;

/**
 * 测试Version2L
 * 给Person类添加email属性，不修改serialVersionUID，则读取磁盘中旧的序列化对象成功
 * 给Person类添加email属性，修改Person的serialVersionUID=2L,表明已经弃用1L版本，读取失败
 * 意思就是，如果Person做的修改不能向前兼容的话，我们就需要改变serialVersionUID的值，如果是可以像后兼容，就不修改
 */
public class T_02_Version {

    public static void main(String[] args) throws Exception {

        // 创建文件
        File file = new File("D:\\tmp\\zhangsan.out");

        // 反序列化
        Object newzhangsan = Reader.read(file);

        // 输出
        System.out.println(newzhangsan);


    }
}
