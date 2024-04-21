package com.allst.jmh.entity;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * @author Hutu
 * @since 2024-04-21 下午 09:03
 */
public class Tourist implements Runnable {
    private final int touristID;
    private final CyclicBarrier barrier;

    public Tourist(int touristID, CyclicBarrier barrier) {
        this.touristID = touristID;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        System.out.printf("Tourist:%d by bus\n", touristID);
        // 模拟乘客上车的时间开销
        this.spendSeveralSeconds();
        // 上车后等待其他同伴上车
        this.waitAndPrint("Tourist:%d Get on the bus, and wait other people reached.\n");
        System.out.printf("Tourist:%d arrival the destination\n", touristID);
        // 模拟乘客下车的时间开销
        this.spendSeveralSeconds();
        // 下车后稍作等待，等待其他同伴全部下车
        this.waitAndPrint("Tourist:%d Get off the bus, and wait other people get off.\n");
    }

    private void waitAndPrint(String message) {
        System.out.printf(message, touristID);
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            // ignore
        }
    }

    // random sleep
    private void spendSeveralSeconds() {
        try {
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
