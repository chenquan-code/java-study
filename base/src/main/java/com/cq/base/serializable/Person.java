package com.cq.base.serializable;

import java.io.Serializable;

/**
 * 实现序列化接口
 * 这样Person类的对象就可以被序列化到磁盘
 */
public class Person implements Serializable {

    /**
     * 序列化ID(版本号)
     */
    private static final long serialVersionUID = 1L;

    private String name;

    private int  age;

    private String sex;

    // 测试增加一个属性
    private String email;

    public Person(){

    }

    public Person(String name, int age, String sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}
