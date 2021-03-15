package com.jdbc.preparestatement2;

import com.jdbc.bean.Order;
import com.jdbc.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;

//针对order表的通用查询工作
public class OrderForQuery {
    public static void main(String[] args) {
//        QueryForOrder1();
        test();


    }

//不加参数的Order表查询
 /*
  public static void QueryForOrder1()  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql="select order_id,order_name,order_date from `order` where order_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,1);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int id = (int) resultSet.getObject(1);
                String name = (String) resultSet.getObject(2);
                Date date= (Date) resultSet.getObject(3);
                Order order = new Order(id, name, date);
                System.out.println(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection,preparedStatement,resultSet);
        }

    }*/

    //加参数的Order表查询
    public static  void test(){
        String sql="select order_id orderId ,order_name orderName ,order_date orderDate from `order` where order_id=?";
        Order order = QueryForOrder2(sql, 2);
        System.out.println(order);

    }

    public static Order QueryForOrder2(String sql, Object... args) {
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
            if (resultSet.next()) {
                Order order = new Order();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    Object columnValue = resultSet.getObject(i + 1);
                    //获取类的别名 getColumnName 不推荐使用 &&getColumnLabel  推荐


                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field declaredField = Order.class.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(order, columnValue);
                }
                return order;

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return null;


    }


}
