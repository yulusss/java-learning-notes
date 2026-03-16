package com.yulus.StudentManagerSystem2;/*
 @author 云禄
 @version 1.0 
*/

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int age;
    private String grade;
    private String phone;

    public Student() {
    }

    public Student(String name, int age, String grade, String phone) {
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.phone = phone;
    }

    public Student(int id, String name, int age, String grade, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.phone = phone;
    }


    @Override
    public String toString() {
        return String.format("id：%-4d\t姓名：%-6s\t年龄：%-4d\t班级：%-4s\t电话：%-11s" , id , name , age , grade , phone);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
