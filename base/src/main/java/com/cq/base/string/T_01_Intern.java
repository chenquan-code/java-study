package com.cq.base.string;

/**
 * @Author chenquan
 * @Date 2022-10-30 14:14
 * @Description 测试String类的intern方法
 *
 * String::intern()是一个本地方法，它的作用是如果字符串常量池中已经包含一个等于此String对象的
 * 字符串，则返回代表池中这个字符串的String对象的引用；否则，会将此String对象包含的字符串添加
 * 到常量池中，并且返回此String对象的引用。
 *
 **/

public class T_01_Intern {
    public static void main(String[] args) {
        // "hello" 编译时已经在字符串常量池。所以变量a,b指向同一个地址
        String a = "hello";
        String b = "hello";
        System.out.println(a == b);


        String c = new String("word");
        c.intern(); // 此时字符串常量池已经有"word"，不会放入，返回常量池的地址，这地址跟c不一样，c是堆里的地址，但是跟d一样，因为d是从常量池里面取得
        String d = "word";
        System.out.println(c == d);

        // 执行完e,堆内生成的是new ("helloword")对象，此时堆里面只有"hello"和"word",没有"helloword"
        String e = new String("hello") + new String("word");
        e.intern(); // 此时常量池里还没有"helloword",所以把new ("helloword") 对象的引用放到字符串常量池里，并返回这个引用的地址
        String f = "helloword"; // 这里取到的其实就是上面放进去的指向堆的对象的地址，所以跟e是指向同一个地址
        System.out.println(e == f); // 如果注释掉e.intern()，则输出false，因为"helloword"是真正放到字符串常量池的对象，而不是指向堆的引用


    }
}
