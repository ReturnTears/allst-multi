package com.allst.multi.extract;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接池
 * @author June
 * @version 1.0
 * @date 2018-06-27
 */
public class ConnectionPool {

    private static final String URL = "jdbc:mysql://localhost:3306/dx?characterEncoding=utf-8&useSSL=true&serverTimezone=UTC";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456";
    private DataSource dataSource;
    private static ConnectionPool pool = new ConnectionPool();

    private ConnectionPool() {
        try {
            dataSource = createDataSource(URL, USER_NAME, PASSWORD);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static final ConnectionPool getInstance() {
        return pool;
    }

    public synchronized final Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private DataSource createDataSource(String jdbcUrl, String user, String password) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        dataSource.setMaxPoolSize(100);
        dataSource.setMinPoolSize(10);
        dataSource.setInitialPoolSize(10);
        dataSource.setAcquireIncrement(5);
        return dataSource;
    }
}
