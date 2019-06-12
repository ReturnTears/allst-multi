package com.allst.multi.extract;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author June
 * @version 1.0
 * @date 2018-06-27
 */
public abstract class ConnectionHelper {

    public static Connection getTargetConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/dx?characterEncoding=utf-8&useSSL=true&serverTimezone=UTC","root","123456");
    }
}
