package com.cq.base.collection.list;

import java.util.Arrays;
import java.util.List;

/**
 * @Author chenquan
 * @Date 2022-11-06 1:00
 * @Description
 **/

public class TestArrays {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "java");
        System.out.println(list);
        // TODO asList 得到的只是一个 Arrays 的内部类，是一个原来数组的视图 List，因此如果对它进行增删操作会报错。
        list.add("hadoop");
        System.out.println(list);

    }
}
