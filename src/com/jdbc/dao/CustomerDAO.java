package com.jdbc.dao;

import com.jdbc.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
/**此接口用于规范对于customers表的常用操作
 * @description:
 * @author Zhang Jingbo
 * @param null
 * @return
 * @date 2021/3/24 8:26 下午
 */


public interface CustomerDAO {

     /**
      * @description:将customer对象添加到数据库中
      * @author Zhang Jingbo
      * @param connection
      * @param customer
      * @return void
      * @date 2021/3/24 7:07 下午
      */
     void insert(Connection connection, Customer customer);

     /**
      * @description:针对指定的id，删除表中的一条记录
      * @author Zhang Jingbo
      * @param connection
      * @param id
      * @return void
      * @date 2021/3/24 7:59 下午
      */

     void deleteById(Connection connection,int id);

     /**
      * @description:针对内存中的customer对象，去修改数据表中的指定记录
      * @author Zhang Jingbo
      * @param connection
      * @param customer
      * @return void
      * @date 2021/3/24 8:00 下午
      */

     void update(Connection connection,Customer customer);
     /**
      * @description:正对指定的id查询得到对应的Customer对象
      * @author Zhang Jingbo
      * @param connection
      * @param id
      * @return void
      * @date 2021/3/24 8:03 下午
      */

     Customer getCustomerById(Connection connection, int id);
     /**
      * @description:查询表中所有记录构成的集合
      * @author Zhang Jingbo
      * @param connection
      * @return java.util.List<com.jdbc.bean.Customer>
      * @date 2021/3/24 8:04 下午
      */

     List<Customer> getAll(Connection connection);
     /**
      * @description:返回数据表中的数据条目数
      * @author Zhang Jingbo
      * @param connection
      * @return java.lang.Long
      * @date 2021/3/24 8:05 下午
      */

     Long getCount(Connection connection);
     /**
      * @description:返回数据表中的最大生日
      * @author Zhang Jingbo
      * @param connection
      * @return java.sql.Date
      * @date 2021/3/24 8:09 下午
      */

     Date getMaxBirth(Connection connection);


}


