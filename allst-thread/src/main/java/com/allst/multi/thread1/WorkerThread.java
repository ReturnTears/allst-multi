package com.allst.multi.thread1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 工作者线程的代码示例
 *
 * @author Hutu
 * @since 2023-02-14 下午 10:33
 */
public class WorkerThread {
    public static void main(String[] args) {
        Helper helper = new Helper();
        helper.init();
        // 此处helper的客户端线程为main线程
        helper.submit("Something...");
    }

    static class Helper {
        private final BlockingQueue<String> workQueue = new ArrayBlockingQueue<>(100);
        // 用于处理队列workQueue中的任务的工作者线程
        private final Thread workerThread = new Thread() {
            @Override
            public void run() {
                String task = null;
                while (true) {
                    try {
                        task = workQueue.take();
                    } catch (InterruptedException e) {
                        break;
                    }
                    System.out.println(doProcess(task));
                }
            }
        };

        public void init() {
            workerThread.start();
        }

        protected String doProcess(String task) {
            return task + " -> processed.";
        }
        public void submit(String task) {
            try {
                workQueue.put(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
