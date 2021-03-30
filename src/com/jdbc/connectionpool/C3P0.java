package com.jdbc.connectionpool;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.jupiter.api.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0 {
    //方式一
    @Test
    public void testGetConnection() throws PropertyVetoException, SQLException {
        //获取c3p0数据库连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.cj.jdbc.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=Asia/Shanghai");
        cpds.setUser("root");
        cpds.setPassword("ylmn750716");
        //通过设置相关参数，对数据库连接池进行管理
        //设置初始时数据库连接池中的连接数
        cpds.setInitialPoolSize(10);
        Connection connection = cpds.getConnection();
        System.out.println(connection);
        //销毁c3p0数据库连接池
//        DataSources.destroy(cpds);
    }

    //方式二 使用配置文件
    public static ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
@Test
    public void testGetConnection1() throws SQLException {
        Connection connection = cpds.getConnection();
        System.out.println(connection);
    }
}
