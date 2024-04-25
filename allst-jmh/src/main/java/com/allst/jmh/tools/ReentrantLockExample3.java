package com.allst.jmh.tools;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多个原子性方法的组合不能确保原子性
 *
 * @author Hutu
 * @since 2024-04-25 下午 09:41
 */
public class ReentrantLockExample3 {
    // 在Accumulator中，所有的方法都是线程安全的，每一个方法的执行都是原子性的，不可被中断
    private static class Accumulator {
        private static final Lock lock = new ReentrantLock();
        private int x = 0;
        private int y = 0;

        void addX() {
            lock.lock();
            try {
                x++;
            } finally {
                lock.unlock();
            }
        }

        void addY() {
            lock.lock();
            try {
                y++;
            } finally {
                lock.unlock();
            }
        }

        int getX() {
            lock.lock();
            try {
                return x;
            } finally {
                lock.unlock();
            }
        }

        int getY() {
            lock.lock();
            try {
                return y;
            } finally {
                lock.unlock();
            }
        }
    }

    private static class AccumulatorThread extends Thread {
        private final Accumulator accumulator;

        private AccumulatorThread(Accumulator accumulator) {
            this.accumulator = accumulator;
        }

        @Override
        public void run() {
            // 不断地调用addX和addY，根据我们的期望，x和y应该一样，但是事实并非如此
            while (true) {
                accumulator.addX();
                accumulator.addY();
                // 检查不相等的情况
                if (accumulator.getX() != accumulator.getY()) {
                    System.out.printf("The x:%d not equals y:%d\n", accumulator.getX(), accumulator.getY());
                }
            }
        }
    }

    public static void main(String[] args) {
        // 启动10个线程
        final Accumulator accumulator = new Accumulator();
        for (int i = 0; i < 10; i++) {
            new AccumulatorThread(accumulator).start();
        }
    }
}
