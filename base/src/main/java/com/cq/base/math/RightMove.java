package com.cq.base.math;

/**
 * @Author chenquan
 * @Date 2022-09-23 22:57
 * @Description 测试右移操作
 **/

public class RightMove {

    public static void main(String[] args) {
        // 右移一位相当于 除以 2
        // 1 0 0 0
        // 0 0 1 0
        System.out.println(8 >> 2);

        // 1 1 1
        // 0 1 1 (1) -> 删除了  相当于 7 / 2
        System.out.println(7 >> 1);

        // 1 1 1
        // 0 0 1 (1 1) -> 删除了  相当于 7 / 4
        System.out.println(7 >> 2);


    }
}
