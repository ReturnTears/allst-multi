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
 * 对比lock和synchronized关键字进行同步的方法在单线程下的性能表现
 *
 * @author Hutu
 * @since 2024-04-25 下午 09:48
 */

// 基准测试的设定，10批Warmup，10批Measurement
@Measurement(iterations = 10)
@Warmup(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
// 单线程
@Threads(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
// 每个线程一个实例
@State(Scope.Thread)
public class ReentrantLockExample4 {
    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder().include(ReentrantLockExample4.class.getSimpleName()).forks(1).build();
        new Runner(opts).run();
    }

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

    private Test test;

    // 每一个批次都会产生一个新的test实例
    @Setup(Level.Iteration)
    public void setUp() {
        this.test = new Test();
    }

    @Benchmark
    public void base(Blackhole hole) {
        hole.consume(test.baseMethod());
    }

    @Benchmark
    public void testLockMethod(Blackhole hole) {
        hole.consume(test.lockMethod());
    }

    @Benchmark
    public void testSyncMethod(Blackhole hole) {
        hole.consume(test.syncMethod());
    }
}
