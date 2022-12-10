package com.cq.jvm.jvm_02_classloader;

import java.io.*;

/**
 * @Author chenquan
 * @Description 自定义类加载器，实现从文件中加载class
 *  自定义ClassLoader有两种方式
 *  1、继承ClassLoader类，重写loadClass()方法，打破双亲委派机制，loadClass()中也调用了findClass()
 *  也就是说，重写loadClass()也得重写findClass()？
 *  2、继承ClassLoader类，重写findClass()方法，继续使用双亲委派机制，但是补充去加载某些类
 *  建议使用重写findClass()，进行小范围的改动，但是保留双亲委派机制的模型
 * @Date 2022-04-13 23:03
 **/

public class FileClassLoader extends ClassLoader {

    private String path;
    private String name;

    public FileClassLoader() {
    }

    public FileClassLoader(String path, String name) {
        this.path = path;
        this.name = name;
    }

    //TODO 重写父类的findClass方法
    @Override
    protected Class<?> findClass(String className) {
        byte[] b = loadClassData(className);
        //TODO 这里调用的还是父类的方法，跟进源码可以查看到时个native方法，是C语言的实现
        return defineClass(className, b, 0, b.length);
    }

    // TODO 将class文件以字节数组的形式读取
    private byte[] loadClassData(String className) {

        //TODO 拼接路劲
        String fullPath = path + "\\" +className + ".class";
        //TODO IO流操作，Java基础
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(new File(fullPath));
            out = new ByteArrayOutputStream();
            int i = 0;
            byte[] bytes = new byte[1024];
            while ((i = in.read(bytes)) != -1) {
                out.write(bytes);
            }
        } catch (Exception e) {
//            System.out.println("class not fund !" + fullPath);
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out.toByteArray();
    }
}
