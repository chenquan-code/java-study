package com.cq.base.designpattern.single;

/**
 * 测试类
 */
public class TestDesignPatterns {

    public static void main(String[] args) {

        // 测试饿汉式
        System.out.println("测试饿汉式");
        HungerSingleton instance1 = HungerSingleton.getInstance();
        HungerSingleton instance2 = HungerSingleton.getInstance();

        System.out.println("Hash Code:");
        System.out.println(instance1);
        System.out.println(instance2);

        System.out.println("=======================================");

        System.out.println("测试懒汉式");
        DoubleCheckSingleton instance3 = DoubleCheckSingleton.getInstance();
        DoubleCheckSingleton instance4 = DoubleCheckSingleton.getInstance();

        System.out.println("Hash Code:");
        System.out.println(instance3);
        System.out.println(instance4);

        System.out.println("=======================================");

        System.out.println("测试静态内部类");
        StaticInnerClassesSingleton instance5 = StaticInnerClassesSingleton.getInstance();
        StaticInnerClassesSingleton instance6 = StaticInnerClassesSingleton.getInstance();

        System.out.println("Hash Code:");
        System.out.println(instance5);
        System.out.println(instance6);

        System.out.println("=======================================");


    }
}
