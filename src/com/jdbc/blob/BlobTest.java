package com.jdbc.blob;
/*
测试使用PrepareStatement操作Blob类型的数据
*/


import com.jdbc.bean.Customer;
import com.jdbc.util.JDBCUtils;

import java.io.*;
import java.sql.*;

public class BlobTest {
    public static void main(String[] args) {
//        InsertBlobTest();
        QueryBlobTest();
    }

    //插入一个blob
    public static void InsertBlobTest() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        String sql = "insert into customers(name,email,birth,photo)values(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, "hjl");
        preparedStatement.setObject(2, "hjl@qq.com");
        preparedStatement.setObject(3, "1997-02-13");
        FileInputStream fileInputStream = new FileInputStream(new File("/Users/zhangjingbo/IdeaProjects/JDBC_bilibili_demo/src/girl.jpg"));
        preparedStatement.setBlob(4, fileInputStream);
        preparedStatement.execute();
        JDBCUtils.closeResource(connection, preparedStatement);


    }

    //查询customer表中的blob字段
    //查询用executeQuery
    public static void QueryBlobTest() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        InputStream is = null;
        FileOutputStream fileOutputStream = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select id,birth,email,name,photo from customers where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 19);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                int id = resultSet.getInt("id");
                Date birth = resultSet.getDate("birth");
                String email = resultSet.getString("email");
                String name = resultSet.getString("name");

                Customer customer = new Customer(id, name, email, birth);


                //把图像转换为二进制在转换为数组
                //将blob类型的字段下载下来，以文件的方式保存在本地
                Blob photo = resultSet.getBlob("photo");
                //因为Blob存取的二进制数据，getBinaryStream获取的就是二进制数据的流
                is = photo.getBinaryStream();
                fileOutputStream = new FileOutputStream("hjl.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                }


                            System.out.println(customer);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            JDBCUtils.closeResource(connection, preparedStatement, resultSet);
        }

    }


}
