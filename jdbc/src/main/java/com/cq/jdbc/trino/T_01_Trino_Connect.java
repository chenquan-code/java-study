package com.cq.jdbc.trino;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Author chenquan
 * @Description
 * @Date 2022/6/6 11:29
 **/

public class T_01_Trino_Connect {

    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("请输入SQL");
            return;
        }
//        System.out.println("默认时区"+TimeZone.getDefault());
//        Date d1 = new Date();
//        System.out.println("系统时区，设置前："+d1.toLocaleString());
//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"));
//        Date d2 = new Date();
//        System.out.println("系统时区，设置后："+d2.toLocaleString());


//
//        System.out.println(TimeZone.getDefault());
//        SimpleDateFormat dfOld = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(dfOld.format(new Date()));
//
//        final TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
//        TimeZone.setDefault(timeZone);
//        System.out.println(TimeZone.getDefault());
//        SimpleDateFormat dfNew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(dfNew.format(new Date()));

        System.out.println("SQL:" + args[0]);
        Class.forName("io.trino.jdbc.TrinoDriver");
        String url = "jdbc:trino://ip-172-29-2-241.ec2.internal:8889";
        Properties properties = new Properties();
        properties.setProperty("user", "presto");
        properties.setProperty("password", "");
        Connection connection = DriverManager.getConnection(url, properties);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(args[0]);
        while (rs.next()) {
            System.out.println("result: "+rs.getString(1));
        }
    }
}
