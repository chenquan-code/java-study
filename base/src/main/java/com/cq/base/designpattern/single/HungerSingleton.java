package com.cq.base.designpattern.single;

/**
 * 单例模式-饿汉式
 */
public class HungerSingleton {

    // 类被类加载器加载是就实例化对象
    private static final HungerSingleton singleton = new HungerSingleton();

    // 私有化构造方法
    private HungerSingleton(){

    }

    // 提供外部访问的唯一入口
    public static HungerSingleton getInstance(){
        return singleton;
    }

}
