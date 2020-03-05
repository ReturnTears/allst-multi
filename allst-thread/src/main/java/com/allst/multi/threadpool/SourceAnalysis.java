package com.allst.multi.threadpool;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.*;

/**
 * ThreadPoolExecutor源码剖析
 *
 * @author YiYa
 * @since 2020-03-06 上午 12:26
 */
public class SourceAnalysis {

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new BlockingQueue<Runnable>() {
            @Override
            public boolean add(Runnable runnable) {
                return false;
            }

            @Override
            public boolean offer(Runnable runnable) {
                return false;
            }

            @Override
            public void put(Runnable runnable) throws InterruptedException {

            }

            @Override
            public boolean offer(Runnable runnable, long timeout, TimeUnit unit) throws InterruptedException {
                return false;
            }

            @Override
            public Runnable take() throws InterruptedException {
                return null;
            }

            @Override
            public Runnable poll(long timeout, TimeUnit unit) throws InterruptedException {
                return null;
            }

            @Override
            public int remainingCapacity() {
                return 0;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public int drainTo(Collection<? super Runnable> c) {
                return 0;
            }

            @Override
            public int drainTo(Collection<? super Runnable> c, int maxElements) {
                return 0;
            }

            @Override
            public Runnable remove() {
                return null;
            }

            @Override
            public Runnable poll() {
                return null;
            }

            @Override
            public Runnable element() {
                return null;
            }

            @Override
            public Runnable peek() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public Iterator<Runnable> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Runnable> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        };

        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return null;
            }
        };

        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,         // 核心线程数量: 初始化是会创建的线程数， 达到最这个线程数之后, 允许继续创建线程数，由最大线程数控制
                7,        // 最大线程数: > 核心线程数
                1000,       // 最大空闲时间: 当没有任务来执行线程时，线程处于空闲状态下能够保留多长时间, 空闲一定时间后系统会回收线程
                TimeUnit.SECONDS,        // 时间单位
                queue,                   // 任务队列
                threadFactory,           // 线程工厂
                handler);                // 饱和处理机制
    }

}
