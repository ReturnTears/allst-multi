package com.allst.multi.threadpoolcase.qk;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author YiYa
 * @since 2020-03-08 下午 09:34
 */
public class QkTast {

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool(new ThreadFactory() {
            int flag = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "ATM-" + flag++);
            }
        });
        for (int i = 0; i < 2; i++) {
            es.submit(new QkTask("" + (i + 1), 700));
        }
        es.shutdown();
    }

}
