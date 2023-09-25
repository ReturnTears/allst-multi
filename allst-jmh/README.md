# JMH

## 设置全局的Warmup和Measurement
```text
final Options opts = new OptionsBuilder()
        .include(JMHExample03.class.getSimpleName())
        .forks(1)
        .measurementIterations(5) // 度量执行的批次为5,也就是
        // 说在这5个批次中，对基准方法的执行与调用将会纳入统计
        .warmupIterations(3)      // 在真正的度量之前，首先会对代码进行3个批次的热身，
        // 使代码的运行达到JVM已经优化的效果
        .build();
new Runner(opts).run();

除了在构造Options时设置Warmup和Measurement，我们还可以通过注解的方式指定预热和度量各自的批次:

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Measurement(iterations = 5) // 度量5个批次

@Warmup(iterations = 3)      // 预热3个批次
public class JMHExample03
```

## 在基准测试方法上设置Warmup和Measurement
```text
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Measurement(iterations = 5)
@Warmup(iterations = 2)
public class JMHExample03 {

    @Benchmark
    public void test() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(10);
    }

    /**
    * 预热5个批次
    * 度量10个批次
    */
    @Measurement(iterations = 10)
    @Warmup(iterations = 5)
    @Benchmark
    public void test2() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(1);
    }
```