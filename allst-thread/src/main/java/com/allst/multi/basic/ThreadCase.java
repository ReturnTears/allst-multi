package com.allst.multi.basic;

/**
 * 线程的输出?
 * @author YiYa
 * @since 2019-12-15 下午 11:48
 */
public class ThreadCase implements Runnable {
    private int count = 10;
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
        System.out.println("Current Thread id is : " +Thread.currentThread().getId());
        System.out.println("Current Thread priority is : " +Thread.currentThread().getPriority());
    }

    /**
     * 运行结果可能会各不相同, 原因是线程的执行过程中执行时长不一致, 加锁可以保证每个线程都是在上一线程执行完成并释放锁后获取的
     * @param args  参数
     */
    public static void main(String[] args) {
        ThreadCase t = new ThreadCase();
        for (int i = 0; i < 5; i++) {
            new Thread(t, "Thread" + i).start();
        }
        System.out.println("count : " + t.count);
    }
}
