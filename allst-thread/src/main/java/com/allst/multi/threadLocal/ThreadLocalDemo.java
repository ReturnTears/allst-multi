package com.allst.multi.threadLocal;

import java.util.concurrent.TimeUnit;

/**
 * 若Person对象中属性没有使用ThreadLocal对象修饰， 那么如下代码获得的数据会存在交叉
 *
 * @author June
 * @since 2021年08月
 */
public class ThreadLocalDemo {
    public static void main(String[] args) {
        Person person = new Person();
        new Thread(new Runnable() {
            @Override
            public void run() {
                person.setName("KangShuai");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1 >> : " + person.getName());
                // 解决第三点造成内存泄漏的情况
                person.remove();
            }
        }).start();

        new Thread(() -> {
            person.setName("XiaoHu");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 2 >> : " + person.getName());
            // 解决第三点造成内存泄漏的情况
            person.remove();
        }).start();
    }
}
