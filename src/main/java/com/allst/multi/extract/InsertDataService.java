package com.allst.multi.extract;

import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author June
 * @version 1.0
 * @date 2018-06-28
 */
public class InsertDataService extends AbstractInsertDataRunnable {

    public InsertDataService(String batchInsertSQL, DataContainer dataContainer, CountDownLatch countDownLatch) {
        super(batchInsertSQL, dataContainer, countDownLatch);
    }

    @Override
    protected void setValueToPstmt(PreparedStatement pstmt, List<String> rows) throws SQLException {
        for (String row : rows) {
            int index = 1;
            String[] fileds = StringUtils.split(row, "");
            int fieldIndex = 0;
            if (fileds.length != 4) {
                Startup.errorLog.add(row);
            } else {
                pstmt.setString(index++, fileds[fieldIndex++]);
                pstmt.setString(index++, fileds[fieldIndex++]);
                pstmt.setString(index++, fileds[fieldIndex++]);
                pstmt.setString(index++, fileds[fieldIndex++]);
                pstmt.addBatch();

            }
        }
    }
}
