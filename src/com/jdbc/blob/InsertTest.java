package com.jdbc.blob;

/*
 * 使用PrepareStatement实现批量数据操作
 * update、delete本身就具备批量操作的功能
 * 此时的批量操作，主要是指批量插入。使用PrepareStatement如何实现更高效的批量插入。
 * */

import com.jdbc.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertTest {
    public static void main(String[] args) {
//        TestInsert1();
//        TestInsert2();
//        TestInsert3();

    }

    //批量操作法1
    public static void TestInsert1() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            long start = System.currentTimeMillis();
            connection = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < 20000; i++) {
                preparedStatement.setObject(1, "name" + i);
                preparedStatement.execute();
                //需要过多的交互
            }
            long end = System.currentTimeMillis();
            System.out.println("花费时间:" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connection, preparedStatement);
        }


    }

    //批量操作法2
    //url=jdbc:mysql://localhost:3306/test?rewriteBatchedStatements=true
    //addBatch() executeBatch() executeBatch()都是默认关闭的 需要在url后+ ?rewriteBatchedStatements=true
    public static void TestInsert2() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            long start = System.currentTimeMillis();
            connection = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < 1000000; i++) {
                preparedStatement.setString(1, "name_" + i);

                //攒sql
                preparedStatement.addBatch();
                if (i % 500 == 0) {
                    //攒够500执行一次
                    preparedStatement.executeBatch(); //默认会提交一次
                    //清空batch
                    preparedStatement.clearBatch();
                }

            }
            long end = System.currentTimeMillis();
            System.out.println("所消耗的时间为：" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement);

        }


    }

    //批量操作3 设置不允许自动提交数据
    public static void TestInsert3() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        long start = System.currentTimeMillis();

        try {

            connection = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false); //不用于自动提交数据
            for (int i = 0; i <= 20000; i++) {
                preparedStatement.setString(1, "name_" + i);
                preparedStatement.addBatch();
                if (i % 500 == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            //统一提交数据
            connection.commit();
            long end = System.currentTimeMillis();
            System.out.println("所花时间" + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connection, preparedStatement);
        }
    }


}
