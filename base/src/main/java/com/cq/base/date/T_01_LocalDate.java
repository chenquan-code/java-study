package com.cq.base.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author chenquan
 * @Date 2022-12-10 23:39
 * @Description: TODO 测试 LocalDate
 * @Version: 1.0
 **/

public class T_01_LocalDate {
    public static void main(String[] args) {
        // 0:当前时间  1:昨天  -1:明天
        LocalDate reportDate = LocalDate.now().minusDays(1);
        System.out.println("昨天：" + reportDate);
        String format = reportDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println("格式化：" + format);
    }
}
