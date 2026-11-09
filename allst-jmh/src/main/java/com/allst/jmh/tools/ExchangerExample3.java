package com.allst.jmh.tools;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExchangerExample3 {
    public static void main(String[] args) throws InterruptedException, TimeoutException {
        // 创建用于交换 String 数据的 Exchanger
        final Exchanger<String> exchanger = new Exchanger<>();

        // 启动子线程
        Thread childThread = getChildThread(exchanger);

        // 主线程准备数据并等待交换
        String mainData = "Hello from main thread";
        System.out.println("Main: preparing to send [" + mainData + "]");

        // 主线程调用 exchange()，与子线程配对完成双向数据交换
        String receivedFromChild = exchanger.exchange(mainData, 5, TimeUnit.SECONDS);

        System.out.println("Main: received [" + receivedFromChild + "] from child thread");

        // 等待子线程结束以确保输出完整
        childThread.join();
        System.out.println("Exchange completed successfully.");
    }

    private static Thread getChildThread(Exchanger<String> exchanger) {
        Thread childThread = new Thread(() -> {
            try {
                // 子线程准备数据并等待交换
                String childData = "Hello from child thread";
                System.out.println("Child: preparing to send [" + childData + "]");

                // exchange() 阻塞直到主线程也调用 exchange()
                String received = exchanger.exchange(childData, 5, TimeUnit.SECONDS);

                System.out.println("Child: received [" + received + "] from main thread");
            } catch (Exception e) {
                System.err.println("Child exchange failed: " + e.getMessage());
            }
        }, "Child-Thread");

        childThread.start();
        return childThread;
    }
}