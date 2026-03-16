package com.yulus.StudentManagerSystem2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 云禄
 * @version 1.0
 */

public class StudentDao {

    //检查id是否存在
    public static boolean isIdExists(int id){
        String sql = "select id from student where id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtilityByDruid.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1 , id);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            JDBCUtilityByDruid.close(resultSet , preparedStatement , connection);
        }
    }

    //添加学生
    public static boolean insert(Student student){
        String sql = "insert into student (name , age , grade , phone) values( ? , ? , ? , ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtilityByDruid.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,student.getName());
            preparedStatement.setInt(2,student.getAge());
            preparedStatement.setString(3,student.getGrade());
            preparedStatement.setString(4,student.getPhone());
            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            JDBCUtilityByDruid.close(preparedStatement, connection);
        }
    }

    //查看所有学生
    public static List<Student> findAll(){
        List<Student> list = new ArrayList<>();
        String sql = "select * from student";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtilityByDruid.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                student.setGrade(resultSet.getString("grade"));
                student.setPhone(resultSet.getString("phone"));
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtilityByDruid.close(resultSet , preparedStatement , connection);
        }
        return list;
    }


    //删除学生
    public static boolean SingleRemove(int id){
        String sql = "delete from student where id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtilityByDruid.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1 , id);
            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            JDBCUtilityByDruid.close(preparedStatement , connection);
        }
    }

    //修改学生
    public static boolean update(int id , Student student){
        String sql = "update student set name = ? , age = ? , grade = ? , phone = ? where id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtilityByDruid.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , student.getName());
            preparedStatement.setInt(2 , student.getAge());
            preparedStatement.setString(3 , student.getGrade());
            preparedStatement.setString(4 , student.getPhone());
            preparedStatement.setInt(5 , id);
            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            JDBCUtilityByDruid.close(preparedStatement , connection);
        }
    }

    //根据学号查找学生，并返回一个学生对象
    public static Student searchStudentById(int id){
        String sql = "select * from student where id = ?";
        Student student = new Student();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtilityByDruid.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1 , id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                student.setGrade(resultSet.getString("grade"));
                student.setPhone(resultSet.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtilityByDruid.close(resultSet , preparedStatement , connection);
        }
        return student;
    }

    //检查姓名是否存在,模糊搜索
    public static boolean isNameExists(String name){
        String sql = "select id from student where name like ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtilityByDruid.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , "%" + name + "%");
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            JDBCUtilityByDruid.close(resultSet , preparedStatement , connection);
        }
    }


    //模糊查找学生，根据姓名
    public static List<Student> searchStudentByName(String name){
        String sql = "select * from student where name like ?";
        List<Student> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtilityByDruid.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1 , "%" + name + "%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                student.setGrade(resultSet.getString("grade"));
                student.setPhone(resultSet.getString("phone"));
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtilityByDruid.close(resultSet , preparedStatement , connection);
        }
        return list;
    }

    //学生人数
    public static int studentsCount(){
        String sql = "select count(*) as total from student";
        int count = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtilityByDruid.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtilityByDruid.close(resultSet , preparedStatement , connection);
        }
        return count;
    }
}