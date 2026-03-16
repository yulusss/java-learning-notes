package com.yulus.StudentManagerSystem2;

/*
 @author 云禄
 @version 1.0 
*/

public class StudentView {
    public static void view(){
        while (true) {
            System.out.println("========== 学生管理系统 ==========");
            System.out.println("1.添加学生");
            System.out.println("2.删除学生");
            System.out.println("3.修改学生信息");
            System.out.println("4.查询所有学生");
            System.out.println("5.按学号查询学生");
            System.out.println("6.按姓名查询学生");
            System.out.println("7.统计学生人数");
            System.out.println("0.退出系统");
            int choice = Utility.scannerIntScope("请输入您的选择", 0, 7);
            switch (choice){
                case 1:
                    StudentManager.add();
                    break;
                case 2:
                    StudentManager.remove();
                    break;
                case 3:
                    StudentManager.updateStudent();
                    break;
                case 4:
                    StudentManager.SearchForAllStudents();
                    break;
                case 5:
                    StudentManager.outputStudentById();
                    break;
                case 6:
                    StudentManager.outputStudentByName();
                    break;
                case 7:
                    StudentManager.outputStudentsCount();
                    break;
                case 0:
                    StudentManager.exit();
            }
        }
    }
}
