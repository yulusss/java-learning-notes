package com.yulus.StudentManagerSystem1;/*
 @author 云禄
 @version 1.0 
*/


import java.util.Scanner;

public class Utility {
    private static Scanner scanner = new Scanner(System.in);
    public static int scannerInt(String prompt){
        while(true) {
            System.out.print(prompt + ":");
            if(scanner.hasNextInt()){
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            }else{
                String input = scanner.nextLine();
                System.out.println("您输入的 " + input + " 不是整数,请重新输入");
            }
        }
    }

    public static String scannerYesOrNo(String prompt){
        while(true){
            System.out.print(prompt + "[y/n]:");
            String s = scanner.nextLine().trim().toLowerCase();
            if(s.equals("y") || s.equals("n")){
                return s;
            }else{
                System.out.println("请输入[y/n]");
                scanner.nextLine();
            }
        }
    }

    public static int scannerIntScope(String prompt , int min , int max){
        while(true) {
            System.out.print(prompt + ":");
            if(scanner.hasNextInt()){
                int value = scanner.nextInt();
                scanner.nextLine();
                if(value >= min && value <= max){
                    return value;
                }else{
                    System.out.println("您输入的整数不规范,请在 " + min + " 到 " + max + " 中输入");
                }
            }else{
                String input = scanner.nextLine();
                System.out.println("您输入的 " + input + " 不是整数,请重新输入");
            }
        }
    }

    public static String scannerString(String prompt){
        while(true){
            System.out.print(prompt + ":");
            String str = scanner.nextLine();
            if(str == null) {
                System.out.println("输入错误，请重新输入");
                continue;
            }
            String trimmed = str.trim();
            if(trimmed.length() > 0) {
                return trimmed;
            }else{
                System.out.println("输入不能为空，请重新输入");
            }
        }
    }
    public static String scannerStringScope(String prompt , int min , int max){
        while(true){
            System.out.print(prompt + " [" + min + "-" + max + "字符]: ");
            String str = scanner.nextLine();
            String trimmed = str.trim();
            if(trimmed.length() >= min && trimmed.length() <= max) {
                return trimmed;
            }else{
                System.out.println("输入不规范,长度只能在 " + min + " 到 " + max + " 个字符,请重新输入");
            }
        }
    }
    public static String scannerStringStrict(String prompt , int length){
        while(true){
            System.out.print(prompt + " [" + length + "字符]: ");
            String str = scanner.nextLine();
            String trimmed = str.trim();
            if(trimmed.length() == length) {
                return trimmed;
            }else{
                System.out.println("输入不规范,长度只能在 "  + length + " 个字符,请重新输入");
            }
        }
    }
}
