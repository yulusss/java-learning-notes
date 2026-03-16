package com.yulus.StudentManagerSystem1;/*
 @author 云禄
 @version 1.0 
*/

import java.io.*;
import java.util.*;

public class StudentManager {
    //存储学生信息
    static HashMap<Integer, Student> myHashmap = new HashMap<>();
    //存储学生id
    static List<Integer> studentId = new ArrayList<>();
    //学生人数
    static int studentCount = 0;

    //添加学生
    public static void add() {
        int id;
        while (true) {
            id = Utility.scannerInt("请输入学生号");
            if (myHashmap.get(id) != null) {
                System.out.println("该学号已存在,请重新输入");
            } else {
                break;
            }
        }
        String name = Utility.scannerStringScope("请输入学生姓名" , 0 , 10);
        int age = Utility.scannerIntScope("请输入学生年龄", 0, 120);
        String grade = Utility.scannerString("请输入学生班级");
        String phone = Utility.scannerStringStrict("请输入手机号", 11);

        studentId.add(id);
        myHashmap.put(id, new Student(id, name, age, grade, phone));
        studentCount++;
        System.out.println("添加成功");
    }

    //删除学生
    public static void remove() {
        int removed = Utility.scannerInt("请输入学生id");

        if (!studentId.contains(removed)) {
            System.out.println("未找到相关学生信息");
            return;
        }

        myHashmap.remove(removed);
        studentId.remove((Integer) removed);
        studentCount--;
        System.out.println("删除成功");
    }

    //修改学生信息
    public static void modify() {
        int id = Utility.scannerInt("请输入学生id");
        if(!studentId.contains(id)){
            System.out.println("暂无此学生");
            return;
        }
        boolean bool = true;
        while(bool) {
            System.out.println("1.学生id");
            System.out.println("2.学生姓名");
            System.out.println("3.学生年龄");
            System.out.println("4.学生班级");
            System.out.println("5.电话");
            System.out.println("0.退出");
            int choice = Utility.scannerIntScope("请输入要修改的属性", 0, 5);
            switch (choice) {
                case 1:
                    System.out.println("当前学生id: " + myHashmap.get(id).getId());
                    int newId = Utility.scannerInt("请输入修改后的id");
                    if (newId == id) {
                        System.out.println("新id与旧id相同，无需修改");
                        break;
                    }
                    if (studentId.contains(newId)) {
                        System.out.println("该 id 已存在，修改失败");
                        break;
                    }
                    Student student = myHashmap.get(id);
                    myHashmap.remove(id);
                    studentId.remove((Integer) id);
                    student.setId(newId);

                    myHashmap.put(newId, student);
                    studentId.add(newId);

                    id = newId;
                    System.out.println("id修改成功");
                    break;
                case 2:
                    System.out.println("当前学生姓名: " + myHashmap.get(id).getName());
                    String modifiedName = Utility.scannerStringScope("请输入修改后的学生姓名", 0, 10);
                    myHashmap.get(id).setName(modifiedName);
                    System.out.println("姓名修改成功");
                    break;
                case 3:
                    System.out.println("当前学生年龄: " + myHashmap.get(id).getAge());
                    int modifiedAge = Utility.scannerIntScope("请输入修改后的学生年龄", 0, 120);
                    myHashmap.get(id).setAge(modifiedAge);
                    System.out.println("年龄修改成功");
                    break;
                case 4:
                    System.out.println("当前学生班级: " + myHashmap.get(id).getGrade());
                    String modifiedGrade = Utility.scannerString("请输入修改后的学生班级");
                    myHashmap.get(id).setGrade(modifiedGrade);
                    System.out.println("班级修改成功");
                    break;
                case 5:
                    System.out.println("当前电话: " + myHashmap.get(id).getPhone());
                    String modifiedPhone = Utility.scannerStringStrict("请输入修改后的电话", 11);
                    myHashmap.get(id).setPhone(modifiedPhone);
                    System.out.println("电话修改成功");
                    break;
                case 0:
                    System.out.println("==========修改后学生信息==========");
                    System.out.println(myHashmap.get(id));
                    return;
            }
        }
    }


    //查询所有学生
    public static void list() {
        System.out.println("==========学生名单==========");
        Iterator<Map.Entry<Integer, Student>> iterator = myHashmap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Student> next = iterator.next();
            System.out.println(next.getValue());
        }
        System.out.println( "总人数: " + studentCount + "人");
    }

    //按学号查询学生
    public static void idSearchStudent() {
        int id = Utility.scannerInt("请输入您要查询的学号");
        if (!studentId.contains(id)) {
            System.out.println("暂无此学生");
            return;
        }
        System.out.println("==========学生信息==========");
        System.out.println(myHashmap.get(id));
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
    public static void saveFile() {
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
        String filePath = "/Volumes/yulus/java/studentSave.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("没有找到保存文件，将创建新文件");
            return;
        }

        myHashmap.clear();
        studentId.clear();
        studentCount = 0;
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
                saveFile();
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
