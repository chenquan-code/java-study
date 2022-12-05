package com.cq.util.util_01_notes;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author chenquan
 * @Date 2022-11-26 17:49
 * @Description: TODO Java 常用工具记录
 * @Version: 1.0
 **/
public class CommonUtil {

    private final AtomicInteger idx = new AtomicInteger();

    private final int[] nums = new int[]{1, 2, 3, 4, 5};

    @Test
    public void test01() {
        // TODO 获取机器的CPU核数
        int coreNum = Runtime.getRuntime().availableProcessors();
        // TODO 添加JVM关闭勾子
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("JVM 关闭啦！");
            }
        }));
        System.out.println("【CPU核数】：" + coreNum);
    }

    @Test
    public void test02() {
        // TODO round-robin to choose next 简单的轮询选择下一个
        for (int i = 0; i < 10; i++) {
            System.out.println("-------------------------------------------");
//            System.out.println(nums[idx.getAndIncrement() & nums.length - 1]);
            System.out.println(nums[idx.getAndIncrement() % nums.length]);
            System.out.println("-------------------------------------------");
        }
    }


}
