package com.jdbc.transation;

import com.jdbc.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class TransationImplementTest {
    private TransationImplement transationImplement =new TransationImplement();

    @Test
    void getInstance2() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        //设置数据库的隔离级别
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //获取当前连接的隔离级别
        System.out.println(connection.getTransactionIsolation());
        //取消自动提交数据
        connection.setAutoCommit(false);
        String sql = "select user,password,balance from user_table where user= ?";
        User user = transationImplement.getInstance2(connection, User.class, sql, "CC");
        System.out.println(user);


    }

    @Test
    void update2() {
        try {
            Connection connection = JDBCUtils.getConnection();
            //取消自动提交数据

            connection.setAutoCommit(false);
            String sql = "update user_table set balance=? where  user=?";
            transationImplement.update2(connection, sql, 2000, "CC");
            Thread.sleep(15000);
            System.out.println("修改结束");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}