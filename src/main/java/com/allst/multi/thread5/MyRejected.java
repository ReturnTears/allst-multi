package com.allst.multi.thread5;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author June
 * @version 1.0
 * @date 2018-07-01
 */
public class MyRejected implements RejectedExecutionHandler {

    public MyRejected() {
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("自定义处理...");
        System.out.println("当前被拒绝任务为 : " + r.toString());
    }
}
