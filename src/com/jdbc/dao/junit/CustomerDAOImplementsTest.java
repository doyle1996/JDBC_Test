package com.jdbc.dao.junit;


import com.jdbc.dao.CustomerDAOImplements;
import com.jdbc.bean.Customer;
import com.jdbc.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

class CustomerDAOImplementsTest {
    private CustomerDAOImplements dao=new CustomerDAOImplements();


    @Test
    void insert() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Customer customer = new Customer(1,"doyle","doyle@gamil.com",new Date(123456));
            dao.insert(connection,customer);
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

        JDBCUtils.closeResource(connection,null);
        }

    }

    @Test
    void deleteById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            dao.deleteById(connection,1);
            System.out.println("remove success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

        JDBCUtils.closeResource(connection,null);
        }


    }

    @Test
    void update() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Customer customer = new Customer(2,"doyle2","update@gamil.com",new Date(1997-02-03));
            dao.update(connection,customer);
            System.out.println("update success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            JDBCUtils.closeResource(connection,null);
        }
    }

    @Test
    void getCustomerById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection1();
            Customer customer=dao.getCustomerById(connection,2);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            JDBCUtils.closeResource(connection,null);
        }
    }

    @Test
    void getAll() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection1();
            List<Customer> customerList=dao.getAll(connection);
            customerList.forEach(System.out::println);
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            JDBCUtils.closeResource(connection,null);
        }


    }

    @Test
    void getCount() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Long count=dao.getCount(connection);
            System.out.println("表中过的记录数为"+count);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            JDBCUtils.closeResource(connection,null);
        }


    }

    @Test
    void getMaxBirth() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            Date maxbirth=dao.getMaxBirth(connection);
            System.out.println("获取到最大生日"+maxbirth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            JDBCUtils.closeResource(connection,null);
        }

    }
}