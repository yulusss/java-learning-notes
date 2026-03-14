package com.yulus.StudentManagerSystem;/*
 @author 云禄
 @version 1.0 
*/

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String age;
    private String grade;
    private String phone;

    public Student(String id, String name, String age, String grade, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.phone = phone;
    }


    @Override
    public String toString() {
        return String.format("%-4s %-6s %-4s %-4s %-11s" , id , name , age , grade , phone);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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
