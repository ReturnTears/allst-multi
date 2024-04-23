package com.allst.jmh.tools;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;

/**
 * 通过继承的方式扩展Semaphore
 * 通过扩展Semaphore来实现优雅的许可证资源释放操
 *
 * @author Hutu
 * @since 2024-04-23 下午 10:49
 */
public class MySemaphore extends Semaphore {
    // 定义线程安全的、存放Thread类型的队列
    private final ConcurrentLinkedQueue<Thread> queue = new ConcurrentLinkedQueue<>();

    public MySemaphore(int permits) {
        super(permits);
    }

    public MySemaphore(int permits, boolean fair) {
        super(permits, fair);
    }

    @Override
    public void acquire() throws InterruptedException {
        super.acquire();
        // 线程成功获取许可证，将其放入队列中
        this.queue.add(currentThread());
    }

    @Override
    public void acquireUninterruptibly() {
        super.acquireUninterruptibly();
        // 线程成功获取许可证，将其放入队列中
        this.queue.add(currentThread());
    }

    @Override
    public boolean tryAcquire() {
        final boolean acquired = super.tryAcquire();
        if (acquired) {
            // 线程成功获取许可证，将其放入队列中
            this.queue.add(currentThread());
        }
        return acquired;
    }

    @Override
    public boolean tryAcquire(long timeout, TimeUnit unit)
            throws InterruptedException {
        final boolean acquired = super.tryAcquire(timeout, unit);
        if (acquired) {
// 线程成功获取许可证，将其放入队列中
            this.queue.add(currentThread());
        }
        return acquired;
    }

    @Override
    public void release() {
        final Thread currentThread = currentThread();
        // 当队列中不存在该线程时，调用release方法将会被忽略
        if (!this.queue.contains(currentThread))
            return;

        super.release();
        // 成功释放，并且将当前线程从队列中剔除
        this.queue.remove(currentThread);
    }

    @Override
    public void acquire(int permits) throws InterruptedException {
        super.acquire(permits);
        // 线程成功获取许可证，将其放入队列中
        this.queue.add(currentThread());
    }

    @Override
    public void acquireUninterruptibly(int permits) {
        super.acquireUninterruptibly(permits);
        // 线程成功获取许可证，将其放入队列中
        this.queue.add(currentThread());
    }

    @Override
    public boolean tryAcquire(int permits) {
        boolean acquired = super.tryAcquire(permits);
        if (acquired) {
            // 线程成功获取许可证，将其放入队列中
            this.queue.add(currentThread());
        }
        return acquired;
    }

    @Override
    public boolean tryAcquire(int permits, long timeout, TimeUnit unit)
            throws InterruptedException {
        boolean acquired = super.tryAcquire(permits, timeout, unit);
        if (acquired) {
            // 线程成功获取许可证，将其放入队列中
            this.queue.add(currentThread());
        }
        return acquired;
    }

    @Override
    public void release(int permits) {
        final Thread currentThread = currentThread();
        // 当队列中不存在该线程时，调用release方法将会被忽略
        if (!this.queue.contains(currentThread))
            return;

        super.release(permits);
        // 成功释放，并且将当前线程从队列中剔除
        this.queue.remove(currentThread);
    }
}
