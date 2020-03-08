package com.allst.multi.threadpool2;

import java.util.List;
import java.util.concurrent.*;

/**
 * Executors获取ExecutorService
 *
 * @author YiYa
 * @since 2020-03-08 下午 03:21
 */
public class MyPoolExecutors {

    public static void main(String[] args) {
        meth9();
    }

    private static void meth1() {
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            es.submit(new MtRunnable(i + 1));
        }
    }

    private static void meth2() {
        ExecutorService es = Executors.newCachedThreadPool(new ThreadFactory() {
            int n = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "自定义Cached线程" + n++);
            }
        });
        for (int i = 0; i < 10; i++) {
            es.submit(new MtRunnable(i + 1));
        }
    }

    private static void meth3() {
        ExecutorService es = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            es.submit(new MtRunnable(i + 1));
        }
    }

    private static void meth4() {
        ExecutorService es = Executors.newFixedThreadPool(3, new ThreadFactory() {
            int n = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "自定义FixedT线程" + n++);
            }
        });
        for (int i = 0; i < 10; i++) {
            es.submit(new MtRunnable(i + 1));
        }
    }

    private static void meth5() {
        // 使用线程工厂获取线程池对象
        ExecutorService es = Executors.newSingleThreadExecutor();
        // 提交任务
        for (int i = 0; i < 10; i++) {
            es.submit(new MtRunnable(i + 1));
        }
        // 关闭线程池,仅仅时不能接受新的任务,以前的任务还是会继续执行
        es.shutdown();
        // 关闭线程池后，不能再提交新的任务了
        // es.submit(new MtRunnable(22));
    }

    private static void meth6() {
        ExecutorService es = Executors.newSingleThreadExecutor(new ThreadFactory() {
            int n = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "自定义SingleT线程" + n++);
            }
        });
        // 提交任务
        for (int i = 0; i < 10; i++) {
            es.submit(new MtRunnable(i + 1));
        }
        // 关闭线程池， 如果线程池中有缓存的任务没有执行， 则取消执行并返回
        List<Runnable> list = es.shutdownNow();
        System.out.println(list);
    }


    private static void meth7() {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);
        for (int i = 0; i < 10; i++) {
            ses.schedule(new MtRunnable(i + 1), 2, TimeUnit.SECONDS);
        }
        System.out.println("it`s over~~~");
    }

    private static void meth8() {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(3, new ThreadFactory() {
            int i = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "自定义线程Scheduled" + i++);
            }
        });

        ses.scheduleAtFixedRate(new MtRunnable( 1), 1, 2, TimeUnit.SECONDS);

        System.out.println("it`s over~~~");
    }

    private static void meth9() {
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(3, new ThreadFactory() {
            int i = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "自定义线程Scheduled" + i++);
            }
        });

        ses.scheduleWithFixedDelay(new MtRunnable( 1), 1, 2, TimeUnit.SECONDS);

        System.out.println("it`s over~~~");
    }
}

class MtRunnable implements Runnable {

    private int id;

    public MtRunnable(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MtRunnable{" +
                "id=" + id +
                '}';
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.printf("%s 执行了任务~~~%d\n", name, id);
    }
}