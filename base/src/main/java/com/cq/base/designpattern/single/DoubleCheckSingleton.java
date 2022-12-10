package com.cq.base.designpattern.single;

/**
 * 单例模式-双重检查懒汉式
 */
public class DoubleCheckSingleton {

    // 静态成员变量,比较懒，先不初始化
    private static volatile DoubleCheckSingleton singleton;

    // 私有化构造方法
    private DoubleCheckSingleton(){}

    // 外部访问唯一入口
    public static DoubleCheckSingleton getInstance(){

        if (singleton == null){
            // 同步锁锁代码块，效率更高
            synchronized (DoubleCheckSingleton.class){
                if (singleton == null){
                    singleton = new DoubleCheckSingleton();
                }

            }
        }
        return singleton;

    }


}
