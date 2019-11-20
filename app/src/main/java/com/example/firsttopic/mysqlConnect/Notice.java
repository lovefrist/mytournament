package com.example.firsttopic.mysqlConnect;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库工具类：连接数据库用、获取数据库数据用
 * 相关操作数据库的方法均可写在该类
 */
public class Notice {

    private static String driver = "com.mysql.jdbc.Driver";// MySql驱动

//    private static String url = "jdbc:mysql://localhost:3306/map_designer_test_db";

    private static String user = "root";// 用户名

    private static String password = "admin";// 密码

    private static Connection getConn(String dbName){

        Connection connection = null;
        try{
            Class.forName(driver);// 动态加载类
            String ip = "39.106.192.145";// 写成本机地址，不能写成localhost，同时手机和电脑连接的网络必须是同一个

            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + dbName, user, password);
            Log.d("连接数据库", "getConn: 成功");
        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }

    public static  List<Map> getInfoByName(){

        List<Map> list =new ArrayList();
        // 根据数据库名称，建立连接
        Connection connection = getConn("LogText");
        try {
            // mysql简单的查询语句。这里是根据MD_CHARGER表的NAME字段来查询某条记录
            String sql = "select * from notice";
//            String sql = "select * from MD_CHARGER";
            if (connection != null){// connection不为null表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);

                if (ps != null){
                    // 执行sql查询语句并返回结果集
                    ResultSet rs = ps.executeQuery();
                    if (rs != null){
                        int count = rs.getMetaData().getColumnCount();
                        Log.e("DBUtils","列总数：" + count);
                        while (rs.next()){
                            // 注意：下标是从1开始的
                            for (int i = 1;i <= count;i++){
                                HashMap<String, Object> map = new HashMap<>();
                                String field = rs.getMetaData().getColumnName(i);
                                Log.d("key", "getInfoByName: 名字是"+field);
                                Log.d("view", "getInfoByName: "+rs.getString(field));
                                if (field.equals("content")){
                                    map.put(field, rs.getString(field));
                                    list.add(map);
                                }

                            }
                        }
                        connection.close();
                        ps.close();
                        return  list;
                    }else {
                        return null;
                    }
                }else {
                    return  null;
                }
            }else {
                return  null;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("DBUtils","异常：" + e.getMessage());
            return null;
        }

    }

}
