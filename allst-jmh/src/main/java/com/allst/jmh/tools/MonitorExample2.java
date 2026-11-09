package com.allst.jmh.tools;

import com.google.common.util.concurrent.Monitor;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class MonitorExample2 {

    private final LinkedList<Integer> buffer = new LinkedList<>();
    private static final int MAX_SIZE = 5;
    private final Monitor monitor = new Monitor();

    // ✅ Guard：缓冲区未满时允许生产
    private final Monitor.Guard canProduce = new Monitor.Guard(monitor) {
        @Override
        public boolean isSatisfied() {
            return buffer.size() < MAX_SIZE;
        }
    };

    // ✅ Guard：缓冲区非空时允许消费
    private final Monitor.Guard canConsume = new Monitor.Guard(monitor) {
        @Override
        public boolean isSatisfied() {
            return !buffer.isEmpty();
        }
    };

    /**
     * 生产者：当缓冲区未满时放入元素，否则自动阻塞等待
     */
    public void produce(int value) throws InterruptedException {
        // enterWhen 会阻塞直到 Guard 条件满足，且已持有锁
        monitor.enterWhen(canProduce);
        try {
            buffer.addLast(value);
            System.out.printf("[%s] Produced: %d | Buffer size: %d%n",
                    Thread.currentThread().getName(), value, buffer.size());
        } finally {
            // ⚠️ 必须在 finally 中释放锁，否则其他线程永久阻塞
            monitor.leave();
        }
    }

    /**
     * 消费者：当缓冲区非空时取出元素，否则自动阻塞等待
     */
    public int consume() throws InterruptedException {
        monitor.enterWhen(canConsume);
        try {
            int value = buffer.removeFirst();
            System.out.printf("[%s] Consumed: %d | Buffer size: %d%n",
                    Thread.currentThread().getName(), value, buffer.size());
            return value;
        } finally {
            monitor.leave();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MonitorExample2 demo = new MonitorExample2();

        // 启动 3 个生产者线程
        for (int i = 0; i < 3; i++) {
            final int producerId = (i + 1);
            new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        demo.produce(producerId * 100 + j);
                        TimeUnit.MILLISECONDS.sleep(50);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Producer-" + i).start();
        }

        // 启动 2 个消费者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 15; j++) {
                        demo.consume();
                        TimeUnit.MILLISECONDS.sleep(80);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "Consumer-" + i).start();
        }
    }
}