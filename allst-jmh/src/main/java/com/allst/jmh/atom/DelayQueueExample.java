package com.allst.jmh.atom;

import java.util.concurrent.DelayQueue;

/**
 * @author Hutu
 * @since 2024-05-24 下午 10:17
 */
public class DelayQueueExample {
    public static void main(String[] args) throws InterruptedException {
        // 定义DelayQueue，无需指定容量，因为DelayQueue是一个"无边界"的阻塞队列
        DelayQueue<DelayedEntity> delayQueue = new DelayQueue<>();
        // 存入数据A，数据A将在10000毫秒后过期，或者说会被延期10000毫秒后处理
        delayQueue.put(new DelayedEntity("A", 10 * 1000L));
        // 存入数据B，数据B将在5000毫秒后过期，或者说会被延期5000毫秒后处理
        delayQueue.put(new DelayedEntity("B", 5 * 1000L));
        // 记录时间戳
        final long timestamp = System.currentTimeMillis();
        // 非阻塞读方法，立即返回null，原因是当前AB元素不会有一个到达过期时间
        assert delayQueue.poll() == null;
        // take方法会阻塞5000毫秒左右，因为此刻队列中最快达到过期条件的数据B只能在5000毫秒以后
        DelayedEntity value = delayQueue.take();
        // 断言队列头部的元素为B
        assert value.getValue().equals("B");
        // 耗时5000毫秒或以上
        assert (System.currentTimeMillis() - timestamp) >= 5_000L;

        // 再次执行take操作
        value = delayQueue.take();
        // 断言队列头部的元素为A
        assert value.getValue().equals("A");
        // 耗时在10000毫秒或以上
        assert (System.currentTimeMillis() - timestamp) >= 10_000L;
    }
}
