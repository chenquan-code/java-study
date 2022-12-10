package com.cq.base.designpattern.single;

/**
 * 单例模式-静态内部类
 */
public class StaticInnerClassesSingleton {

    // 构造方法私有化
    private StaticInnerClassesSingleton(){}

    // 静态内部类,比较聪明，先藏到口袋里，类加载不会加载，只有调用了getInstance才会被加载
    private static class StaticInnerClassesInstance {
        static final StaticInnerClassesSingleton singleton = new StaticInnerClassesSingleton();
    }

    // 外部访问唯一入口
    public static StaticInnerClassesSingleton getInstance(){
        return StaticInnerClassesInstance.singleton;
    }

}
