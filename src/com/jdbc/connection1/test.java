package com.jdbc.connection1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class test {
    public static void main(String[] args) {
        try {
            //加载驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn;

            String DB_URL="jdbc:mysql://localhost:3306/student?useSSL=false&serverTimezone=Asia/Shanghai";
            //testjdbc是数据库的名字
            try {
                conn = DriverManager.getConnection(DB_URL,"root","ylmn750716");
                //建立连接
                System.out.println(conn);//打印对象，看是否建立成功
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}