package com.allst.multi.thread3;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author June
 * @version 1.0
 * @date 2018-06-30
 */
public class UseDeque {

    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(10);
        deque.addFirst("a");
        deque.addFirst("b");
        deque.addFirst("c");
        deque.addFirst("d");
        deque.addFirst("e");
        deque.addFirst("f");
        deque.addFirst("g");
        deque.addFirst("h");
        deque.addFirst("i");
        deque.addFirst("j");
        //deque.offerFirst("k")
        System.out.println("查看头元素 : " + deque.peekFirst());
        System.out.println("查看尾元素 : " + deque.pollLast());
        Object[] objs = deque.toArray();
        for (int i = objs.length - 1; i > 0; i--) {
            System.out.println(objs[i]);
        }
    }
}
