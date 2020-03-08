package com.allst.multi.threadpoolcase.ms;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author YiYa
 * @since 2020-03-08 下午 09:13
 */
public class MsTast {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(15));

        for (int i = 0; i <= 20; i++) {
            MsTask task = new MsTask("客户-" + (i + 1));
            pool.submit(task);
        }
        pool.shutdown();
    }

}
