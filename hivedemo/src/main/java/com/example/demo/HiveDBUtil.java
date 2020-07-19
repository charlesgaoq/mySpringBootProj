package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HiveDBUtil {
    public static Connection conn=null;
 
    public static Connection getConn()throws Exception{
        if(conn==null){
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            System.out.println("开始连接");
            conn=  DriverManager.getConnection(
                    "jdbc:hive2://10.0.10.59:21050/default;auth=noSasl","hive","");
            System.out.println("connected");
        }
        return conn;
    }
 
    public static void close(Connection conn){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
 
    public static void main(String[] args) {
        try{
            System.out.println(getConn());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
 
}