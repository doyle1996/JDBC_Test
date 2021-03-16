package com.jdbc.preparestatement2;

//updateTest
//prepareStatement代替statement 实现增删改查

import com.jdbc.connection1.ConnectionTest;
import com.jdbc.util.JDBCUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class PrepareStatementTest {
    public static void main(String[] args) throws Exception {
//        testInsert();
//        testUpdate();
//        testDelete();
//        testupdate();


    }

    //1.customer添加一条数据 add
   public static void testInsert() throws Exception {
        Connection conn= null;
        PreparedStatement preparedStatement= null;
        try {
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
            conn = DriverManager.getConnection(url,user,psw);
            // System.out.println(conn);
            //预编译sql语句，返回preparestatement的实例
            String sql="insert into customers(name,email,birth)values(?,?,?)";//?是占位符
            preparedStatement = conn.prepareStatement(sql);
//        填充占位符
            preparedStatement.setString(1,"哪吒");
            preparedStatement.setString(2,"zhang@qq.com");
            //日期
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date=simpleDateFormat.parse("1000-01-01");
            preparedStatement.setDate(3,new Date(date.getTime()));

            //执行sql
            preparedStatement.execute();
            //资源关闭
        } catch (Exception e) {
              e.printStackTrace();
        } finally {
            try {
                if (preparedStatement!=null)
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {if(conn!=null)
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }





    }



    //2.修改customer表的一条记录
    public static void testUpdate() throws Exception {
//        1.数据库连接
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = JDBCUtils.getConnection();
//        2.预编译sql语句，返回PrepareStatement的实例
            String sql="update customers set name=? where id=?";
            preparedStatement = conn.prepareStatement(sql);
//        3.填充占位符
            preparedStatement.setString(1,"周淑怡");
            preparedStatement.setString(2,"20");
//        4.excute
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
//            5.资源关闭
            JDBCUtils.closeResource(conn,preparedStatement);

        }
//


    }



    //3,customer表的删除操作
    public static void testDelete() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        String sql="delete from customers where id=?";
        PreparedStatement preparedStatement=conn.prepareStatement(sql);
        preparedStatement.setString(1,"19");
        preparedStatement.execute();
        JDBCUtils.closeResource(conn,preparedStatement);


    }





    //4.通用的增删改 ****
    public static void update(String sql, Object...args) {
        //连接数据库
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = JDBCUtils.getConnection();
            //预编sql语句，返回prepareStatement实例
            preparedStatement = conn.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setString(i + 1, (String) args[i]);
            }
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, preparedStatement);

        }


    }

    public static void testupdate() {
        String sql = "delete from customers where id=?";
        update(sql, 3);
//        String sql = "update `order` set order_name=? where order_id=?";
        update(sql, "bbq", 4);
    }


}
