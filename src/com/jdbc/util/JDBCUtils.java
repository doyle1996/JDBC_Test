package com.jdbc.util;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//操作数据库的工具类
public class JDBCUtils {
    public static Connection getConnection() throws Exception{
        //读取配置文件中的四个信息
        InputStream is=ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(is);
        String user=properties.getProperty("user");
        String psw=properties.getProperty("psw");
        String url=properties.getProperty("url");
        String driverClass=properties.getProperty("driverClass");
        //加载驱动
        Class.forName(driverClass);
        //获取连接
        Connection conn = DriverManager.getConnection(url,user,psw);
        return conn;
    }
    public static void closeResource(Connection conn,PreparedStatement preparedStatement){
        try {if(conn!=null)
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {if(preparedStatement!=null)
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void closeResource(Connection conn, PreparedStatement preparedStatement, ResultSet resultSet){
        try {if(conn!=null)
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {if(preparedStatement!=null)
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {if(resultSet!=null)
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
