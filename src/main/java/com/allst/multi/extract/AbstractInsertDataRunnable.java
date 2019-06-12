package com.allst.multi.extract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author June
 * @version 1.0
 * @date 2018-06-27
 */
public abstract class AbstractInsertDataRunnable implements Runnable {

    private CountDownLatch countDownLatch;

    private DataContainer dataContainer;

    private Connection connection;

    private String batchInsertSQL;

    public AbstractInsertDataRunnable(String batchInsertSQL, DataContainer dataContainer, CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
        this.connection = ConnectionPool.getInstance().getConnection();
        this.batchInsertSQL = batchInsertSQL;
        this.dataContainer = dataContainer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object rows = this.dataContainer.getData();
                if (rows == null || rows instanceof Integer) {
                    break;
                } else {
                    batchInsertData(connection, (List) rows);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void batchInsertData(Connection connection, List rows) {
        PreparedStatement ps = null;
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(batchInsertSQL);
            setValueToPstmt(ps, rows);
            ps.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置值到PreparedStatement
     * @param pstmt
     * @param rows
     * @throws SQLException
     */
    protected abstract void setValueToPstmt(PreparedStatement pstmt, List<String> rows) throws SQLException;
}
