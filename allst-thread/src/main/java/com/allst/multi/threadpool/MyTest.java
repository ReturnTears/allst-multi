package com.allst.multi.threadpool;

/**
 * 测试类
 *
 * @author YiYa
 * @since 2020-03-07 下午 11:52
 */
public class MyTest {

    public static void main(String[] args) {
        MyThreadPool pool = new MyThreadPool(2, 4, 20);

        for (int i = 0; i < 10; i++) {
            MyTask my = new MyTask(i);
            pool.submit(my);
        }
    }

}
