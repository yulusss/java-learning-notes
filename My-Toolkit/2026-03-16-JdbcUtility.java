package com.yulus.jdbc.jdbcUtility;

/*
 @author 云禄
 @version 1.0 
*/

import com.yulus.jdbc.JDBCUtilityByDruid;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JdbcUtility {

    //定义相关的属性，因为只需要一份，所以使用static
    private static String user;//用户名
    private static String password;//密码
    private static String url;//url
    private static String driver;//驱动名

    //静态代码块,创建对象时就初始化
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/record.properties"));
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            driver = properties.getProperty("driver");
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //连接数据库，返回 Connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //关闭相关资源
    /*
       1.ResultSet(结果集)
       2.Statement 或者 preparedStatement
       3.Connection
       4.如果需要关闭资源 就传入对象
     */
    public static void close(ResultSet resultSet,
                             Statement statement/*既可以接受Statement也可以接受preparedStatement*/,
                             Connection connection) {

        JDBCUtilityByDruid.closeAssist(resultSet, statement, connection);
    }

    // 重载1：关闭Statement和ResultSet
    public static void close(Statement statement, ResultSet resultSet) {
        close(resultSet, statement, null);
    }

    // 重载2：只关闭Statement
    public static void close(Statement statement) {
        close(null, statement, null);
    }

    // 重载3：只关闭Connection
    public static void close(Connection connection) {
        close(null, null, connection);
    }
}
