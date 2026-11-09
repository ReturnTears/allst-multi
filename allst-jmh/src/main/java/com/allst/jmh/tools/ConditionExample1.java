package com.allst.jmh.tools;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Condition是Java 1.5中引入的一个用于替代传统的对象监视器方法（如wait()、notify()和notifyAll()）的API。
 * 它提供了一种更加灵活的线程间协作方式。Condition需要与Lock配合使用，通常与ReentrantLock结合使用，以实现更高效的线程同步。
 * <p>
 * 在使用Condition之前，需要先创建一个Lock对象，然后通过这个Lock对象调用newCondition()方法来获取一个Condition对象。
 * Condition的核心方法包括await()、signal()和signalAll()，它们分别对应于Object监视器方法的wait()、notify()和notifyAll()。
 * await()方法可以响应中断，而awaitUninterruptibly()方法则不会。Condition还提供了await(long time, TimeUnit unit)方法，
 * 允许线程在指定的时间内等待，如果在这段时间内没有被唤醒，线程可以自动醒来继续执行。
 * 使用Condition时，必须确保await()和signal()方法的调用是在锁的保护范围内，即在lock.lock()和lock.unlock()之间。
 * 此外，从await()返回的线程需要重新获取锁才能继续执行。
 * @author Hutu
 * @since 2024-04-26 下午 09:44
 */
public class ConditionExample1 {

    // 定义共享数据
    private static int shareData = 0;
    // 定义布尔变量标识当前的共享数据是否已经被使用
    private static boolean isUsed = false;
    // 创建显示锁
    private static final Lock lock = new ReentrantLock();
    // 使用显示锁创建Condition对象对象并且与之关联
    private static final Condition condition = lock.newCondition();
    // 对数据的写操作
    private static void write() {
        // 获取锁， 如果当前锁被其他线程持有，则当前线程会进入阻塞
        lock.lock();
        try {
            // 如果共享数据未被使用，则当前线程将进入wait队列，并且释放lock
            while (!isUsed) {
                condition.await();
            }
            TimeUnit.SECONDS.sleep(current().nextInt(5));
            // 模拟数据写入,并将isUsed状态标识为false
            shareData = (int) (Math.random() * 100);
            isUsed = false;
            System.out.println("write data is : " + shareData);
            // 通知并唤醒在wait队列中的其他线程：数据读取线程
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    // 对数据的读操作
    private static void read() {
        // 获取锁,如果当前锁被其他线程持有，则当前线程会进入阻塞
        lock.lock();
        try {
            while (isUsed) {
                condition.await();
            }
            // 使用数据，并且将dataUsed状态标识置为true
            TimeUnit.SECONDS.sleep(current().nextInt(5));
            isUsed = true;
            System.out.println("read data is : " + shareData);
            // 通知并唤醒wait队列中的其他线程：数据写入线程
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        AtomicInteger proCount = new AtomicInteger(10);
        AtomicInteger conCount = new AtomicInteger(10);

        // 创建并启动两个匿名线程
        new Thread(() -> {
            while (true) {
                write();
                int i = proCount.decrementAndGet();
                if (i == 0) {
                    break;
                }
            }
        }, "Producer").start();
        new Thread(() -> {
            while (true) {
                read();
                int i = conCount.decrementAndGet();
                if (i == 0) {
                    break;
                }
            }
        }, "Consumer").start();
    }
}
