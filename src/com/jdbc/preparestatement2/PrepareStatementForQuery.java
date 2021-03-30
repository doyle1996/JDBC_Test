package com.jdbc.preparestatement2;


import com.jdbc.bean.Customer;
import com.jdbc.bean.Order;
import com.jdbc.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class PrepareStatementForQuery {
    public static void main(String[] args) {
        testGetInstance();
        test();


    }



    //通用的查看操作 返回1条记录******

    public static void testGetInstance() {
        String sql = "select id,name,email from customers where id=?";
        Customer instance1 = getInstance(Customer.class, sql, 2);
        System.out.println(instance1);
        String sql1 = "select order_id orderId ,order_name orderName ,order_date orderDate from `order` where order_id=?";
        Order instance2 = getInstance(Order.class, sql1, 2);
        System.out.println(instance2);

    }

    public static <T> T getInstance(Class<T> clazz, String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
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
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);

        }
        return null;
    }


    //通用的查看操作 返回多条记录******


    public static void test() {
        String sql = "select order_id orderId ,order_name orderName ,order_date orderDate from `order` where order_id<?";
        List<Order> forList = getForList(Order.class, sql, 10);
//        forList.forEach(System.out::println);
        for (int i = 0; i < forList.size(); i++) {
            System.out.println(forList.get(i));

        }

    }

    public static <T> List<T> getForList(Class<T> clazz, String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);


            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            //create 集合对象
            ArrayList<T> list = new ArrayList<>();
            while (resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = resultSet.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field declaredField = clazz.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(t, columnValue);
                }
                list.add(t);

            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);

        }
        return null;


    }


}
