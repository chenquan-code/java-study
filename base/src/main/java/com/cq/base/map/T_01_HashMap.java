package com.cq.base.map;

import java.util.HashMap;

/**
 * @Author chenquan
 * @Date 2022-09-23 21:18
 * @Description
 **/

public class T_01_HashMap {
    public static void main(String[] args) {
        // Constructs an empty HashMap with the default initial capacity (16) and the default load factor (0.75).
        // 初始容量16个元素，加载因子为0.75
        // 初始容量： newCap = DEFAULT_INITIAL_CAPACITY;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("1","hello");
        // 插入元素逻辑：
        // if map 为空，初始化一个长度为16的hashmap
        // else if map 不为空，并且长度不超过16， 并且当前插入的key没有hash冲突，直接插入
        // else 扩容 newCap = oldCap << 1
        for (int i = 0 ; i <= 16 ; i++){
            hashMap.put(String.valueOf(i),i);
        }







    }
}
