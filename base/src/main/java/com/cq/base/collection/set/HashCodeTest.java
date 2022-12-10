package com.cq.base.collection.set;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author chenquan
 * @Date 2022-10-28 23:09
 * @Description
 * 为什么重写equals一定要重写hashcode方法？
 * equals()方法的作用：o1 == o2  判断两个对象是否具有相同的引用
 * hashcode的作用：返回对象的内存地址的hash值
 *
 **/

public class HashCodeTest {

    public static void main(String[] args) {
        // 在set里就体现出用处了，set需要对相同的key去重，如果重写了equals不重写hashcode,就会导致set去重失败
        // Set区别对象是不是唯一的标准是，两个对象hashcode是不是一样，再判定两个对象是否equals;

        Set<Student> students = new HashSet<>();
        Student s1 = new Student("zs", 18);
        Student s2 = new Student("zs", 18);


        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        System.out.println(s1.hashCode() == s2.hashCode());

        // 重写hashcode,set去重正确
        // 不重写hashcode，set去重失败
        students.add(s1);
        students.add(s2);
        System.out.println(students);


    }


}
