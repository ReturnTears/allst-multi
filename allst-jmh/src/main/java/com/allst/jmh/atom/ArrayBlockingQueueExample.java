package com.allst.jmh.atom;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Hutu
 * @since 2024-05-21 下午 10:52
 */
public class ArrayBlockingQueueExample {
    public static void main(String[] args) {

    }

    /**
     * 阻塞式写入1
     */
    private void queuePut() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        try {
            queue.put("1");
            queue.put("2");
            // 执行put("3")时，队列已满，会阻塞
            queue.put("3");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(queue);
    }

    /**
     * 阻塞式写入2
     */
    private void queueOfferTimeOut() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        try {
            queue.offer("4", 10, TimeUnit.SECONDS);
            queue.offer("5", 10, TimeUnit.SECONDS);
            // 该方法会进入阻塞， 10秒之后当前线程将会退出阻塞，并且对“6”的写入将会失败
            queue.put("6");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(queue);
    }

    /**
     * 非阻塞式写入1
     */
    private void queueAdd() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.add("7");
        queue.add("8");
        try {
            // 写入失败，抛出异常
            queue.add("9");
        } catch (Exception e) {
            assert e instanceof IllegalStateException;
        }
        System.out.println(queue);
    }

    /**
     * 非阻塞式写入2
     */
    private void queueOffer() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.offer("7");
        queue.offer("8");
        // 写入失败
        queue.offer("9");
        assert queue.size() == 2;
        System.out.println(queue);
    }

    /**
     * 阻塞式读取1
     */
    private void queueTake() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.offer("7");
        queue.offer("8");
        try {
            assert queue.take().equals("7");
            assert queue.take().equals("8");
            // 进入阻塞
            queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 阻塞式读取2
     */
    private void queuePollTimeOut() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.offer("7");
        queue.offer("8");
        try {
            assert queue.poll(10, TimeUnit.SECONDS).equals("7");
            assert queue.poll(10, TimeUnit.SECONDS).equals("8");
            // 10秒后线程退出阻塞， 并且返回null值
            assert queue.poll(10, TimeUnit.SECONDS) == null;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 非阻塞式读取1
     */
    private void queuePoll() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.offer("7");
        queue.offer("8");
        assert Objects.equals(queue.poll(), "7");
        assert Objects.equals(queue.poll(), "8");
        // 队列为空， 立即返回但是结果为null
        assert queue.poll() == null;
    }

    /**
     * 非阻塞式读取2
     */
    private void queuePeek() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        queue.offer("7");
        queue.offer("8");
        assert Objects.equals(queue.peek(), "7");
        assert Objects.equals(queue.peek(), "8");
        queue.clear();
        // peek返回结果为null
        assert queue.peek() == null;
    }
}
