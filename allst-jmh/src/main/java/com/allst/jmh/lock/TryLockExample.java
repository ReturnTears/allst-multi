package com.allst.jmh.lock;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * 验证在同一时刻是否只有一个线程才能成功获得TryLock显式锁
 *
 * @author Hutu
 * @since 2024-04-18 下午 10:59
 */
public class TryLockExample {
    private final static Object VAL_OBJ = new Object();

    public static void main(String[] args) {
        final TryLock lock = new TryLock();
        final List<Object> validation = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        // 尝试获取该锁，该方法并不会导致当前线程进入阻塞
                        if (lock.tryLock()) {
                            System.out.println(currentThread() + ": get the lock.");
                            // 进行校验，以确保validation中只存在一个元素
                            if (validation.size() > 1) {
                                throw new IllegalStateException("validation failed.");
                            }
                            validation.add(VAL_OBJ);
                            TimeUnit.MILLISECONDS.sleep(current().nextInt(10));
                        } else {
                            // 未获得锁，简单做个休眠，以防止出现CPU过高电脑死机的情况发生
                            TimeUnit.MILLISECONDS.sleep(current().nextInt(10));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 在finally语句块中进行锁的释放操作
                        if (lock.release()) {
                            System.out.println(currentThread() + ": release the lock.");
                            validation.remove(VAL_OBJ);
                        }
                    }
                }
            }).start();
        }
    }
}
