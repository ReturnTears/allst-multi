# Java并发编程
```
Java并发编程: https://www.bilibili.com/video/av81461839?p=21 (待看线程运行的原理)
给每个线程取好名字
logback是slf4j的一个实现

相关的资料在Java并发编程.xmind中,

```

## 并行和并发
```
单核CPU下, 线程实际上还是串行执行的.操作系统中的任务调度器将CPU的时间片分给不同的线程使用,只是CPU在线程间切换很快,我们感知上是同时运行的,
总结:微观串行,宏观并行

一般将线程轮流使用CPU的做法称为并发, concurrent
并发(concurrent)是同一时间应对(dealing with)多件事情的能力
并行(parallel)是同一时间动手(doing)多件事情的能力
```
## 应用
```
应用之异步调用
从方法调用的角度来讲, 如果
需要等待结构返回,才能继续执行就是同步
不需要等待结构返回,就能继续运行就是异步
注意: 同步在多线程中还有另一个意思, 就是多个线程步调一致
 
应用之提高效率
充分利用多核CPU的优势, 提高运行效率.
需要在多核CPU才能提高效率, 单核仍然是轮流执行

案例1 - 多线程提升效率
基准测试工具选择,使用了比较靠谱的JMH,它会执行程序预热,执行多次测试并平均
CPU核数限制,两种思路
1,使用虚拟机,分配合适的核
2,使用msconfig,分配合适的核,需要重启比较麻烦
并行计算方式的选择
1.最初项直接使用parallel stream , 后来发现它有自己的问题
2.改为了自己手动控制thread,实现简单的并行计算

使用JMH需要使用它的骨架文件
mvn archetype:generate -DinteractiveMode=false -DarchetypeGroupId=org.openjdk.jmh 
-DarchetypeArtifactId=jmh-java-benchmark-archetype =DgroupId=org.sample -DartifactId=test
-Dversion=1.0

详情查看: https://www.bilibili.com/video/av81461839?p=9

```

## Java线程
```
thread 和 runnable的关系
分析Thread的源码,理清它与Runnable的关系
小结:
方式1是把线程和任务合并在一起, 方法2是线程和任务分开了
用runnable更容易与线程池等高级API配合
用runnable让任务类脱离了Thraed继承体系, 更灵活


方式3, FutureTask配合Thread
FutureTask能够接收Callback类型参数, 用来处理有返回结果的情况

观察多个线程同时运行
1.理解交替执行
2.谁先谁后,不由我们控制, 由计算机底层的任务管理器决定


查看进程线程的方法
windows
任务管理器可以查看进程和线程数, 也可以用来杀死进程
tasklist 查看进程
taskkill 杀手进程
关键字查找进程: tasklist | findstr java
强制删除某个进程: taskkill /F /PID <pid>

linux
ps -fe 查看所有进程
ps -fT -p <PID> 查看某个进程(PID)的所有线程
kill pid 杀手进程
top 按大写H切换是否显示线程
top -H -p <PID>
jps

查看Java相关进程: ps -fe | grep java

Java
jps命令查看所有进程
jstack <PID>查看某个Java进程PID的所有线程状态
jconsole来查看某俄国Java进程中线程的运行情况

jconsole远程监控配置
1.需要以如下方式运行你的Java类
java -Djava.rmi.server.hostname=`ip地址` -Dcom.sun.management.jmxremote 
-Dcom.sun.management.jmxremote.port=`连接端口`
-Dcom.sun.management.jmxremote.ssl=是否安全连接
-Dcom.sun.management.jmxremote.authenticate=是否认证 java类
2.修改/etc/hosts文件将127.0.0.1映射至主机名
如果要认证访问,还需要做如下步骤:
1.复制jmxremote.password
2.修改jmxremote.password和jmxremote.access文件的权限为600即文件所有者读写
3.连接时填入controlRole(用户名), R&D(密码)


```

## 线程运行的原理
```text
栈与栈帧
Java virtual machine stacks (Java虚拟机栈)
JVM中由堆,栈,方法区,本地方法栈构成内存空间,每个线程启动后虚拟机都会为其分配一块独立的栈内存空间
每个栈由多个栈帧组成(Frame)组成,对应着每次方法调用时所占用的内存
每个线程只能有一个活动栈帧,对应着当前正在执行的那个方法

线程上下文切换(Thread context switch)
因为一下原因导致CPU不再执行当前的线程了, 转而执行另一个线程的代码
线程的CPU时间片用完了
垃圾回收
有更高优先级的线程需要运行
线程自己调用了sleep,yield,wait,join,park,synchronized,lock等方法程序
当

```