package com.allst.multi.extract;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author June
 * @version 1.0
 * @date 2018-06-27
 */
public abstract class AbstractReadDataRunnable implements Runnable {

    private CountDownLatch countDownLatch;

    private DataContainer dataContainer;

    private File srcFile;

    private static int BLOCK_SIZE = 5000;

    public AbstractReadDataRunnable(File srcFile, DataContainer dataContainer, CountDownLatch countDownLatch) {
        this.srcFile = srcFile;
        this.dataContainer = dataContainer;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        FileReader fr = null;
        BufferedReader bf = null;
        try {
            fr = new FileReader(this.srcFile);
            bf = new BufferedReader(fr);
            String line = null;
            int rowNum = 0;
            List rows = new ArrayList(BLOCK_SIZE);
            while ((line = bf.readLine()) != null) {
                rowNum++;
                rows.add(line);
                if (rowNum % BLOCK_SIZE == 0) {
                    dataContainer.insertData(rows);
                    rows = new ArrayList(BLOCK_SIZE);
                }
            }
            if (rows.size() > 0) {
                dataContainer.insertData(rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            insertEndFlag(this.dataContainer);
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void insertEndFlag(DataContainer data) {
        try {
            data.insertData(EtlConstants.EXTRACT_END_FLAG);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
