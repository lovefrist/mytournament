package com.example.firsttopic.mysqlConnect;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;

/**
 * 数据库工具类：连接数据库用、获取数据库数据用
 * 相关操作数据库的方法均可写在该类
 */
public class Adddata {

    private static String driver = "com.mysql.jdbc.Driver";// MySql驱动

//    private static String url = "jdbc:mysql://localhost:3306/map_designer_test_db";

    private static String user = "root";// 用户名

    private static String password = "admin";// 密码

    private static Connection getConn(String dbName) {

        Connection connection = null;
        try {
            Class.forName(driver);// 动态加载类
            String ip = "39.106.192.145";// 写成本机地址，不能写成localhost，同时手机和电脑连接的网络必须是同一个

            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + dbName, user, password);
            Log.d("连接数据库", "getConn: 成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static boolean setadddata(String name, String mpassword) {
        HashMap<String, Object> map = new HashMap<>();
        // 根据数据库名称，建立连接
        Connection connection = getConn("LogText");
        try {
            // mysql简单的查询语句。这里是根据MD_CHARGER表的NAME字段来查询某条记录
            String sql = "INSERT INTO user (user, password) VALUES (?, ?)";
//            String sql = "select * from MD_CHARGER";
            if (connection != null) {// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);
                Log.d("连接成功", "连接成功: ");
                if (ps != null) {
                    // 设置上面的sql语句中的？的值为name
                    ps.setString(1, name);
                    ps.setString(2, mpassword);
                    Log.d("sql的内容是", "" + ps);
                    // 执行sql查询语句并返回结果集
                    int rs = ps.executeUpdate();
                    Log.d("行数",""+rs);
                    return true;

                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DBUtils", "异常：");
            return false;
        }

    }

}
