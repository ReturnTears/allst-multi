package com.allst.concurrent.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *  测试类3 - 创建线程方式3
 * @author YiYa
 * @since 2020-01-08 下午 10:58
 */
@Slf4j(topic = "c.Test3")
public class Test3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable() {
            @Override
            public Object call() throws Exception {
                log.debug("running....");
                Thread.sleep(1000);
                return 100;
            }
        });

        Thread t = new Thread(task, "t3");
        t.start();

        log.debug("{}", task.get());
    }

}
