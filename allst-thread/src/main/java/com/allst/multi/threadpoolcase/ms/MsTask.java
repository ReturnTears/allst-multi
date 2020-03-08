package com.allst.multi.threadpoolcase.ms;

/**
 * 秒杀任务类
 * @author YiYa
 * @since 2020-03-08 下午 09:06
 */
public class MsTask implements Runnable {

    private static int nums = 10;

    private String name;

    public MsTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.printf("%s 正在使用 %s 参与秒杀任务~~~\n", name, threadName);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (MsTask.class) {
            if (nums > 0) {
                System.out.printf("%s 使用 %s 参与秒杀: %d 号商品成功~~~\n", name, threadName, nums);
            } else {
                System.out.printf("%s 使用 %s 参与秒杀失败~~~\n", name, threadName);
            }
        }
    }
}
