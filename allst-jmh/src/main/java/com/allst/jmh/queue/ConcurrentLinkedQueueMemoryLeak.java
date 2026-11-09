package com.allst.jmh.queue;

import com.google.common.base.Stopwatch;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * ConcurrentLinkedQueue的内存泄露问题
 * 真正的 ConcurrentLinkedQueue 内存泄漏通常指以下场景（均与本例无关）：
 * 1、迭代器未释放：持有迭代器期间队列持续增长，迭代器内部的 lastRet 节点阻止前驱节点被 GC。
 * 2、生产者远快于消费者：队列无限增长导致 OOM，这是使用不当而非 bug。
 * 3、JDK早期版本的 CAS 失败重试：某些旧版 JDK 中 remove 在高竞争下可能产生临时节点未及时清理，已在后续版本修复。
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
                System.out.println("queue size=" + queue.size());
                break;
            }
            queue.add(object);
            // ② remove方法删除object
            queue.remove(object);
            ++loops;
        }
    }
}
