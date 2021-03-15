package com.jdbc.preparestatement2;

import com.jdbc.bean.Customer;
import com.jdbc.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;

/*
主要考查内容：
获取结果集的元数据  行列的名称
ResultSetMetaData metaData = resultSet.getMetaData();
反射：
 Field declaredField = Customer.class.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(customer, columnValue);

*/

public class CustomerForQuery {
    public static void main(String[] args) throws Exception {
//        TestQuery();
        //testQueryForCustomer1();


    }
    //针对customer表的一种查询操作
    /*
    public static  void TestQuery(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql="select id,name,email,birth from customers where id=?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"1");
            //执行 并返回结果集
            resultSet = preparedStatement.executeQuery();
            //处理结果集
            if(resultSet.next()){ //判断结果集的下一条是否有数据，如果有数据，返回true，指针下移
                //获取当前这条数据的各个字段值
                int id=resultSet.getInt(1);
                String name=resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                //方式一
               // System.out.println("id="+id+",name="+name+",email="+email+",birth="+birth);
                //方式二
//                Object[] data=new Object[]{id,name,email,birth};
                //方式三 将数据封装为一个对象
                Customer customer=new Customer(id,name,email,birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭资源
        JDBCUtils.closeResource(connection,preparedStatement,resultSet);
    }
     */

    /*public static void testQueryForCustomer1() {
        String sql = "select name,id from customers where id=?";
        Customer customer = QueryForCustomer(sql, 2);
        System.out.println(customer);
    }


    //针对customer表的查询操作
    public static Customer QueryForCustomer(String sql, Object... args) {
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
            //获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //通过ResultSetMetaData获取结果集的列数
            int columnCount = metaData.getColumnCount();
            if (resultSet.next()) {
                Customer customer = new Customer();
                //处理数据集一行数据中的每一列
                for (int i = 0; i < columnCount; i++) {
                    //获取这列的值
                    Object columnValue = resultSet.getObject(i + 1);
                    //获取这列的列名
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    //给customer对象的columnName属性赋值为columnValue;通过反射
                    Field declaredField = Customer.class.getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(customer, columnValue);
                }
                return customer; //返回结果 包含添加的内容
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }
        return null;


    }
*/



}
