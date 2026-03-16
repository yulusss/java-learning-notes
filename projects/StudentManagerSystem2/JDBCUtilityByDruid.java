package com.yulus.StudentManagerSystem2;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author 云禄
 * @version 1.0
 * 基于 Druid 数据库连接池的工具类
 */

public class JDBCUtilityByDruid {
    private static DataSource dataSource;

    //在静态代码块中完成初始化
    static {

        try {

            Properties properties = new Properties();
            properties.load(new FileInputStream("src/druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public static Connection getConnection() throws SQLException {
            return dataSource.getConnection();
    }

    public static void close(ResultSet resultSet,
                             Statement statement/*既可以接受Statement也可以接受preparedStatement*/,
                             Connection connection) {

        closeAssist(resultSet, statement, connection);
    }

    private static void closeAssist(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            //判断是否为空
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    // 重载4:关闭statement和connection
    public static void close(Statement statement , Connection connection){
        close(null , statement , connection);
    }
}
