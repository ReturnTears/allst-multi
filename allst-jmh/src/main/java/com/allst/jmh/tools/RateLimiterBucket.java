package com.allst.jmh.tools;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

/**
 * 漏桶算法
 *
 * @author Hutu
 * @since 2024-04-27 下午 09:56
 */
public class RateLimiterBucket {

    // 漏桶采用线程安全的容器
    private final ConcurrentLinkedQueue<Request> bucket = new ConcurrentLinkedQueue<>();

    // 定义漏桶的上沿容量
    private final static int BUCKET_CAPACITY = 1000;

    // 定义漏桶的下沿水流速率，每秒匀速放行10个request
    private final RateLimiter rateLimiter = RateLimiter.create(10.0D);

    // 提交请求时需要用到的Monitor
    private final Monitor requestMonitor = new Monitor();

    // 处理请求时需要用到的Monitor
    private final Monitor handleMonitor = new Monitor();

    public void submitRequest(int data) {
        this.submitRequest(new Request(data));
    }

    // 该方法主要用于接受来自客户端提交的请求数据
    public void submitRequest(Request request) {
        // 注释① 当漏桶容量未溢出时
        if (requestMonitor.enterIf(requestMonitor.newGuard(() -> bucket.size() < BUCKET_CAPACITY))) {
            try {
                // 在漏桶中加入新的request
                boolean result = bucket.offer(request);
                if (result) {
                    System.out.println(currentThread() + " submit request: " + request.getData() + " successfully.");
                } else {
                    // produce into MQ and will try again later.
                }
            } finally {
                requestMonitor.leave();
            }
        } else {
            // 注释② 当漏桶溢出的时候做降权处理
            System.out.println("The request:" + request.getData() + " will be down-dimensional handle due to bucket is overflow.");
            // produce into MQ and will try again later.
        }
    }

    // 该方法主要从漏桶中匀速地处理相关请求
    public void handleRequest(Consumer<Request> consumer) {
        // 若漏桶中存在请求，则处理
        if (handleMonitor.enterIf(handleMonitor.newGuard(() -> !bucket.isEmpty()))) {
            try {
                // 注释③， 匀速处理
                rateLimiter.acquire();
                // 处理数据
                consumer.accept(bucket.poll());
            } finally {
                handleMonitor.leave();
            }
        }
    }

    static class Request {
        private final int data;

        public Request(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public String toString() {
            return "Request{" + "data=" + data + '}';
        }
    }
}
