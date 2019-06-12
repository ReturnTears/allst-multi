package com.allst.multi.thread3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * Queue
 * @author June
 * @version 1.0
 * @date 2018-06-30
 */
public class UseQueue {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 高性能无阻塞无界队列：ConcurrentLinkedQueue
         * ConcurrentLinkedQueue是一个基于链接节点的无界线程安全队列，它采用先进先出的规
         * 则对节点进行排序，当我们添加一个元素的时候，它会添加到队列的尾部；当我们获取一个元
         * 素时，它会返回队列头部的元素
         */
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.offer("a");
        q.offer("b");
        q.offer("c");
        q.offer("d");
        q.add("e");
        //a 从头部取出元素，并从队列里删除
//        System.out.println(q.poll());
//        System.out.println(q.size());
//        System.out.println(q.peek());
//        System.out.println(q.size());

        ArrayBlockingQueue<String> array = new ArrayBlockingQueue<>(6);
        array.put("a");
        array.put("b");
        array.add("c");
        array.add("d");
        array.add("e");
        array.add("f");
//        System.out.println(array.offer("a", 3, TimeUnit.SECONDS));

        //阻塞队列
        LinkedBlockingQueue<String> qu = new LinkedBlockingQueue<>();
        qu.offer("a");
        qu.offer("b");
        qu.offer("c");
        qu.offer("d");
        qu.offer("e");
        qu.add("f");
//        System.out.println(qu.size());

		for (Iterator iterator = qu.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
//			System.out.println(string);
		}

        List<String> list = new ArrayList<>();
//        System.out.println(qu.drainTo(list, 3));
//        System.out.println(list.size());
        for (String string : list) {
//            System.out.println(string);
        }

        /**
         * 一个不存储元素的阻塞队列
         */
        final SynchronousQueue<String> que = new SynchronousQueue<>();
        Thread t1 = new Thread(() -> {
            try {
                System.out.println(que.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> que.add("asdasd"));
        t2.start();
    }
}
