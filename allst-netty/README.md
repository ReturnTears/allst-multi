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


