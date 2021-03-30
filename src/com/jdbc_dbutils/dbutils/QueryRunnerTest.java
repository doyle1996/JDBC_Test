package com.jdbc_dbutils.dbutils;

import com.jdbc.bean.Customer;
import com.jdbc.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @param null
 * @author Zhang Jingbo
 * @description:commons-abutils是Apache组织提供的一个开源JDBC工具库，封装了针对数据库的增删改查操作
 * @return
 * @date 2021/3/30 12:16 下午
 */


public class QueryRunnerTest {
    //测试插入
    @Test
    public void insert() {
        Connection connection1 = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection1 = JDBCUtils.getConnection1();
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            int insertcpunt = queryRunner.update(connection1, sql, "cxu", "cxk@gmail.com", "1996-12-04");
            System.out.println("插入了" + insertcpunt + "条记录");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connection1, null);
        }
    }

    //测试查询

    @Test
    /**
     * @description:BeanHandler是ResultSetHandker接口的实现类，用于封装一条记录
     * @author Zhang Jingbo
     * @param
     * @return void
     * @date 2021/3/30 2:18 下午
     */
    public void testQuery1() {
        Connection connection1 = null;
        try {
            QueryRunner queryRunner = new QueryRunner();
            connection1 = JDBCUtils.getConnection1();
            String sql = "select id,name,email,birth from customers where id=?";
            BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
            Customer customer = queryRunner.query(connection1, sql, handler, 25);
            System.out.println(customer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connection1, null);
        }


    }

    @Test
    /**
     * @description:BeanListHandler:是ResultSetHandker接口的实现类，封装多条记录构成的集合
     * @author Zhang Jingbo
     * @param null
     * @return
     * @date 2021/3/30 2:20 下午
     */
    public void testQuery2() {
        Connection connection1 = null;
        try {
            connection1 = JDBCUtils.getConnection1();
            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id<? ";
            BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);
            List<Customer> list = queryRunner.query(connection1, sql, handler, 25);
            list.forEach(System.out::println);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connection1, null);
        }
    }

    @Test
    /**
     *@description: MapHandle
     * @author Zhang Jingbo
     * @param null
     * @return
     * @date 2021/3/30 2:48 下午
     */
    public void testQuery3() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name,email from customers where id=25";
            MapHandler mapHandler = new MapHandler();
            Map<String, Object> map = queryRunner.query(connection, sql, mapHandler);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connection, null);
        }
    }


    @Test
    /**
     * @description:MapListHandler 对应表中的多条记录
     * @param null
     * @return
     * @author Zhang Jingbo
     * @date 2021/3/30 2:57 下午
     */
    public void testQuery4() {
        Connection connection1 = null;
        try {
            connection1 = JDBCUtils.getConnection1();
            QueryRunner queryRunner = new QueryRunner();
            String sql = "select id,name,email,birth from customers where id<? ";
            MapListHandler mapListHandler = new MapListHandler();
            List<Map<String, Object>> list = queryRunner.query(connection1, sql, mapListHandler, 25);
            list.forEach(System.out::println);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connection1, null);
        }
    }

    /**
     * @param
     * @return void
     * @description:ScalarHandler用于查询特殊值
     * @author Zhang Jingbo
     * @date 2021/3/30 5:02 下午
     */

    @Test
    public void testQuery5() throws SQLException {
        Connection connection1 = JDBCUtils.getConnection1();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select count(*) from customers";
        ScalarHandler handler = new ScalarHandler();

        Object query = queryRunner.query(connection1, sql, handler);
        System.out.println(query);
    }

    @Test
    public void testQuery6() throws SQLException {
        Connection connection1 = JDBCUtils.getConnection1();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select max(birth) from customers";
        ScalarHandler handler = new ScalarHandler();

        Date query = (Date) queryRunner.query(connection1, sql, handler);
        System.out.println(query);
        JDBCUtils.closeResource1(connection1,null);
    }


}
