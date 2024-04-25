package com.allst.jmh.tools;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程下两者只进行读取操作的性能表现
 * 执行基准测试会发现在10个线程的情况下，显式锁Lock的性能要优于synchronized关键字
 *
 * @author Hutu
 * @since 2024-04-25 下午 09:52
 */

/*@Measurement(iterations = 10)
@Warmup(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)*/
public class ReentrantLockExample5 {

    public static class Test {
        private int x = 10;
        private final Lock lock = new ReentrantLock();

        // 基准方法
        public int baseMethod() {
            return x;
        }

        // 使用lock进行方法同步
        public int lockMethod() {
            lock.lock();
            try {
                return x;
            } finally {
                lock.unlock();
            }
        }

        // 使用关键字synchronized进行方法同步
        public int syncMethod() {
            synchronized (this) {
                return x;
            }
        }
    }

    // 10个线程进行测试
    /*@GroupThreads(10)
    @Group("base")
    @Benchmark
    public void base(Test test, Blackhole hole) {
        hole.consume(test.baseMethod());
    }*/

    // 10个线程进行测试
    /*@GroupThreads(10)
    @Group("lock")
    @Benchmark
    public void testLockMethod(Test test, Blackhole hole) {
        hole.consume(test.lockMethod());
    }*/

    // 10个线程进行测试
    /*@GroupThreads(10)
    @Group("sync")
    @Benchmark
    public void testSyncMethod(Test test, Blackhole hole) {
        hole.consume(test.syncMethod());
    }*/

    /*public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(ReentrantLockExample5.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opts).run();
    }*/
}
