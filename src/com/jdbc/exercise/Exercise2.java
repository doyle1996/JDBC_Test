package com.jdbc.exercise;

import com.jdbc.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

/*
* 插入一个新的student信息
* 在idea中建立java程序，输入身份证号或准考证号可以查询到学生的基本信息；结果如下：
* */

public class Exercise2 {
    public static void main(String[] args) {
        InsertData();
    }


    //问题1：插入一个新的student信息
    public static void InsertData(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("input 四/六级:");
        int TyPe=scanner.nextInt();
        System.out.println("input 身份证号");
         String IDCard=scanner.next();
        System.out.println("input 准考证号:");
        String ExamCard=scanner.next();
        System.out.println("input 姓名:");
        String StudentName=scanner.next();
        System.out.println("input 所在城市:");
        String Location=scanner.next();
        System.out.println("input 考试成绩:");
        int Grade=scanner.nextInt();
        String sql="insert into examstudent (Type,IDCard,ExamCard,StudentName,Location,Grade)values(?,?,?,?,?,?)";
        int count =InsertStudentData(sql,TyPe,IDCard,ExamCard,StudentName,Location,Grade);
        if(count>0){
            System.out.println("success");

        }
        else {
            System.out.println("error");
        }

    }
    //student表中添加一条记录
    public static int InsertStudentData(String sql,Object ...args)  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i+1,args[i]);
            }
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeResource(connection,preparedStatement);
        }

        return 0;

    }


    //问题2：根据身份证号或者准考证号查询学生的成绩信息
















}
