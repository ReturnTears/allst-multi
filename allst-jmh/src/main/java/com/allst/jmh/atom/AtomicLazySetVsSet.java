package com.allst.jmh.atom;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger 中set 与lazySet的区别
 *
 * @author Hutu
 * @since 2024-04-18 下午 10:27
 */
@Measurement(iterations = 10)
@Warmup(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class AtomicLazySetVsSet {
    private AtomicInteger ai;

    @Setup(Level.Iteration)
    public void setUp() {
        this.ai = new AtomicInteger(0);
    }

    @Benchmark
    public void testSet() {
        this.ai.set(10);
    }

    @Benchmark
    public void testLazySet() {
        this.ai.lazySet(10);
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(AtomicLazySetVsSet.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opts).run();
    }
}
