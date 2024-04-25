package com.allst.jmh.tools;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同样保持了10个数量的线程，其中5个线程并发地去修改共享资源x，5个线程并发地去读取共享资源x，
 * 运行如下的基准测试，发现显式锁Lock的表现仍旧比关键字synchronized要好。
 *
 * @author Hutu
 * @since 2024-04-25 下午 09:59
 */
public class ReentrantLockExample6 {
    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(ReentrantLockExample6.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opts).run();
    }

    @State(Scope.Group)
    public static class Test {
        private int x = 10;
        private final Lock lock = new ReentrantLock();

        public void lockInc() {
            lock.lock();
            try {
                x++;
            } finally {
                lock.unlock();
            }
        }

        public int lockGet() {
            lock.lock();
            try {
                return x;
            } finally {
                lock.unlock();
            }
        }

        public void synInc() {
            synchronized (this) {
                x++;
            }
        }

        public int syncGet() {
            synchronized (this) {
                return x;
            }
        }
    }

    @GroupThreads(5)
    @Group("lock")
    @Benchmark
    public void lockInc(Test test) {
        test.lockInc();
    }

    @GroupThreads(5)
    @Group("lock")
    @Benchmark
    public void lockGet(Test test, Blackhole blackhole) {
        blackhole.consume(test.lockGet());
    }

    @GroupThreads(5)
    @Group("sync")
    @Benchmark
    public void syncInc(Test test) {
        test.synInc();
    }

    @GroupThreads(5)
    @Group("sync")
    @Benchmark
    public void syncGet(Test test, Blackhole blackhole) {
        blackhole.consume(test.syncGet());
    }
}
