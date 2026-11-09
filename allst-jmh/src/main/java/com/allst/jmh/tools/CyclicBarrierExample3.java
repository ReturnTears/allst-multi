package com.allst.jmh.tools;

import com.allst.jmh.entity.Tourist;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier传递 Runnable barrierAction
 */
public class CyclicBarrierExample3 {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        final int touristCount = 10;

        // ✅ 关键：传入 barrierAction，每轮所有线程到齐后自动执行
        final CyclicBarrier barrier = new CyclicBarrier(touristCount + 1, () -> {
            // 此任务由最后一个到达屏障的线程执行
            // 注意：此时所有参与者均已到达但尚未释放
            System.out.println("=== Barrier Action: All " + touristCount + " tourists synchronized at round ===");
        });

        // 启动游客线程
        for (int i = 0; i < touristCount; i++) {
            new Thread(new Tourist(i, barrier), "Tourist-" + i).start();
        }

        // 导游（主线程）参与第一轮同步：等所有人上车
        barrier.await();
        System.out.println("Tour Guide: All tourists are ON the bus. Departing...");

        // 导游参与第二轮同步：等所有人下车
        barrier.await();
        System.out.println("Tour Guide: All tourists are OFF the bus. Trip complete.");
    }
}
