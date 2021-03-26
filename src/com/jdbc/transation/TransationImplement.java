package com.jdbc.transation;

import com.jdbc.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
/*
数据库事务介绍
ACID
原子性 ：事务是不可分割的单位，事务中的操作要么都发生 要么都不发生
一致性 ：事务必须使数据库从一个一致性状态转换到另一个一致性状态
隔离性 ：一个事物执行时不被其他事务干扰
持久性 ：一个事务一旦提交，他对数据库中的数据的改变就是永久性的，即使之后有其他操作或者数据库故障不会对其有任何影响。

>DDL操作一旦执行都会自动提交                 set autocommit=false对DDL没用
>DML默认情况下一旦执行都会自动提交 可以通过set autocommit=false的方式取消DML操作的自动提交
>默认在关闭连接时会自动提交数据
*/

//针对数据表user_table 演示AA给BB转账100
public class TransationImplement {
    // *****************************

    //通用的查询操作，用于返回数据表中的一条记录(version2.0 考虑上事务)
    public static <T> T getInstance2(Connection connection, Class<T> clazz, String sql, Object... args) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = resultSet.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field declaredField = t.getClass().getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, preparedStatement, resultSet);
        }
        return null;
    }

    //通用的更新操作(version2.0 考虑上事务)
    public static int update2(Connection connection, String sql, Object... args) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JDBCUtils.closeResource(null, preparedStatement);
        }

        return 0;

    }


    //*********************考虑数据事务情况下的转账操作****************************
    public static void TrainsationTest2() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            //1.取消DML的自动提交功能
            connection.setAutoCommit(false);
            String sql1 = "update user_table set balance =balance-100 where user=?";
            update2(connection, sql1, "AA");
            //网络异常
            System.out.println(10 / 0);
            String sql2 = "update user_table set balance =balance+100 where user=?";
            update2(connection, sql2, "BB");
            System.out.println("转账成功");
            //2.提交数据
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                //3.出现异常 用roolback()回滚事务
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } finally {
            try {
                //修改其为自动提交 主要针对于数据库连接池的使用
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            JDBCUtils.closeResource(connection, null);
        }
    }

    //*********************未考虑数据事务情况下的转账操作****************************
    public static int update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connection, preparedStatement);
        }

        return 0;

    }

    public static void TransationTest() {


        String sql1 = "update user_table set balance =balance-100 where user=?";
        String sql2 = "update user_table set balance =balance+100 where user=?";

        update(sql1, "AA");
        //网络异常
        System.out.println(10 / 0);
        update(sql2, "BB");
        System.out.println("转账成功");
    }
}