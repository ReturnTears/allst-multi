package com.allst.multi.thread5;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;

/**
 * @author June
 * @version 1.0
 * @date 2018-07-01
 */
public class ScheduledJob {
    public static void main(String[] args) {
        Temp temp = new Temp();
        ScheduledExecutorService service = newScheduledThreadPool(1);
        ScheduledFuture<?> scheduled = service.scheduleWithFixedDelay(temp, 5, 1, TimeUnit.SECONDS);
    }
}

class Temp extends Thread {
    @Override
    public void run() {
        System.out.println("run...");
    }
}