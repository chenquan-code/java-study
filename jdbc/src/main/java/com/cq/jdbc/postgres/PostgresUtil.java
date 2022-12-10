package com.cq.jdbc.postgres;

/**
 * @Author chenquan
 * @Description
 * @Date 2022/6/21 9:02
 **/

import java.sql.*;

public class PostgresUtil {

    private static volatile Connection conn;

    /**
     * @method getConn() 获取数据库的连接
     * @return Connection
     */
    public static Connection getConn() {
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://node5:5432/test";
        String username = "flink_cdc";
        String password = "flink_cdc";
        if (conn == null){
            synchronized (PostgresUtil.class){
                if (conn == null){
                    try {
                        Class.forName(driver); // classLoader,加载对应驱动
                        conn = (Connection) DriverManager.getConnection(url, username, password);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return conn;
    }

    public static void createTable(){
        Connection c = null;
        Statement stmt = null;
        try {
            Connection conn = PostgresUtil.getConn();
            System.out.println("Opened database successfully");

            stmt = conn.createStatement();
            String sql = "CREATE TABLE STUDENTS " +
                    "(ID              SERIAL  NOT NULL," +
                    " NAME            TEXT    NOT NULL, " +
                    " SEX             TEXT    NOT NULL, " +
                    " AGE             TEXT    NOT NULL)";
            stmt.executeUpdate(sql);
            System.out.println("Table created successfully");

            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * @method insert(Student student) 往表中插入数据
     * @return int 成功插入数据条数
     */
    public static int insert(Student student) {
        Connection conn = getConn();
        int i = 0;
        String sql = "insert into students (Name,Sex,Age) values(?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getSex());
            pstmt.setString(3, student.getAge());
            i = pstmt.executeUpdate();
//            pstmt.close();
//            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int insert_t1(String sql) {
        Connection conn = getConn();
        int i = 0;
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
//            pstmt.close();
//            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @method delete(Student student) 删除表中数据
     * @return int 成功删除表中数据条数
     */
    public static int delete(String name) {
        Connection conn = getConn();
        int i = 0;
        String sql = "delete from students where Name='" + name + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("resutl: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @method update(Student student) 更改表中数据
     * @return int 成功更改表中数据条数
     */
    public static int update(Student student) {
        Connection conn = getConn();
        int i = 0;
        String sql = "update students set Age='" + student.getAge() + "' where Name='" + student.getName() + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("resutl: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @method Integer getAll() 查询并打印表中数据
     * @return Integer 查询并打印表中数据
     */
    public static Integer getAll() {
        Connection conn = getConn();
        String sql = "select * from students";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("============================");
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }
                System.out.println("");
            }
            System.out.println("============================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
