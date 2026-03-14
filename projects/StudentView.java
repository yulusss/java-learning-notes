package com.yulus.StudentManagerSystem;

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
            System.out.println("8.保存数据到文件");
            System.out.println("9.从文件加载数据");
            System.out.println("0.退出系统");
            int choice = Utility.scannerIntScope("请输入您的选择", 0, 9);
            switch (choice){
                case 1:
                    StudentManager.add();
                    break;
                case 2:
                    StudentManager.remove();
                    break;
                case 3:
                    StudentManager.modify();
                    break;
                case 4:
                    StudentManager.list();
                    break;
                case 5:
                    StudentManager.idSearchStudent();
                    break;
                case 6:
                    StudentManager.nameSearchStudent();
                    break;
                case 7:
                    StudentManager.searchStudentCount();
                    break;
                case 8:
                    StudentManager.SaveFile();
                    break;
                case 9:
                    StudentManager.loadFile();
                    break;
                case 0:
                    StudentManager.exit();
            }
        }
    }
}
