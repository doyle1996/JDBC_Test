package com.jdbc.connection1;



import java.io.InputStream;
import java.sql.Connection;

import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionTest {
    public static void main(String[] args) throws Exception {
        getConnection();

    }

//    法3
//
//    public static void testConnection() throws Exception{
//        //提供 url user psw
//        String url="jdbc:mysql://localhost:3306/family?useSSL=false&serverTimezone=Asia/Shanghai";
//        String user="root";
//        String psw="ylmn750716";
//        //获取driver实现类对象
//        Class clazz=Class.forName("com.mysql.cj.jdbc.Driver");

//        Driver driver=(Driver) clazz.newInstance();
    //注册驱动
//        DriverManager.registerDriver(driver);
//        DriverManager.getConnection(url,user,psw);
    //获取连接
//        Connection conn=DriverManager.getConnection(url,user,psw);
//        System.out.println(conn);
//    }

//    public static void testConnection() throws Exception{
//        //1.提供 url user psw
//        String url="jdbc:mysql://localhost:3306/family?useSSL=false&serverTimezone=Asia/Shanghai";
//        String user="root";
//        String psw="ylmn750716";
//        //2.获取driver实现类对象
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        //注释：静态代码块随着类加载而执行 static
//        //3.获取连接
//        DriverManager.getConnection(url,user,psw);
//        Connection conn=DriverManager.getConnection(url,user,psw);
//        System.out.println(conn);
//    }
    //法五

    public static void getConnection() throws Exception {
        InputStream is=ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(is);
        String user=properties.getProperty("user");
        String psw=properties.getProperty("psw");
        String url=properties.getProperty("url");
        String driverClass=properties.getProperty("driverClass");
        Class.forName(driverClass);
        Connection conn=DriverManager.getConnection(url,user,psw);
        System.out.println(conn);


    }
}
