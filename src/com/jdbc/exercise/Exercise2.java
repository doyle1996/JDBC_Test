package com.jdbc.exercise;

import com.jdbc.bean.Customer;
import com.jdbc.bean.Order;
import com.jdbc.bean.Student;
import com.jdbc.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

/*
* 插入一个新的student信息
* 在idea中建立java程序，输入身份证号或准考证号可以查询到学生的基本信息；结果如下：
* */

public class Exercise2 {
    public static void main(String[] args) {
//        InsertData();
//        QueryWithIdCardOrExamCard();
//        DeleteByExamCard();
        DeleteByExamCard1();
    }


    //问题1：插入一个新的student信息
    //调用通用的增删改查方法
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
    //调用通用的查询方法
    public static <T> T getInstance(Class<T> clazz, String sql, Object... args) {
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
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            if (resultSet.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = resultSet.getObject(i + 1);
                    String columnLabel = metaData.getColumnLabel(i + 1);
                    Field declaredField = t.getClass().getDeclaredField(columnLabel);
                    declaredField.setAccessible(true);
                    declaredField.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, preparedStatement, resultSet);

        }
        return null;
    }
    public static void QueryWithIdCardOrExamCard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请选择您要输入的类型");
        System.out.println("a.身份证号");
        System.out.println("b.准考证号");
        String inputValue=scanner.next();
        if("a".equalsIgnoreCase(inputValue)){
            System.out.println("请输入身份证号");
            String idcard=scanner.next();
            String sql="select FlowID FlowId,Type,IDCard IdCard,ExamCard,StudentName,Location,Grade from examstudent where IDCard=?";
            Student student = getInstance(Student.class, sql, idcard);
            if (student != null) {

                System.out.println(student);
            }
            else{
                System.out.println("输入的身份证号有误");
            }
        }
        else if("b".equalsIgnoreCase(inputValue)){
            System.out.println("请输入准考证号");
            String excard=scanner.next();
            String sql="select FlowID FlowId,Type,IDCard IdCard,ExamCard,StudentName,Location,Grade from examstudent where ExamCard=?";
            Student student = getInstance(Student.class, sql, excard);
            if(student!=null){
                System.out.println(student);
            }
            else{
                System.out.println("输入的准考证号有误");
            }

        }else{
            System.out.println("输入有误 请重新输入");
        }

    }



    //问题3.删除指定学生的信息

    //法一： 先查询到 然后判断是否存在决定删除 调用通用查询方法
    public static void DeleteByExamCard() {
        System.out.println("请输入学生的考号：");
        Scanner scanner = new Scanner(System.in);
        String examcard = scanner.next();
        String sql = "select FlowID FlowId,Type,IDCard IdCard,ExamCard,StudentName,Location,Grade from examstudent where ExamCard=?";
        Student student = getInstance(Student.class, sql, examcard);
        if(student==null){
            System.out.println("查无此人");

        }
        else{
            String sql1="delete from examstudent where examcard=?";
            int deleteCount = InsertStudentData(sql1, examcard);
            if(deleteCount>0){
                System.out.println("删除成功");
            }
        }

    }

    //法2 ：
    // 直接调用通用的增删改方法，preparedStatement.executeUpdate()增长则直接删除
    public static int update(String sql, Object... args)  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            /*
             *   preparedStatement.execute()如果是查询工作返回的是true 如果是增删改操作返回的false
             * */
//            preparedStatement.execute();
            /*
            preparedStatement.executeUpdate如果添加成功行数增加
            * */
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connection, preparedStatement);
        }

        return 0;

    }

    public static void DeleteByExamCard1(){
        System.out.println("请输入考生的准考证号:");
        Scanner scanner = new Scanner(System.in);
        String examid=scanner.next();
        String sql1="delete from examstudent where examcard=?";
        int deletecount = update(sql1, examid);
        if(deletecount>0){
            System.out.println("删除成功");
        }
        else{
            System.out.println("查无此人");
        }
    }

















}
