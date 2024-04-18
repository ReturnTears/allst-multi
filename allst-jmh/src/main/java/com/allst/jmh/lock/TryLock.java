package com.allst.jmh.lock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 借助于AtomicBoolean实现一个可立即返回并且退出阻塞的显式锁Lock
 *
 * @author Hutu
 * @since 2024-04-18 下午 10:51
 */
public class TryLock {
    private final AtomicBoolean ab = new AtomicBoolean(false);
    private final ThreadLocal<Boolean> threadLocal = ThreadLocal.withInitial(() -> false);

    /**
     * 可立即返回的lock方法
     */
    public boolean tryLock() {
        boolean result = ab.compareAndSet(false, true);
        if (result) {
            threadLocal.set(true);
        }
        return result;
    }

    /**
     * 锁的释放
     */
    public boolean release() {
        // 判断调用release方法的线程是否成功获得了该锁
        if (threadLocal.get()) {
            threadLocal.set(false);
            return ab.compareAndSet(true, false);
        } else {
            return false;
        }
    }
}
