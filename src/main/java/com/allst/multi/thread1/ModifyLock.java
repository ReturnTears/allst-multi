package com.allst.multi.thread1;

import com.allst.multi.utils.ThreadPools;

import java.util.concurrent.ExecutorService;

/**
 * 同一对象属性的修改不会影响锁的情况
 * @author June
 * @version 1.0
 * @date 2018-06-29
 */
public class ModifyLock {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public  synchronized  void changeAttribute(String name, int age) {
        try {
            System.out.println("curr thread : " + Thread.currentThread().getName() + " start. " + name + " : " + age);
            this.setName(name);
            this.setAge(age);
            Thread.sleep(2000);
            System.out.println("curr thread : " + Thread.currentThread().getName() + " end. " + name + " : " + (age + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        final ModifyLock lock = new ModifyLock();
//        Thread t1 = new Thread(() -> {
//           lock.changeAttribute("xiaohu", 18);
//        }, "t1");
//        Thread t2 = new Thread(() -> {
//            lock.changeAttribute("yangyang", 17);
//        }, "t2");
//
//        t1.start();
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        t2.start();
//    }

    public static void main(String[] args) throws InterruptedException {
        final ModifyLock lock = new ModifyLock();
        ExecutorService service = ThreadPools.getInstance();
        service.execute(() -> lock.changeAttribute("yangYang", 17));
        Thread.sleep(1000);
        service.execute(() -> lock.changeAttribute("June", 20));
        service.shutdown();
    }
}
