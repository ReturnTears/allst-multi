# JMH
```text
JMH是Java Micro Benchmark Harness的简写，是专门用于代码微基准测试的工具集（toolkit）

```
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

## 编写正确的微基准测试用例
```text
1、避免DCE（Dead Code Elimination）
   所谓Dead Code Elimination是指JVM为我们擦去了一些上下文无关，甚至经过计算之后确定压根不会用到的代码
2、使用Blackhole
   JMH提供了一个称为Blackhole的类，可以在不作任何返回的情况下避免Dead Code的发生，Blackhole直译为“黑洞”，与Linux系统下的黑洞设备/dev/null非常相似
   Blackhole可以帮助你在无返回值的基准测试方法中避免DC（Dead Code）情况的发生。
3、避免常量折叠（Constant Folding）
   常量折叠是Java编译器早期的一种优化——编译优化。
   在javac对源文件进行编译的过程中，通过词法分析可以发现某些常量是可以被折叠的，也就是可以直接将计算结果存放到声明中，而不需要在执行阶段再次进行运算。
4、避免循环展开（Loop Unwinding）
   
5、Fork用于避免Profile-guided optimizations
   将Fork设置为1的时候，也就是说每一次运行基准测试时都会开辟一个全新的JVM进程对其进行测试

```