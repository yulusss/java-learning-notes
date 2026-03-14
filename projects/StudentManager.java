package com.yulus.StudentManagerSystem;/*
 @author 云禄
 @version 1.0 
*/

import java.io.*;
import java.util.*;

public class StudentManager {
    //存储学生信息
    static HashMap<String, Student> myHashmap = new HashMap<>();
    //存储学生id
    static List<String> studentId = new ArrayList<>();
    //学生人数
    static int studentCount = 0;

    //添加学生
    public static void add() {
        String id;
        while (true) {
            id = Utility.scannerString("请输入学生号");
            if (myHashmap.get(id) != null) {
                System.out.println("该学号已存在,请重新输入");
            } else {
                break;
            }
        }
        String name = Utility.scannerString("请输入学生姓名");
        String age = Utility.scannerStringScope("请输入学生年龄", 0, 3);
        String grade = Utility.scannerString("请输入学生班级");
        String phone = Utility.scannerStringStrict("请输入手机号", 11);

        studentId.add(id);
        myHashmap.put(id, new Student(id, name, age, grade, phone));
        studentCount++;
        System.out.println("添加成功");
    }

    //删除学生
    public static void remove() {
        String removed = Utility.scannerString("请输入学生id");

        if (!studentId.contains(removed)) {
            System.out.println("未找到相关学生信息");
            return;
        }

        myHashmap.remove(removed);
        studentId.remove(removed);
        studentCount--;
        System.out.println("删除成功");
    }

    //修改学生信息
    public static void modify() {
        Scanner scanner = new Scanner(System.in);
        String i = Utility.scannerString("请输入修改学生id");
        if (!studentId.contains(i)) {
            System.out.println("暂无该学生信息");
            return;
        }

        Student student = myHashmap.get(i);
        String id;
        while (true) {
            System.out.print("学生id: " + student.getId() + "\t请修改[回车默认不修改]: ");
            id = scanner.nextLine();
            if (!id.isEmpty()) {
                if(myHashmap.get(id) != null){
                    System.out.println("该id已存在，请重新输入");
                }else {
                    student.setId(id);
                    break;
                }
            }else{
                break;
            }

        }
        String name;
        System.out.print("学生姓名: " + student.getName() + "\t请修改[回车默认不修改]: ");
        name = scanner.nextLine();
        if (!name.isEmpty()) {
            student.setName(name);
        }
        System.out.print("学生年龄: " + student.getAge() + "\t请修改[回车默认不修改]: ");
        String age = scanner.nextLine();
        if (!age.isEmpty()) {
            student.setAge(age);
        }
        System.out.print("学生班级: " + student.getGrade() + "\t请修改[回车默认不修改]: ");
        String grade = scanner.nextLine();
        if (!grade.isEmpty()) {
            student.setGrade(grade);
        }
        System.out.print("电话号码: " + student.getPhone() + "\t请修改[回车默认不修改]: ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) {
            if(phone.length() != 11){
                phone = Utility.scannerStringStrict("输入格式不正确" , 11);
            }
            student.setPhone(phone);
        }
        myHashmap.remove(i);
        studentId.remove(i);
        myHashmap.put(id , student);
        studentId.add(id);
        System.out.println("修改完毕");
        System.out.println("==========修改后学生信息==========");
        System.out.printf( "%-4s %-6s %-4s %-4s %-11s\n" , "学号" , "姓名" , "年龄" , "班级" , "电话");
        System.out.println(student);
    }


    //查询所有学生
    public static void list() {
        System.out.println("==========学生名单==========");
        System.out.printf( "%-4s %-6s %-4s %-4s %-11s\n" , "学号" , "姓名" , "年龄" , "班级" , "电话");
        Iterator<Map.Entry<String, Student>> iterator = myHashmap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Student> next = iterator.next();
            System.out.println(next.getValue());
        }
        System.out.println( "总人数: " + studentCount + "人");
    }

    //按学号查询学生
    public static void idSearchStudent() {
        String i = Utility.scannerString("请输入您要查询的学号");
        if (!studentId.contains(i)) {
            System.out.println("暂无此学生");
            return;
        }
        System.out.println("==========学生信息==========");
        System.out.printf( "%-4s %-6s %-4s %-4s %-11s\n" , "学号" , "姓名" , "年龄" , "班级" , "电话");
        System.out.println(myHashmap.get(i));
    }

    //按姓名查询学生
    public static void nameSearchStudent() {
        String s = Utility.scannerString("请输入学生姓名(支持模糊搜索)");
        System.out.println("正在搜索包含[" + s + "]的学生");
        boolean found = false;
        for (Student student : myHashmap.values()) {
            if (student.getName().contains(s)) {
                System.out.println(student);
                found = true;
            }
        }
        if (!found) {
            System.out.println("未找到相关学生");
        }
    }

    //统计学生人数
    public static void searchStudentCount() {
        if (studentCount == 0) {
            System.out.println("暂无学生");
            return;
        }
        System.out.println("学生人数: " + studentCount + "人");
    }

    //保存数据到文件
    public static void SaveFile() {
        String filePath = "/Volumes/yulus/java/studentSave.txt";
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath));
            for (Student student : myHashmap.values()) {
                objectOutputStream.writeObject(student);
            }
            System.out.println("保存成功，共 " + myHashmap.size() + " 个学生");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //加载文件
    public static void loadFile() {
        myHashmap.clear();
        studentId.clear();
        studentCount = 0;
        String filePath = "/Volumes/yulus/java/studentSave.txt";
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));

            while (true) {
                try {
                    Student o = (Student) objectInputStream.readObject();
                    myHashmap.put(o.getId(), o);
                    studentCount++;
                    studentId.add(o.getId());
                    System.out.println("已加载数据: " + o);
                } catch (EOFException e) {
                    System.out.println("文件加载完毕");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //退出系统
    public static void exit() {
        String s = Utility.scannerYesOrNo("请确认是否退出系统");
        if (s.equals("y")) {
            String s1 = Utility.scannerYesOrNo("是否要保存文件");
            if (s1.equals("y")) {
                SaveFile();
                System.out.println("已退出系统");
                System.exit(0);
            } else {
                System.out.println("已退出系统");
                System.exit(0);
            }
        } else {
            return;
        }
    }
}
