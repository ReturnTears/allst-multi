package com.allst.multi.thread4;

/**
 * @author June
 * @version 1.0
 * @date 2018-07-01
 */
public class FutureData implements MyData {

    private RealData realData;

    private boolean isReady = false;

    public synchronized void setRealData(RealData data) {
        if (isReady) {
            return;
        }
        this.realData = data;
        isReady = true;
        // 进行通知
        notify();
    }

    @Override
    public synchronized String getRequest() {
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.realData.getRequest();
    }
}
