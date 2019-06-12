package com.allst.multi.utils;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static org.apache.commons.dbcp.BasicDataSourceFactory.createDataSource;

/**
 * 使用dbutils链接数据库
 * @author June
 */
public class JdbcUtils {

    private static final Logger log = LoggerFactory.getLogger(JdbcUtils.class);

    /**
     * 定义数据源
     */
    private static DataSource ds;

    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    static {
        try {
            Properties prop = new Properties();
            InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
            prop.load(in);
            BasicDataSourceFactory factory = new BasicDataSourceFactory();
            ds = createDataSource(prop);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource(){
        return ds;
    }

    public static Connection getConnection() throws SQLException {
        try{
            // 得到当前线程上绑定的连接
            Connection conn = tl.get();
            // 代表线程上没有绑定连接
            if(conn == null){
                conn = ds.getConnection();
                tl.set(conn);
            }
            return conn;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
        String sql = "insert into users (username,password,password_salt) values(?,?,?)";
        Object params[] = {"ten","root12310","417b499a1314fb58184a659fd054aaa"};
        try {
            int flag = runner.update(sql, params);
            log.info("成功插入" + flag + "条数据");
        } catch (SQLException e) {
            log.error(e.getMessage(),e);
            e.printStackTrace();
        }
    }
}
