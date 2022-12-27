package com.cq.base.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author chenquan
 * @Date 2022-12-28 0:05
 * @Description: TODO 需要配置 log4j.properties
 * @Version: 1.0
 **/

public class T_01_Log4j {
    // 创建 logger
    private static final Logger logger = LoggerFactory.getLogger(T_01_Log4j.class);

    public static void main(String[] args) {
        String message = "log4j";
        logger.info("测试 {}", message); // 输出 2022-12-28 00:16:06 INFO  T_01_Log4j:19 - 测试 log4j
    }
}
