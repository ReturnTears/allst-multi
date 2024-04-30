package com.allst.jmh.linked;

/**
 * @author Hutu
 * @since 2024-04-30 下午 09:32
 */
public class MyPriorityListTest {
    public static void main(String[] args) {
        MyPriorityList<Integer> list = new MyPriorityList<>(Integer::compareTo);
        list.add(50);
        System.out.println(list);
        System.out.println("===================================");
        list.add(100);
        list.add(4);
        list.add(20);
        list.add(130);
        System.out.println(list);
        System.out.println("===================================");
    }
}
