package com.jdbc.exercise;

import com.jdbc.util.JDBCUtils;

import java.sql.*;
import java.util.Scanner;

/*
 * 从控制台向数据库的表customer中插入一条数据
 * */
public class Exercise1 {
    public static void main(String[] args)  {
        InsertTest();

    }
    public static void InsertTest()  {

            Scanner scanner = new Scanner(System.in);
            System.out.println("input name:");
            String name=scanner.next();
            System.out.println("input email:");
            String email=scanner.next();
            System.out.println("input birth");
            String birth=scanner.next();
            String sql="insert into customers(name,email,birth)value(?,?,?)";
            int insertCount=update(sql,name,email,birth);
            if(insertCount>0){
                System.out.println("success");

            }
            else {
                System.out.println("error");
            }



    }
//通用的增删改操作
    public static int update(String sql, Object... args)  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            /*
            *   preparedStatement.execute()如果是查询工作返回的是true 如果是增删改操作返回的false
            * */
//            preparedStatement.execute();
            /*
            preparedStatement.executeUpdate如果添加成功行数增加
            * */
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connection, preparedStatement);
        }

        return 0;

    }
}
