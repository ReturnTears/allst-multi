package com.allst.multi.thread4;

/**
 * @author June
 * @version 1.0
 * @date 2018-07-01
 */
public class FutureClient {

    public MyData request(final String queryStr) {
        final FutureData futureData = new FutureData();
        new Thread(() -> {
            RealData realData = new RealData(queryStr);
            futureData.setRealData(realData);
        }).start();
        return futureData;
    }
}
