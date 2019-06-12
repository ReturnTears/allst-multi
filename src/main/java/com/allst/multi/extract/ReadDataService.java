package com.allst.multi.extract;

import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * @author June
 * @version 1.0
 * @date 2018-06-27
 */
public class ReadDataService extends AbstractReadDataRunnable {

    public ReadDataService(File srcFile, DataContainer dataContainer, CountDownLatch countDownLatch) {
        super(srcFile, dataContainer, countDownLatch);
    }
}
