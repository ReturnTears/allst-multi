package com.allst.jmh.tools;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * @author Hutu
 * @since 2024-04-22 下午 09:09
 */
public class ExchangerExample2 {

    public static void main(String[] args) {
        // 定义数据类型为String的Exchanger
        final Exchanger<String> exchanger = new Exchanger<>();
        // 定义StringGenerator线程， 并将该线程命名为Generator
        StringGenerator generator = new StringGenerator(exchanger, "Generator");
        // 定义StringConsumer线程， 并将该线程命名为Consumer
        StringConsumer consumer = new StringConsumer(exchanger, "Consumer");
        // 启动线程
        consumer.start();
        generator.start();
        // 休眠1分钟后， 将线程关闭
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            consumer.close();
            generator.close();
        }
    }

    private interface Closable {
        // 关闭方法
        void close();

        // 判断当前线程是否被关闭
        boolean closed();
    }

    private abstract static class ClosableThread extends Thread implements Closable {
        protected final Exchanger<String> exchanger;
        // 线程关闭标志
        private volatile boolean closed = false;

        public ClosableThread(Exchanger<String> exchanger, final String name) {
            super(name);
            this.exchanger = exchanger;
        }

        public void run() {
            while (!closed()) {
                this.doExchange();
            }
        }

        protected abstract void doExchange();

        // 关闭方法
        @Override
        public void close() {
            System.out.println(currentThread() + "will be closed.");
            this.closed = true;
            this.interrupt();
        }

        // 判断当前线程是否被关闭
        @Override
        public boolean closed() {
            return this.closed || this.isInterrupted();
        }
    }

    private static class StringGenerator extends ClosableThread {
        private char initialValue = 'A';

        public StringGenerator(Exchanger<String> exchanger, String name) {
            super(exchanger, name);
        }

        @Override
        protected void doExchange() {
            StringBuilder data = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                randomSleep();
                data.append(++initialValue);
            }
            try {
                // 如果当前线程未关闭，则执行Exchanger的exchange方法
                if (!this.closed()) {
                    this.exchanger.exchange(data.toString());
                }
            } catch (InterruptedException e) {
                System.out.println(currentThread() + " received the close signal.");
            }
        }
    }

    private static class StringConsumer extends ClosableThread {
        public StringConsumer(Exchanger<String> exchanger, String name) {
            super(exchanger, name);
        }

        @Override
        protected void doExchange() {
            try {
                // 如果当前线程未关闭，则执行Exchanger的exchange方法
                if (!this.closed()) {
                    String data = this.exchanger.exchange(null);
                    System.out.println(currentThread() + " received data: " + data);
                }
            } catch (InterruptedException e) {
                System.out.println(currentThread() + " received the close signal.");
            }
        }
    }

    private static void randomSleep() {
        try {
            TimeUnit.SECONDS.sleep(current().nextInt(5));
        } catch (InterruptedException e) {
        }
    }
}
