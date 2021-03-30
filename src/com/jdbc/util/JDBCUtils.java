package com.jdbc.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//操作数据库的工具类
public class JDBCUtils {
    public static Connection getConnection() throws Exception {
        //读取配置文件中的四个信息
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(is);
        String user = properties.getProperty("user");
        String psw = properties.getProperty("psw");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");
        //加载驱动
        Class.forName(driverClass);
        //获取连接
        Connection conn = DriverManager.getConnection(url, user, psw);
        return conn;
    }

    public static void closeResource(Connection conn, PreparedStatement preparedStatement) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeResource(Connection conn, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     *  * @description:使用c3p0的数据库连接
     * @param
     * @return java.sql.Connection

     * @author Zhang Jingbo
     * @date 2021/3/30 11:16 上午
     */

    public static Connection getConnection1() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
        Connection connection = cpds.getConnection();
        return connection;
    }

    /**
     * @description:使用dbutils.jar中提供的DBUtils工具类，实现资源的关闭
     * @author Zhang Jingbo
     * @param null
     * @return
     * @date 2021/3/30 5:08 下午
     */

    public static void closeResource1(Connection conn, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        DbUtils.close(conn);
        DbUtils.close(preparedStatement);
        DbUtils.close(resultSet);
    }
    public static void closeResource1(Connection conn, PreparedStatement preparedStatement) throws SQLException {
        DbUtils.close(conn);
        DbUtils.close(preparedStatement);

    }
}
