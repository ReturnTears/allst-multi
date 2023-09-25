package com.allst.jmh.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Hutu
 * @since 2023-09-25 下午 09:31
 */
@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class JmhExample05 {
    // 定义了一个List<String>，但是没有对其进行初始化
    private List<String> list;

    // 将方法标记为@Setup，执行初始化操作
    @Setup
    public void setUp() {
        this.list = new ArrayList<>();
    }

    // 简单地调用list的add 方法
    @Benchmark
    public void measureRight() {
        this.list.add("Test");
    }

    // 该方法什么都不做
    @Benchmark
    public void measureWrong() {
        // do nothing
    }

    // 将方法标记为@TearDown,运行资源回收甚至断言的操作
    @TearDown
    public void tearDown() {
        // 断言list中的元素个数大于0，很明显，measureWrong基准测试将会失败
        assert this.list.size() > 0 : "The list elements must greater than zero";
    }

    public static void main(String[] args) throws RunnerException {
        final Options opts = new OptionsBuilder()
                .include(JmhExample05.class.getSimpleName())
                .jvmArgs("-ea") // 激活断言，enable assertion的意思
                .build();
        new Runner(opts).run();
    }
}
