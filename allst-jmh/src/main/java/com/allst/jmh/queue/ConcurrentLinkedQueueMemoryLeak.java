package com.allst.jmh.queue;

import com.google.common.base.Stopwatch;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * ConcurrentLinkedQueue的内存泄露问题
 *
 * @author Hutu
 * @since 2024-05-31 下午 11:08
 */
public class ConcurrentLinkedQueueMemoryLeak {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<>();
        // 这行代码会导致内存泄露问题
        queue.add(new Object());
        Object object = new Object();
        int loops = 0;
        // 循环10秒, 方便实用JDK诊断工具健康执行前后的内存变化
        TimeUnit.SECONDS.sleep(10);
        Stopwatch watch = Stopwatch.createStarted();
        while (true) {
            if (loops % 10000 == 0 && loops != 0) {
                long elapsedMs = watch.stop().elapsed(TimeUnit.MILLISECONDS);
                System.out.printf("loops=%d duration=%d MS%n", loops, elapsedMs);
                watch.reset().start();

                //break;
            }
            queue.add(object);
            // ② remove方法删除object
            queue.remove(object);
            ++loops;
        }
    }
}
