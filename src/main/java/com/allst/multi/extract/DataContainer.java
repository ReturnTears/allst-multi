package com.allst.multi.extract;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 数据容器
 * @author June
 * @version 1.0
 * @date 2018-06-27
 */
public class DataContainer {

    private boolean ending = false;
    private int currCount = 0;
    private int _queueCapacity;
    /**
     * ConcurrentLinkedQueue线程安全队列
     */
    private ConcurrentLinkedQueue<Object> _queue = new ConcurrentLinkedQueue<>();

    public DataContainer() {
        this(20);
    }

    public DataContainer(int queueCapacity) {
        this._queueCapacity = queueCapacity;
    }

    public synchronized void insertData(Object data) throws InterruptedException {
        while (currCount > _queueCapacity) {
            wait();
        }
        currCount++;
        _queue.add(data);
        notifyAll();
    }

    public synchronized Object getData() throws InterruptedException {
        while (currCount <= 0) {
            if (!ending) {
                wait();
            } else {
                break;
            }
        }
        currCount--;
        Object res = _queue.poll();
        notifyAll();
        if (res == null || res instanceof Integer) {
            this.ending = true;
        }
        return res;
    }
}
