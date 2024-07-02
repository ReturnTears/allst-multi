# Netty的学习之旅

## 学习的关键点:

### 强势技能的学习

### 重视基础

### 节奏感

### 思维方式的改进
    思维方式的改变, 总结遇到的情况，要以不同的角度去思考问题
### 效率工具的使用
    VPN
### 主动输出
    分享

# Netty
    概念:
    Netty is an asynchronous event-driven network application framework 
    for rapid development of maintainable high performance protocol servers & clients.
    Netty是一个异步事件驱动的网络应用程序框架,用于快速开发可维护的高性能协议服务器和客户端。
    
    Netty是一个NIO客户端服务器框架，可以快速轻松地开发协议服务器和客户端等网络应用程序。它极大地简化并简化了TCP和UDP套接字服务器等网络编程。
    
    “快速简便”并不意味着最终的应用程序会受到可维护性或性能问题的影响。Netty经过精心设计，具有丰富的协议，
    如FTP，SMTP，HTTP以及各种二进制和基于文本的传统协议。因此，Netty成功地找到了一种在不妥协的情况下实现易于开发，性能，稳定性和灵活性的方法。
    
    **特征**
    一、设计
        1、适用于各种传输类型的统一API - 阻塞和非阻塞套接字
        2、基于灵活且可扩展的事件模型，可以清晰地分离关注点
        3、高度可定制的线程模型 - 单线程，一个或多个线程池，如SEDA
        4、真正的无连接数据报套接字支持（自3.1起）
    二、便于使用
        1、详细记录的Javadoc，用户指南和示例
        2、没有其他依赖项，JDK 5（Netty 3.x）或6（Netty 4.x）就足够了
        注意：某些组件（如HTTP / 2）可能有更多要求。 有关更多信息，请参阅 “要求”页面。
    三、性能
        1、更高的吞吐量，更低的延迟
        2、减少资源消耗
        3、最小化不必要的内存复制
    四、安全
        1、完整的SSL / TLS和StartTLS支持
    五、社区
        1、早发布，经常发布
        2、自2003年以来，作者一直在编写类似的框架，他仍然发现你的反馈很珍贵！
    
    ---------------------------------------------
    异步同步的区别:
    
    ---------------------------------------------
    事件驱动:
 
## Netty介绍

## Netty架构实现

## Netty模块分析

## Netty Http Tunnel

## Netty对Socket的实现

## Netty压缩与解压缩
    -- 
    对数据传输过程中的压缩
## Netty对于RPC的支持 

## WebSocket实现与原理分析

## WebSocket连接建立方式与生命周期分解

## WebSocket服务端和客户端开发

## RPC框架分析

## Google Protobuf使用方式分析
    
## Apache Thrift使用方式与文件编写方式分析

## Netty大文件传送支持

## 可扩展的事件模型

## Netty统一通信API

## 零拷贝在Netty中的实现与支持

## TCP粘包与拆包分析

## NIO模型在Netty中的实现

## Netty编码开发技术

## Netty重要类与接口源代码剖析

## Channel分析

## 序列化讲解


 
 ## 注意:
    springboot 2.1.5 + netty 4.1.10.Final 会报错java.lang.ClassNotFoundException: jdk.internal.misc.Unsafe
    springboot 2.1.5 + netty 4.1.32.Final
    
```
使用curl发送请求:
curl "http://localhost:8899"    (默认GET请求)
curl -X POST "http://localhost:8899"
curl -X PUT "http://localhost:8899"
curl -X DETELE "http://localhost:8899"
channel的执行顺序流程 : 
channel added           添加         
channel registered      注册
channel active          激活
请求方法名: GET           执行channelRead0
channel inactive        未激活
channel unregistered    未注册

浏览器的执行机制和curl是不一样的

```
    
 ## Netty(Current Study)
 🍎 Netty的介绍
    -- 1) Netty是由JBOSS提供的一个Java开源框架，现为GitHub上的独立项目。
    -- 2) Netty是一个异步的、基于事件驱动的网络应用框架，用以快速开发高性能、高可靠性的网络IO程序
    -- 3) Netty主要是针对TCP协议下，面向Clients端的高并发应用，或者peer-to-peer场景下的大量数据持续传输的应用。
    -- 4) Neety本质是一个NIO框架，适用于服务器通讯相关的多种应用场景。
    -- 5) 要透彻理解Netty，需要先学习NIO，这样才能阅读Netty的源码
 🍎 Netty的应用场景
    -- 互联网行业：1、分布式系统中，各个节点之间需要远程服务调用，高性能的RPC框架必不可少。2、阿里分布式服务框架Dubbo就用到了Netty
    -- 游戏行业：Netty作为高性能的基础通信组件
    -- 大数据领域：Hadoop的高性能通信和序列化组件（AVRO实现数据我呢见共享）的RPC框架
    >> I/O模型: 
        -- I/O简单理解:用什么样的通道进行数据的发送和接收，很大程度上决定了程序通信的性能
        -- Java共支持3种网络编程模型I/O模式: BIO、NIO、AIO
        -- BIO（同步并阻塞/传统阻塞型），服务器实现模式为一个连接对应一个线程，即客户端有链请求时服务端就需要启动一个线程进行处理
        -- 适用于连接数目较小且固定的架构，这种方式对服务器资源要求比较高，并发局限于应用中，编程比较简单
        -- NIO（同步非阻塞），服务器实现模式为一个线程处理多个请求(连接)，即客户端发送的连接请求都会注册到多路复用器上，多路复用器轮询到连接有I/O请求就进行处理
        -- 适用于连接数目多且连接比较短（轻操作）的架构，比如聊天服务器，弹幕系统，服务器间通讯。编程比较复杂，JDK4开始支持
        -- AIO（异步非阻塞），AIO引入异步通道的概念，采用了Proactor模式，简化了程序编写，有效的请求才启动线程，它的特点时先由操作系统完成后才通知服务端线程启动线程去处理，，一般适用于连接数较多且连接时间较长的应用
        -- 该方式适用于连接数目较多且连接比较长（重操作）的架构， 比如，相册服务器，充分调用OS（操作系统）参与并发操作，编程比较复杂，JDK7开始支持
    >> Java BIO
        就是传统的Java IO编程，开源通过线程池机制改善线程开销问题，实现多客户端连接服务器。
        BIO编程简单流程：
        1、服务器端启动一个ServerSocket
        2、客户端启动Socket对服务器进行通信，默认情况下服务器端需要对每个客户端建立一个线程与之通信
        3、客户端发出请求后，先咨询服务器是否有线程响应，如果没有则会等待，或者被拒绝
        4、如果有响应，客户端线程会等待请求结束后，在继续执行
    >> telnet的使用
        telnet host port - telnet 127.0.0.1 6666
        ctrl + ] 进入交互状态
        send "msg" 发送消息
    >> Java NIO
        全称Java non-blocking IO 是指JDK提供的新API。放在java.nio包及其子包下，对原Java。io包中的很多类进行改写。
        NIO有三大核心部分：Channel(通道)，Buffer(缓冲区)，Selector(选择器)
        NIO是面向缓冲区，或者面向块编程的。数据读取到一个它稍后处理的缓冲区，需要时可在缓冲区种前后移动，
        这就增加了处理过程中的灵活性，使用它可以提供非阻塞式的高伸缩性网络。
        Java NIO的非阻塞模式，使一个线程从某通道发送请求或者读取数据，但是它仅能得到可用的数据，如果目前没有可用数据时，就什么都不会获取。
        而不是保持线程阻塞。所以直至数据变得可以读取之前，该线程还可以做其他的事情。通俗的讲：NIO是可以做到用一个线程来处理多个操作的。
        Http2.0使用了多路复用的技术，做到一个连接并发处理多个请求，而且并发请求的数量比Http1.1大了好几个数量级。
        NIO和BIO的比较：
        -- BIO以流的方式处理数据，而NIO以块的方式处理数据，块I/O的效率比流I/O高很多
        -- BIO是阻塞的，NIO则是非阻塞的
        -- BIO基于字节流和字符流进行操作，而NIO基于Channel和Buffer进行操作， 数据总是从通道读取到缓冲区中，或者从缓冲区中写入到通道中。
           selector用于监听多个通道的事件（连接请求，数据到达），因此使用单个线程就可以监听多个客户端通道。
        NIO三大核心关系：
        selector channel buffer
        -- 每个channel都会对应一个buffer
        -- selector对应一个线程，一个线程对应多个channel（连接），或者说一个selector对应多个channel，又或者说多个channel注册到了一个selector
        -- 程序切换到那个channel是由事件决定的，event是一个重要的概念
        -- selector会根据不同的事件，在各个通道上切换
        -- buffer就是一个内存块，底层是有一个数组
        -- 数据的读取写入是通过buffer， 这个和BIO是有区别的，BIO要么是输入流，要么是输出流，不能双向。但是NIO的buffer是可以读写的，需用到flip()切换读写模式。
        -- channel是双向的，可以反应底层操作系统的情况，比如Linux：底层的操作系统通道就是双向的。
        缓冲区（Buffer）
        缓冲区（Buffer）：缓冲区本质上是一个可以读写数据的内存块，可以理解成是一个容器对象（含数组），该对象提供了一组方法，可以轻松的使用内存块，
                        缓冲区对象内置了一些机制，能够跟踪和记录缓冲区的状态变化情况。channel提供从文件，网络读取数据的渠道，但是读取或写入都必须经由Buffer
         Buffer类及其子类
         在NIO中， BUffer是一个顶层父类，它是一个抽象类，类的层级关系:
         常用Buffer子类:
         ByteBuffer:存储字节数据到缓冲区
         ShortBuffer: 存储字符串数据到缓冲区
         CharBuffer: 存储字符数据到缓冲区
         IntBuffer: 存储整数数据到缓冲区
         LongBuffer: 存储长整型数据到缓冲区
         DoubleBuffer: 存储小数到缓冲区
         FloatBuffer: 存储小数到缓冲区
         Buffer类定义了所有的缓冲区都具有的四个属性来提供关于其所包含的数据元素的信息
         属性          描述
         capacity      容量，即可以容纳的最大数据量，在缓冲区创建时被设定并且不能改变。
         limit         表示缓冲区的当前终点，不能对缓冲区超过极限的位置进行读写操作。且极限是可以修改的。
         position      位置，下一个要被读取的或写的元素的索引，每次读写缓冲区时都会改变值，为下一次读写做准备。
         mark          标记
         
         通道（Channel）
         1、基本介绍：NIO的通道类似于流，区别如下：
         -- 通道可以同时进行读写，而流只能读或者只能写
         -- 通道可以实现异步读写数据
         -- 通道可以从缓冲区读取数据，也可以写数据到缓冲区
         2、BIO中的stream是单向的
         3、Channel是NIO中的一个接口
            public interface CHannel extends Closeable {}
         4、常用Channel类：
            FileChannel、DatagramChannel、ServerSocketChannel和SocketChannel
         5、FileChannel用于文件的数据读写，
            DatagramChannel用于UDP数据读写
            ServerSocketChannel和SocketChannel用于TCP的数据读写 
         6、NIO还提供了MappedByteBuffer， 可以让文件直接在内存(堆外的内存)中进行修改，而如何同步到文件由NIO来完成。
         7、NIO还支持通过多个Buffer（即Buffer数组）完成读写操作。即Scattering和Gathering
        
        selector（选择器）
        基本介绍:
            1、Java的NIO，用非阻塞的IO方式， 可以用一个线程， 处理多个客户端的连接，就会使用到Selector(选择器)
            2、selector能够检测多个注册通道上是否有事件发生(注意：多个channel以事件的方式可以注册到同一个selector)
               如果有事件发生，便获取事件然后针对每个事件进行相应的处理。这样就可以只用一个单线程去管理多个通道，
               也就是管理多个连接和请求
            3、只有在连接/通道真正有读写事件发生时， 才会进行读写，就大大地减少了系统开销， 不必要为每个连接创建一个线程， 
            4、避免多线程之间的上下文切换的开销
        特点:
            1、Netty的IO线程NioEventLoop聚合了Selector选择器（也叫做多路复用器）， 可以同时并发处理成百上千个客户端来连接
            2、当线程从某客户端socket通道进行读写数据时， 如没有数据可用时， 该线程可以进行其他任务
            3、线程通常将非阻塞IO的空闲时间用于在其他通道上执行IO操作，所有单独的线程可以管理多个输入和输出通道
            4、由于读写操作都是非阻塞的，这就可以充分提升IO线程的运行效率，避免由于频繁IO阻塞导致线程挂起
            5、一个IO线程可以并发处理N个客户端连接个读写操作，这从根本上解决了传统同步阻塞I/O一连接一线程模型，架构的性能，弹性伸缩能力和可靠性都得到了极大的提升。
        selector类及其相关方法:
            selector类是一个抽象类， 常用方法如下：
            public abstract class Selector implements Closeable {
                // 得到一个选择器对象
                public static Selector open();
                // 监控所有注册通道， 当其中有IO操作可进行时，将对应的selectionKey加入到内部集合中并返回， 参数用于设置超时时间
                public int select()
                // 从内部集合中得到所有的selectionKey
                public Set<SelectionKey> selectdKeys();
            }
            
            
### 🍎 Netty核心模块
```text
Netty框架主要由三个功能模块组成，分别是核⼼（Core）功能模 块、传输服务（Transport Services）功能模块和协议⽀持（Protocol Support）功能模块。
核⼼（Core）功能模块：⽤于核⼼功能的定义与实现。
1、Zero-Copy-Capable Rich Byte Buffer：⽀持零拷⻉的字节缓冲（Rich Byte Buffer表⽰多字节的缓冲对象），实现了Netty框架版本的Byte Buffer功能（针对JDK的ByteBuffer功能进⾏了强⼤的优化）。
2、Universal Communication API：通⽤通信API。
3、Extensible Event Model：可扩展的事件模型。

传输服务（Transport Services）功能模块：⽤于具体⽹络传输的定义与实现。
1、In-VM pipe：内部JVM传输管道的实现。
2、HTTP Tunnel：HTTP传输协议的实现。
3、Socket Datagram：Socket TCP、Socket UDP传输协议的实现。

协议⽀持（Protocol Support）功能模块：相关协议的⽀持。
1、借助单元测试的传统⽂本、⼆进制协议。
2、压缩、⼤⽂件传输协议、实时流传输协议。
3、Http & WebSocket、SSL、StartTLS、Google Protobuf等协议。

```
#### Netty Bootstrap⼊⼝模块
```text
Netty Bootstrap模块相当于Netty框架的启动器，因此也称为⼊⼝模块，主要包括Bootstrap接⼝和ServerBootstrap接⼝，
在 io.netty.bootstrap 模 块 中 定 义 了Bootstrap接⼝类和ServerBootstrap接⼝类。其中，Bootstrap接⼝主要负责客⼾端的启动，ServerBootstrap接⼝主要负责服务端的启动。
Bootstrap接⼝类和ServerBootstrap接⼝类在启动过程中，主要功能是创建、初始化和配置核⼼的Channel对象。

```
#### Netty Channel传输通道模块
```text
Channel传输通道模块是Netty框架⽹络操作的抽象类，主要包括基本的I/O操作（例如：bind、connect、read、write等）。
另外，Channel传输通道模块还包括了Netty框架相关的⼀些功能，⽐如如何获取该Channel模块的事件驱动循环对象（EventLoop)。

Netty框架设计Channel模块的⽬的，主要是为了解决传统套接字（Socket）⽹络编程中的烦琐不易之处。相信但凡有过套接字
（Socket）⽹络编程开发经验的读者⼀定深有感触，使⽤底层Socket开发⽹络应⽤的难度⼤，且成本⾼，⼀不⼩⼼就会掉⼊各种陷阱之中耗费精⼒。

⽽Netty框架设计出的Channel模块很好地解决了上述问题，它所提供的⼀系列API，极⼤地降低了直接使⽤套接字（Socket）进⾏⽹络开发的难度。
尤其是针对原⽣Java NIO的开发，Netty框架的Channel模块较好地解决了诸多痛点。

Channel接⼝API采⽤了Facade模式进⾏统⼀封装，将⽹络I/O操作及其相关的其他操作统⼀封装起来，很好地为SocketChannel接⼝和ServerSocketChannel接⼝提供了统⼀的视图。

Channel模块是使⽤了聚合（⾮包含）的⽅式来实现的，将相关功能聚合在Channel 之中进⾏统⼀的管理与调度。 其中 ，NioSocketChannel接⼝基于Java NIO实现了Netty框架下的TCP协议的NIO传输

```

#### Netty EventLoop事件循环模块
```text
Netty框架是基于事件驱动模型的，具体是使⽤相应的事件来通知状态改变或者操作状态改变。
Channel模块是Netty框架⽹络操作的抽象类,EventLoop模块主要⽤于Channel模块实现I/O操作处理，这两个模块配合在⼀起来参与并完成I/O操作。

Netty框架设计的EventLoop模块实现了控制流、多线程和并发功能，结合Channel模块能够帮助⽤⼾实现周期性的任务调度。
⽐如：当⼀个客⼾端连接到达时，Netty就会注册⼀个Channel对象，然后由EventLoopGroup接⼝（可以理解为EventLoop接⼝组合）分配⼀个EventLoop对象绑定到这个Channel对象上。
此时这个Channel对象在整个⽣命周期中，都是由这个绑定的EventLoop对象来提供相应的服务。

根据Netty官⽅⽂档中的解释，对于Channel、Thread（线程）、EventLoop和EventLoopGroup之间的关系⼤致总结如下：
1、⼀个EventLoopGroup对象包含⼀个或多个EventLoop对象。
2、⼀个EventLoop对象在其⽣命周期内只能绑定⼀个Thread对象。
3、凡是由EventLoop对象处理的I/O事件都由其所绑定的Thread对象来处理。
4、⼀个Channel对象在其⽣命周期内只能注册⼀个EventLoop对象。
5、⼀个EventLoop对象可能被分配处理多个Channel对象（即EventLoop与Channel是1:n的关系）。
6、⼀个Channel对象上所有的ChannelHandler事件由其所绑定的EventLoop对象中的I/O线程进⾏处理。
7、千万不要阻塞Channel对象的I/O线程，这可能会影响该EventLoop对象中所包含的其他Channel对象的事件处理。

```

#### Netty ChannelFuture异步通知接⼝
```text
Netty框架被设计为异步⾮阻塞⽅式，即所有I/O操作都为异步的，也就是应⽤程序不会⻢上得知客⼾端发送的请求是否已经被服务器端处理完成了。

因此，Netty框架设计了⼀个ChannelFuture异步通知接⼝，通过该接⼝定义的addListener()⽅法注册⼀个ChannelFutureListener对象，
当操作执⾏成功或者失败时，监听对象就会⾃动触发并返回结果。

```

#### ChannelHandler与ChannelPipeline接⼝
```text
ChannelHandler接⼝是Netty框架中的核⼼部分，是负责管理所有I/O数据的应⽤程序逻辑容器。具体来讲，ChannelHandler就是⽤来管理连接请求、数据接收、异常处理和数据转换等功能的接⼝。

ChannelHandler接⼝有两个核⼼⼦类：ChannelInboundHandler和ChannelOutboundHandler。其中，ChannelInboundHandler⼦类负责处理输⼊数据和输⼊事件，
⽽ChannelOutboundHandler⼦类负责处理输出数据和输出事件。

ChannelPipeline接⼝则为ChannelHandler接⼝提供了⼀个容器，并定义了⽤于沿着链传播输⼊和输出事件流的API。
⼀个数据或者事件可能会被多个Handler处理，在这个过程中，数据或事件流经ChannelPipeline接⼝后交由ChannelHandler接⼝处理。

在这个处理过程中，⼀个ChannelHandler对象接收输⼊数据并处理完成后交给下⼀个ChannelHandler对象，或者什么都不做直接交给下⼀个ChannelHandler对象进⾏处理。

当⼀个数据流进⼊ChannlePipeline时，会从ChannelPipeline头部开始传给第⼀个ChannelInboundHandler。
当第⼀个处理完后再传给下⼀个，⼀直传递到ChannlePipeline的尾部。相反地，当⼀个数据流写出时，其会从管道尾部开始操作，
当处理完成后会传递给前⼀个ChannelOutboundHandler对象。

当ChannelHandler对象被添加到ChannelPipeline对象时，其会被分配⼀个ChannelHandlerContext，代表了ChannelHandler 和ChannelPipeline之间的绑定。
其中，ChannelHandler对象被添加到ChannelPipeline对象的过程如下：
（1）⼀个ChannelInitializer对象的实现被注册到了ServerBootStrap对象上。
（2）当ChannelInitializer.initChannel⽅法被调⽤时，ChannelInitializer将在ChannelPipeline中安装⼀组⾃定义的ChannelHandler。
（3）ChannelInitializer将它⾃⼰从ChannelPipeline中移除。

```


 🍎 Google Protobuf
 🍎 Netty编解码器和handler的调用机制（important）
 🍎 TCP粘包与拆包及解决方案
 🍎 Netty核心源码剖析
   >> Netty启动过程源码剖析
   >> Netty接受请求过程源码剖析
   >> Netty的FastThreadLocal源码分析
   >> Netty在Dubbo中应用源码分析
   >> Netty心跳（heartbeat）服务源码剖析
   >> Netty核心组件EventLoop源码剖析
 🍎 


### 构建完整的Netty应⽤程序
```text
⼀个完整的Netty应⽤程序包含服务器端和客⼾端，客⼾端将信息发送给服务器端进⾏处理，同时服务器端再将信息经过处理后返回给客⼾端。


```

### Netty线程模型
```text
Netty线程模型是基于Reactor模型的多路复⽤⽅式来实现的，其内部实现了两个线程池：boss线程池和worker线程池。

```

#### 线程基础
```text
1、线程（Thread）
线程（Thread）是操作系统能够进⾏运算调度的最⼩单位。线程是伴随着操作系统的出现⽽产⽣的⼀个概念，是⼀个相对抽象的概念。
具体来说，线程是包含在进程之中的实际运作单位，线程实际控制着进程的单⼀执⾏顺序流程。

2、进程（Process）
进程（Process）是操作系统进⾏资源分配和调度的基本单位。进程同样是伴随操作系统的出现⽽产⽣的⼀个概念，基本可以理解为是在操作系统中切切实实运⾏着的程序。

程序是指令、数据及其组织形式的描述，⽽进程是程序的实体。

3、进程与线程的关系
进程（Process）是操作系统进⾏资源分配和调度的基本单位，⽽线程（Thread）是操作系统能够进⾏运算调度的最⼩单位。
操作系统能同时运⾏多个进程（程序），在同⼀个进程中可能有多个线程同时通过CPU的调度来执⾏。
如何理解呢？如果⼀个进程内有多个线程同时被执⾏，那么执⾏过程不是⼀条线的，⽽是多条线（线程）并⾏被执⾏的。

操作系统在运⾏时会为每个进程分配物理上的内存资源，但不会单独为线程分配资源，各个线程是通过共享同⼀进程的内存资源⽽存在的。
因此，操作系统在切换进程时会带来较⼤的系统开销（内存资源），但线程之间切换的开销却很⼩（共享同⼀进程的资源）

另外，虽然各个线程共享同⼀进程的内存资源，但每个线程都基本是相互独⽴的（独⽴拥有各⾃的运⾏栈、寄存器和计数器等）。

4、什么是线程池（Thread Pool）
线程池（Thread Pool）是⼀种多线程的管理模式，是实现并发框架的基础。
在单⼀进程中是可以存在多个线的，因此管理多个线程就是⼀项很重要的⼯作，这也正是线程池存在的原因。

线程池中的线程⼀般都是后台线程，每个线程都使⽤默认的堆栈⼤⼩，以默认的优先级运⾏，并且线程池会设定运⾏线程数量的最⼤值。
因为线程过多会带来资源开销的激增，影响系统的性能。

线程池中处理线程的过程⼀般称为任务调度，具体⽅式就是同步任务队列与完成队列。线程池保证任务队列中的新建任务进⼊线程池，同时把执⾏完的线程放⼊完成队列中去。

任务调度的具体内容，就是将新建任务添加到任务队列中，然后通过创建线程⾃动启动新建任务，然后将新建线程交由线程池进⾏统⼀管理（线程池则同时维护管理着多个线程）。
同时，还会监控当前线程池中是否有处于空闲状态的线程，如有，则移出该空闲线程并插⼊另⼀个线程来保证所有处理器任务合理且繁忙（不浪费资源）。
另外，还会保证线程池中的线程数量不超过最⼤值，如超过，则会让新建线程保持在任务队列中进⾏排队。

线程池既需要保证系统内核资源的充分利⽤，还要防⽌资源被过分调度。因此，线程池中活跃线程的数量⼀般取决于处理器内核、系统内存、⽹络Sockets和可⽤的并发处理器的数量。

使⽤线程池可以有效降低资源消耗，通过重复利⽤已创建的线程，降低创建线程和销毁线程造成的消耗。使⽤线程池可以提⾼响应速度，任务到达时不需要等到线程创建就可以⽴即执⾏。
使⽤线程池还可以有效管理线程，对线程进⾏统⼀的分配、调优和监控。

5、线程池模型
线程池模型就是指线程池的设计⽅式，主要分为HS/HA（半同步∕半异步）模式和L/F（领导者与跟随者）模式这两种。
HS/HA（半同步∕半异步）模式⼜称为⽣产者消费者模式，是⼀种⽐较简单、⽐较常⻅的实现⽅式，该模式分为同步层、队列层和异步层。
同步层的主线程处理⼯作任务并存⼊⼯作队列，⼯作线程从⼯作队列取出任务进⾏处理，如果⼯作队列为空，则取不到任务的⼯作线程进⼊挂起状态。
但由于线程间有数据通信，因此不适⽤于⼤数据量交换的场合。

L/F（领导者∕跟随者）模式线程池中的线程⼀般处在以下三种状态之⼀： 领导者（leader）、追随者（follower）或执⾏者（processor）。
⽆论在任何时刻，线程池中只有⼀个领导者（leader）线程。当事件到达时，领导者（leader）线程负责消息分离，并在追随者（follower）线程中选出⼀个来当继任领导者（leader），
然后将⾃⾝设置为执⾏者（processor）状态去处置该事件，执⾏者（processor）线程处理完毕后就将⾃⾝的状态置为追随者（follower）。L/F（领导者∕跟随者）模式线程池实现起来较为复杂，
但避免了线程间交换数据的任务。

6、Java线程
Java线程池完美地实现了线程复⽤功能，有效地降低了创建线程和销毁线程操作的资源消耗，提⾼了响应速度和系统性能。
在Java开发中，所有涉及异步执⾏的任务或并发执⾏的任务的程序都需要使⽤线程池。

Java线程池是基于Executor框架的两级调度模型实现的。Executor框架是⼀个灵活且强⼤的异步执⾏框架，⽀持多种不同类型的任务执⾏策略，
提供了⼀种标准的⽅法将任务的提交过程和执⾏过程解耦开发。
Executor框架基于“⽣产者-消费者”模式设计，其提交任务的线程相当于⽣产者，执⾏任务的线程相当于消费者，并⽤Runnable来表⽰任务。
Executor框架还实现了对⽣命周期的⽀持，以及统计信息收集、应⽤程序管理机制和性能监视等机制。

在Java线程启动时会同步创建⼀个本地操作系统线程，⼆者是⼀对⼀相互映射的关系。⽽当该Java线程终⽌时，同步创建的本地操作系统线程也会被回收。
Java多线程应⽤程序会把⾃⾝分解为若⼲个任务去执⾏，这些任务通过⽤⼾级的调度器（Executor框架）映射为固定数量的线程，底层的操作系统内核负责将这些线程映射到硬件处理器（CPU）上。
简单来说，就是Java多线程应⽤程序通过Executor框架实现上层调度，⽽映射到底层的调度则完全由操作系统内核所控制，上层调度是⽆法控制下层调度的。

应⽤程序通过Executor框架控制上层的调度，⽽下层的调度由操作系统内核控制，下层的调度不受应⽤程序的控制。这就是Java线程池基于Executor框架实现的原理。

7、Reactor模型
Reactor模型（反应器模型）是⼀种基于I/O多路复⽤策略、处理⼀个或多个客⼾端并发连接请求的事件设计模式。

7.1、I/O多路复⽤策略
所谓I/O多路复⽤，就是指使⽤⼀个线程来检查多个⽂件描述符Socket的就绪状态
例如，通过调⽤select和poll函数来传⼊多个⽂件描述符，如果有⼀个⽂件描述符的状态就绪则返回，否则就阻塞直到超时。
在得到就绪状态后进⾏真正操作时，可以在同⼀个线程⾥执⾏，也可以启动新线程执⾏（如使⽤线程池时）。

⼀般情况下，I/O多路复⽤策略是需要使⽤事件分发器的。对于事件分发器的作⽤，简单来说就是将那些读写事件源分发给各⾃读写事件的处理者。
涉及事件分发器的两种模式分别称为Reactor模型和Proactor模型。

Reactor模型是基于同步I/O的，⽽Proactor模型是与异步I/O相关的。
Netty线程模型就是通过Reactor模型、并基于I/O多路复⽤策略设计的。

7.2、Reactor模型和Proactor模型
⽆论是Reactor模型或是Proactor模型，⼆者都是⼀种基于事件驱动的、⾼性能的I/O设计模型。
⼆者的区别就是Reactor模型是基于同步I/O的，⽽Proactor模型是与异步I/O相关的。

1). Reactor模型——“反应堆”式的事件驱动调⽤机制
a.普通的函数调⽤机制
在普通的程序调⽤函数⽅式中，应⽤程序会根据处理流程主动调⽤并执⾏函数，然后程序会等待函数执⾏完成后的返回结果，函数会在执⾏完毕后将控制权回交给程序。
b.回调函数
Reactor模型则恰恰相反，其反置了整个处理流程。应⽤程序不是主动的调⽤某个函数完成处理，⽽是提供了调⽤函数所对应的接⼝并注册到Reactor上，
并将触发调⽤的操作定义为事件。当系统或⽤⼾操作触发事件时，Reactor将主动调⽤应⽤程序注册的对应接⼝并执⾏函数，因此这些接⼝也称为“回调函数”。
将Reactor模型描述为“反应堆”式的事件驱动调⽤机制是⾮常形象的。当客⼾端提交⼀个或多个并发服务请求时，服务器端的处理程序会使⽤I/O多路复⽤策略，
同步派发这些请求⾄相关的请求处理程序。

Reactor模型是处理并发I/O⽐较常⻅的⼀种模型，其核⼼思想就是将所有要处理的I/O事件注册到⼀个中⼼I/O多路复⽤器上，同时主线程阻塞在多路复⽤器上。
如果有I/O事件到来或是准备就绪，I/O多路复⽤器就会返回，并将相应I/O事件分发到对应的处理器中进⾏处理。

2). Proactor模型——“主动器”式的事件驱动调⽤机制
虽然Reactor模型相对简单⾼效，但在处理异步I/O请求时就会显得很不适应。因此，设计⼈员就提出来了⼀种“主动器”式的Proactor模型，专⻔⽤于异步I/O请求⽅式。
在Proactor模型下，应⽤程序初始化⼀个异步读写操作，然后注册相应的事件处理器。此时，事件处理器并不会关注读写就绪事件，⽽是只关注读写操作完成事件（这也正是区别于Reactor模型的关键）。
然后，在事件分发器等待读写操作完成事件的过程中，操作系统会通过调⽤内核线程完成读写操作，并将读写的内容放⼊应⽤缓存区中（异步IO请求均是由操作系统负责将数据读写到应⽤缓冲区中的）。

在事件分发器捕获到读写完成事件后，激活应⽤程序注册的事件处理器，并由事件处理器直接从缓存区中读写数据（此时就不是实际的读写操作了，实际的读写操作已经由系统内核完成了）。

3). Reactor模型与Proactor模型的异同
Reactor模型与Proactor模型的相同点是，它们都是基于I/O多路复⽤策略实现的。区别在于Reactor模型是基于同步I/O的，⽽Proactor模型是与异步I/O相关的。

Reactor模型实现了⼀个被动的事件分发模型，服务等待请求事件的到来，再通过不受间断的同步处理事件，进⽽完成操作处理。
Proactor模型实现了⼀个主动的事件分发模型，并⽀持多个任务并发的执⾏，对于耗时⻓的任务有特别优势（各个任务间互不影响）。

Reactor模型实现相⽐于Proactor模型实现要简单，Proactor模型逻辑复杂并依赖于操作系统对异步的⽀持。
对于Proactor模型⽀持⽐较好的有Windows系统实现的IOCP接⼝。相⽐于Windows系统，由于Unix/Linux系统对纯异步的⽀持有限，主要还是⽀持Reactor模型。

7.3、Reactor线程模型
在 Reactor模型中， 主要包括Handle、Synchronous EventDemultiplexer、Event Handler、Concrete Event Handler和Initiation Dispatcher这五⼤基本⻆⾊。

a.Handle（资源描述符）
Handle的概念⽐较宽泛，本质上是操作系统范畴中⼀种⽤于描述资源的标识。
例如：⽂件描述符、Socket描述符和事件描述符等。另外，在Windows操作系统中，Handle⼀般称为句柄，其实就是对资源描述符的另⼀种称谓。
在Reactor模型中，Handle主要⽤于表⽰事件发⽣的源头。

b.Synchronous EventDemultiplexer（同步事件分发器）
Synchronous Event Demultiplexer本质上是⼀个系统调⽤，主要⽤于等待⼀个或多个事件的发⽣。顾名思义，Synchronous Event Demultiplexer是同步的，被调⽤时会被阻塞，直到有事件产⽣为⽌。 
在Linux系统中，Synchronous Event Demultiplexer⼀般就是指I/O多路复⽤机制，⽐如select、poll和epoll等。 在Java NIO范畴中，Synchronous Event Demultiplexer就是指Selector选择器，
对应的就是select()⽅法（阻塞⽅法）。

c.Event Handler（事件处理器）
Event Handler是由多个回调⽅法构成的，这些回调⽅法构成了针对某个事件的反馈机制。
在Java NIO范畴中，Event Handler是由开发⼈员⾃⾏编写代码完成调⽤或回调的。⽽Netty架构中的Event Handler针对
Java NIO进⾏了升级，为开发⼈员提供了⼤量的回调⽅法，针对特定的事件定义了相应的业务逻辑处理的回调⽅法。
例如：在ChannelHandler中对应的都是⼀个个特定事件的回调⽅法。

d.Concrete Event Handler（具体事件处理器）
Concrete Event Handler是Event Handler（事件处理器）的具体实现，它实现了Event Handler（事件处理器）所提供的各种回调⽅法，进⽽实现了特定的业务逻辑。

e.Initiation Dispatcher（初始化分发器）
Initiation Dispatcher定义了⼀组规范，⽤于控制事件的调度⽅式，同时还提供了针对Event Handler（事件处理器）的注册、删除等机制。
Initiation Dispatcher是整个Event Handler的核⼼，其通过Synchronous Event Demultiplexer来等待事件的发⽣、事件到来时负责分离出每⼀个事件，
进⽽调⽤Event Handler中的特定回调⽅法来处理这些事件。在Reactor模型中，Initiation Dispatcher实际上就扮演着Reactor的⻆⾊。

以上这些角色是如何协作工作的？
⾸先，Initiation Dispatcher（初始化分发器）启动时，会把所有相关的Event Handler（事件处理器）对应的具体实现Concrete Event Handler（具体事件处理器）注册到Initiation Dispatcher中。
然后，Initiation Dispatcher（初始化分发器）通过Synchronous Event Demultiplexer（同步事件分离器）等待事件的发⽣，事件到来时再根据事件的类型调⽤Event Handler（事件处理器）的回调。
Event Handler（事件处理器）拥有Handle（事件描述符）的引⽤，Initiation Dispatcher在事件注册完成后会执⾏⾃⼰的内部循环（调⽤Synchronous Event Demultiplexer的select()⽅法）。
最后，当客⼾端的连接请求到来时，select()⽅法会返回事件的集合，由Initiation Dispatcher遍历集合负责获取每⼀个具体的事件，再根据事件类型调⽤Event Handler的回调⽅法进⾏具体操作。

基于Reactor模型的主要有Reactor单线程模型、Reactor多线程模型和主从Reactor多线程模型这三种，这也正是Netty设计者构建Netty线程模型的核⼼基础。


7.4、Reactor单线程模型
Reactor单线程模型，指的是所有的I/O操作都在同⼀个NIO线程上⾯完成，NIO线程的职责如下：
· 作为NIO服务端，接收客⼾端的TCP连接。
· 作为NIO客⼾端，向服务端发起TCP连接。
· 读取通信对端的请求或者应答消息。
· 向通信对端发送消息请求或者应答消息。

Reactor是服务器端的⼀个线程对象，负责启动事件循环，并使⽤select()函数（阻塞⽅式）实现I/O多路复⽤。Acceptor事件处理器注册到Reactor对象中，
并负责监听由客⼾端向服务器端发起的连接请求ACCEPT事件。Acceptor事件处理器会通过accept()⽅法得到客⼾端连接，并将具体的READ事件以及对应的READ事件注册到Reactor对象中。
当Reactor监听到有READ事件发⽣时，会将相关的事件派发给对应的Handle处理器进⾏处理。同理，对于WRITE事件也是⼤致如此。最后，当Reactor对象处理完线程中的I/O事件后，
会再次执⾏select()函数等待新的事件就绪并进⾏处理。

注意：所谓Reactor单线程模型中的“单线程”主要是针对I/O操作⽽⾔，也就是所有的I/O操作都在⼀个线程上完成的。

Reactor单线程模型主要适⽤于低负载、低并发、⼩数据量的应⽤场景，⽽对于⾼负载、⾼并发、⼤数据量的应⽤场景是明显⼒不从⼼的。

7.5、Reactor多线程模型
Reactor多线程模型是指在Reactor单线程模型的基础上，增加了⼀个⼯作者（worker）线程池。该⼯作者（worker）线程池负责处理从Reactor线程中移交出来的⾮I/O操作，
最⼤程度地提⾼Reactor线程的I/O响应处理能⼒，尽量避免由于⼀些耗时的业务操作占⽤资源，进⽽延迟后续I/O请求操作的处理。
Reactor多线程模型相⽐于Reactor单线程模型，在业务逻辑上主要做出了以下⼏个⽅⾯的改进：
a.将Handler处理器的执⾏放⼊线程池，由多线程进⾏业务处理。
b.Reactor线程对象可以仍为单线程，⽽如果服务器为双核CPU，为了充分利⽤系统资源，可将Reactor对象拆分为两个线程。

Reactor多线程模型与Reactor单线程模型不同之处在于增加了⼀个⼯作者线程池（Thread Pool）。

在Reactor多线程模型中，⼯作者线程池（Thread Pool）将⾮I/O操作从Reactor线程对象中移出，并转交给⼯作者线程（Worker Thread）来处理执⾏。
这种⽅式能够提⾼Reactor线程对象的I/O响应处理能⼒，尽量避免由于⼀些耗时的业务操作占⽤资源，进⽽延迟后续I/O请求操作的处理。

在Reactor多线程模型中增加⼯作者线程池（Thread Pool）的设计，可以通过重复使⽤已有的线程（不⽤额外创建新的线程）避免在处理多个请求时，
由于创建新线程及销毁旧线程过程中产⽣的资源开销。另外，由于⼯作者线程（Worker Thread）通常都是处于⼯作状态，当客⼾端请求到达时可以⽴即接受任务，
避免了由于等待创建新线程⽽延迟任务的执⾏，也就是提⾼了线程的响应性。

Reactor多线程模型通过适当调整⼯作者线程池的⼤⼩，可以创建合适的线程数量以满⾜处理器时刻保持忙碌状态。
同时，还可以防⽌由于过多的线程相互竞争系统资源，从⽽使应⽤程序耗尽内存或失败。

7.6、主从Reactor多线程模型
主从Reactor多线程模型是指在Reactor多线程模型的基础上，将Reactor分成mainReactor和subReactor两部分。
其中，mainReactor负责监听服务器端的套接字并接收新连接，然后将建⽴的套接字分派给subReactor。
⽽subReactor则负责解析已连接的套接字，然后将具体的业务操作转发给⼯作者线程池来完成。实际上，subReactor的数量可与服务器的CPU数量等同。
主从Reactor多线程模型相⽐于Reactor多线程模型，在业务逻辑上主要做出了以下⼏⽅⾯的改进：
a.服务器端⽤于接收客⼾端连接的不再是⼀个单独的线程，⽽是⼀个独⽴的线程池。
b.Acceptor接收到客⼾端的连接请求并处理完成后，将创建新的线程并注册到subReactor线程池中的某个I/O线程上，由其负责具体的业务⼯作。

在主从Reactor多线程模型的线程池中，每⼀个Reactor线程对象都会有⾃⼰的selector()函数、线程及事件分发的循环逻辑。
主从Reactor多线程模型中的mainReactor可以只有⼀个，但subReactor⼀般会有多个（通常会对应于CPU数量）。 
其中，mainReactor主要负责接收客⼾端的连接请求，然后将接收到的套接字转发给subReactor，最终由subReactor来完成与客⼾端的实际业务操作。

关于主从Reactor多线程模型的业务逻辑主要如下：
（1）在服务器端接收到客⼾端的连接请求后，会注册⼀个Acceptor事件处理器到mainReactor中。
需要注意，Acceptor事件处理器仅仅负责处理ACCEPT事件，⽽mainReactor负责启动事件循环来监听客⼾端向服务器端发起的ACCEPT连接请求事件。
Acceptor处理器负责处理与客⼾端对应的连接套接字，然后将这个连接套接字传递给subReactor。
（2）subReactor线程池（Thread Pool）会分配⼀个subReactor线程给这个连接套接字，也就是将对应的事件处理器注册到subReactor线程中。
另外，对于subReactor线程池（Thread Pool）中的每个Reactor线程，都会有⾃⼰的循环逻辑。
（3）当服务器端有I/O事件就绪时，相关的subReactor线程就会将事件转发给对应的处理器进⾏处理。
请注意，此时subReactor线程只负责完成I/O读操作，在读取到数据后会将业务逻辑的处理放⼊到线程池中去完成。
如果完成业务逻辑后需要返回数据给客⼾端，则相关I/O写操作还是会被返回给subReactor线程来完成。

对于主从Reactor多线程模型⽽⾔，所有I/O操作是由Reactor线程负责处理的，线程池（Thread Pool）仅仅⽤来处理⾮I/O操作。
其中，mainReactor负责完成接收客⼾端连接请求的操作（不负责与客⼾端的通信业务），然后将建⽴好的连接套接字转发给subReactor，
由subReactor负责完成与客⼾端的通信业务。这样设计的好处就是，对于⾼负载、⾼并发、⼤数据量的应⽤场景，mainReactor专⻔负责客⼾端的连接请求，
具体I/O操作会分发给多个subReactor来进⾏处理，对于⽀持多核CPU服务器端可以最⼤程度地提升负载性能。

8、Netty线程模型
Netty框架能够很好地⽀持⾼并发特性的⼀个重要原因，就是基于其⾼性能的线性模型。具体来说，Netty框架的线性模型是通过Reactor模型，并基于I/O多路复⽤器接收和处理⽤⼾请求的设计。

8.1、Netty线程模型与Reactor模型的关系
Netty线程模型就是基于I/O多路复⽤策略⽽实现的⼀个Reactor线程模型的经典通信架构。
（1）Netty服务器端在启动时会配置⼀个ChannelPipeline，在ChannelPipeline中包含⼀个ChannelHandler链。
所有的I/O事件发⽣时都会触发Channelhandler中的事件⽅法，这个事件会在ChannelPipeline中的ChannelHandler链⾥进⾏传递。
此时，Netty的事件处理器（EventHandle）就相当于ChannelHandler。
（2）Netty 服 务 器 端 从 bossGroup 事 件 循 环 池（NioEventLoopGroup）中获取⼀个NioEventLoop来实现服务器端程序绑定本地端⼝的操作，
将对应的ServerSocketChannel注册到该NioEventLoop中的Selector上，并注册ACCEPT事件为ServerSocketChannel所感兴趣的事件。
此时，在Netty的bossGroup事件循环池（NioEventLoopGroup）中获取的NioEventLoop就相当于mainReactor，
NioEventLoop中的Selector就相当于同步事件分离器（Synchronous Event Demultiplexer）。
（3）NioEventLoop启动事件循环来监听客⼾端的连接请求。每当有客⼾端向服务器端发起连接请求时，NioEventLoop的事件循环监听到该ACCEPT事件，
Netty负责接收这个连接并通过accept()⽅法得到与这 个 客 ⼾ 端 的 连 接 （ SocketChannel ） 。 
此时，Netty会并触发ChannelHandler中的ChannelRead事件，该事件会在ChannelPipeline中的ChannelHandler链中执⾏并传递。
ServerBootstrapAcceptor中的readChannel()⽅法负责将该客⼾端的连接（SocketChannel）注册到workerGroup（NioEventLoopGroup）中某个NioEventLoop的Selector上，
并注册READ事件为客⼾端的连接（SocketChannel）所感兴趣的事件，接下来就可以在客⼾端与服务器端之间进⾏通信了。

8.2、Netty单线程模型应⽤
在Netty单线程模型应⽤中，通过在启动辅助类中创建单线程对应的EventLoopGroup实例，并进⾏与单线程相应的参数配置，就可以实现基于Reactor单线程模型的Netty应⽤。



```

 
### 第一章：初识Netty背景、现状、与趋势
 ```text
Netty是2004年开发的
本质：网络应用程序框架
实现：异步、事件驱动
特性：高性能、可维护、快速开发
用途：开发服务器和客户端

为什么舍近求远？而不直接使用JDK NIO呢？
Netty做的更多：
·支持常用应用层协议
·解决传输问题：粘包，半包现象
·支持流量整形
·完善的断连、Idle等异常处理
Netty做的更好之一：规避JDK NIO bug
Netty做的更好之二：API更友好更强大
·JDK的NIO一些API不够友好，功能薄弱，例如ByteBuffer -> Netty`s ByteBuf
·除了NIO外，也提供了其他一些增强：ThreadLocal -> Netty`s FastThreadLocal
Netty做的更好之三：隔离变化、屏蔽细节
·隔离JDK NIO的实现变化： NIO -> NIO2(aio) ->...
·屏蔽JDK NIO的实现细节

其他的网络编程应用框架：
·apache Mina
·Sun Grizzly
·Apple swift NIO、ACE
·Cindy
·tomcat jetty

Netty的前尘往事
Netty归属于JBoss

Netty的现状与趋势
https://github.com/netty/netty
依赖项中声明io.netty:netty-all
使用Netty的典典型项目：
数据库：Cassandra
大数据处理：Spark、Hadoop
Message Queue: RocketMQ
检索：Elasticsearch
框架：gRPC、Apache Dubbo、Spring5
分布式协调器: Zookeeper
工具类：async-http-client
其他参考：https://netty.io/wiki/adopters.html
Netty的趋势：
更多流行协议的支持
紧跟JDK新功能步伐
更多易用、人性化的功能
IP地址黑白名单、流量整形
应用越来越多
```
### 第二章：Netty源码从"点"（领域知识）的角度剖析
```text
Netty怎么切换I/O模式
什么是经典的三种I/O模式？
BIO（阻塞I/O） JDK1.4-
NIO（非阻塞I/O）JDK1.4+ java.nio包
AIO（异步I／O）JDK1.7+

阻塞与非阻塞：阻塞：在没有数据传递过来时，读会阻塞直到有数据；缓冲区满时，写操作也会阻塞。
             非阻塞遇到这些情况，都是直接返回。
同步与异步：数据就绪后需要自己去读是同步，数据就绪直接读好再回调给程序是异步。

Netty对三种I/O模式的支持
为什么Netty仅支持NIO了？
为什么Netty有多种NIO实现？
NIO一定优于BIO么？
BIO代码简单
特定场景：连接数少，并发度低，BIO性能不输NIO
源码解读Netty怎么切换I/O模式？

```
### 第三章：Netty源码从"线"（请求处理）的角度剖析
```text

```
### 第四章：Netty实战
```text

```
### 第五章：Netty实战进阶
```text


```
### 第六章：赏析高级应用是如何使用Netty的
```text

```

# 《Netty实战》
```text
Netty实战相关编码放在 actual_combat 包下


```

## Protobuf
protobuf工具下载地址：
https://github.com/protocolbuffers/protobuf/releases/tag/v3.17.3
找到符合自己的系统的安装包下载解压即可，如想直接使用protoc.exe工具， 最好配置环境变量

通过*.proto文件生成对应的*.java文件：
方式1：在指定的文件夹E:\TestData\下执行该命令，需要把UserInfo.proto文件放在TestData文件夹下
protoc.exe --java_out=E:\TestData\ ./UserInfo.proto
在项目UserInfo.proto文件所在文件夹下执行如下命令：
protoc.exe --java_out=./ ./com.allst.netty.protobuf.proto.UserInfo.proto


