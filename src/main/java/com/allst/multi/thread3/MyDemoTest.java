package com.allst.multi.thread3;

import java.util.concurrent.DelayQueue;

/**
 * DelayQueue：一个使用优先级队列实现的无界阻塞队列
 * @author June
 * @version 1.0
 * @date 2018-06-30
 */
public class MyDemoTest implements Runnable {

    private DelayQueue<MyDemoTask> queue = new DelayQueue<>();

    public boolean onDoing =true;

    public void onWorking(String name, String id, int money) {
        MyDemoTask task = new MyDemoTask(name, id, 1000 * money);
        System.out.println("名字是 : " + task.getName() + ", ID是 : " + task.getId() + ", pay money is : " + money + " up doing");
        this.queue.add(task);
    }

    public void noWorking(MyDemoTask task) {
        System.out.println("名字是 : " + task.getName() + ", ID 是 : " + task.getId() + " downing...");
    }

    @Override
    public void run() {
        while (onDoing) {
            try {
                MyDemoTask task = queue.take();
                noWorking(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("is working ，welcome in....");
            MyDemoTest test = new MyDemoTest();
            Thread thread = new Thread(test);
            thread.start();
            test.onWorking("yangyang", "123454321", 10);
            test.onWorking("June", "05986736", 4);
            test.onWorking("Seven", "343094857", 18);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
