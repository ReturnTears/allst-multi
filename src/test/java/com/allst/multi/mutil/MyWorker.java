package com.allst.multi.mutil;

/**
 * @author June
 * @version 1.0
 * @date 2018-07-01
 */
public class MyWorker extends Worker {

    /**
     * 处理类
     * @param input
     * @return
     */
    public static Object handle(Task input) {
        Object output = null;
        try {
            //表示处理task任务的耗时，可能是数据的加工，也可能是操作数据库...
            Thread.sleep(500);
            output = input.getPrice();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return output;
    }
}
