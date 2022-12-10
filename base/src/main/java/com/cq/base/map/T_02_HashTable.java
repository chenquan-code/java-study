package com.cq.base.map;

import java.util.Hashtable;

/**
 * @Author chenquan
 * @Date 2022-09-23 23:20
 * @Description 测试 Hashtable
 **/

public class T_02_HashTable {
    public static void main(String[] args) {
        // 初始容量：Constructs a new, empty hashtable with a default initial capacity (11) and load factor (0.75).
        Hashtable<String, Object> hashTable = new Hashtable<>();
        // 发现所有方法都用synchronized
        hashTable.put("1","hello");
        // 很明显get方法是不存在一致性问题的，但是他依然加synchronized
        hashTable.get("1");
    }
}
