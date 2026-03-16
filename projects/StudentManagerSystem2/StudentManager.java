package com.yulus.StudentManagerSystem2;
/*
 @author 云禄
 @version 1.0 
*/


import java.util.List;

public class StudentManager {

    //1.增加学生
    public static void add(){
        String name = Utility.scannerStringScope("请输入学生姓名" , 1 , 10);
        int age = Utility.scannerIntScope("请输入学生年龄" , 0 , 120);
        String grade = Utility.scannerString("请输入学生班级");
        String phone = Utility.scannerStringStrict("请输入电话" , 11);
        Student student = new Student(name, age, grade, phone);
        if(StudentDao.insert(student)){
            System.out.println("添加成功");
            return;
        }
        System.out.println("添加失败");
    }

    //2.删除学生
    public static void remove(){
        int id = Utility.scannerInt("请输入删除学生id");
        if(StudentDao.SingleRemove(id)){
            System.out.println("删除成功");
            return;
        }
        System.out.println("删除失败");
    }

    //3.修改学生
    public static void updateStudent(){
        int id = Utility.scannerInt("请输入修改学生id");
        if(!StudentDao.isIdExists(id)){
            System.out.println("学号不存在");
            return;
        }
        Student student = StudentDao.searchStudentById(id);
        boolean modified = false;
        while (true) {
            System.out.println("1.学生姓名");
            System.out.println("2.学生年龄");
            System.out.println("3.学生班级");
            System.out.println("4.电话");
            System.out.println("0.保存并退出");
            int choice = Utility.scannerIntScope("请输入您要修改的学生信息", 0, 4);
            switch (choice){
                case 1:
                    System.out.println("当前学生姓名: " + student.getName());
                    String newName = Utility.scannerStringScope("请输入修改后的学生姓名", 1, 10);
                    if(!newName.equals(student.getName())){
                        student.setName(newName);
                        modified = true;
                        System.out.println("已修改学生姓名");
                    }else{
                        System.out.println("未修改学生姓名");
                    }
                    break;
                case 2:
                    System.out.println("当前学生年龄: " + student.getAge());
                    int newAge = Utility.scannerIntScope("请输入修改后的学生年龄", 0, 120);
                    if(newAge != student.getAge()){
                        student.setAge(newAge);
                        modified = true;
                        System.out.println("已修改学生年龄");
                    }else{
                        System.out.println("未修改学生年龄");
                    }
                    break;
                case 3:
                    System.out.println("当前学生班级: " + student.getGrade());
                    String newGrade = Utility.scannerString("请输入修改后的学生班级");
                    if(!newGrade.equals(student.getGrade())){
                        student.setGrade(newGrade);
                        modified = true;
                        System.out.println("已修改学生班级");
                    }else{
                        System.out.println("未修改学生班级");
                    }
                    break;
                case 4:
                    System.out.println("当前电话: " + student.getPhone());
                    String newPhone = Utility.scannerStringStrict("请输入修改后的电话" , 11);
                    if(!newPhone.equals(student.getPhone())){
                        student.setPhone(newPhone);
                        modified = true;
                        System.out.println("已修改电话");
                    }else{
                        System.out.println("未修改电话");
                    }
                    break;
                case 0:
                    if(modified){
                        if(StudentDao.update(id , student)){
                            System.out.println("修改成功");
                            System.out.println(student);
                        }else{
                            System.out.println("修改失败");
                        }
                    }else{
                        System.out.println("未修改学生信息");
                    }
                    return;
            }
        }
    }


    //4.查询所有学生
    public static void SearchForAllStudents(){
        List<Student> list = StudentDao.findAll();
        System.out.println("==========所有学生信息==========");
        for (Student student : list) {
            System.out.println(student);
        }
    }

    //5.根据id查找学生
    public static void outputStudentById(){
        int id = Utility.scannerInt("请输入查询学生的id");
        if(!StudentDao.isIdExists(id)){
            System.out.println("暂无学生信息");
            return;
        }

        Student student = StudentDao.searchStudentById(id);
        System.out.println("学生信息");
        System.out.println(student);
    }

    //6.按照姓名查找学生，支持模糊搜索
    public static void outputStudentByName(){
        String name = Utility.scannerString("请输入查询学生的姓名(支持模糊搜索)");
        if(!StudentDao.isNameExists(name)){
            System.out.println("暂无该信息");
            return;
        }
        List<Student> students = StudentDao.searchStudentByName(name);
        System.out.println("==========学生信息==========");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    //7.输出学生总人数
    public static void outputStudentsCount(){
        int count = StudentDao.studentsCount();
        if(count == 0){
            System.out.println("暂无学生信息");
            return;
        }
        System.out.println("学生人数: " + count);
    }

    //0.退出系统
    public static void exit(){
        String choice = Utility.scannerYesOrNo("是否要退出");
        if(choice.equals("y")){
            System.out.println("感谢使用");
            System.exit(0);
        }
    }
}
