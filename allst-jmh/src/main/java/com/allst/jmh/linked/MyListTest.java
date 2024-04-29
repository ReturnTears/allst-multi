package com.allst.jmh.linked;

/**
 * 测试MyList
 * 结果如下：
 * [5,4,3,2,1]
 * 5
 * false
 * 5
 * ==============================
 * 5
 * 4
 * [4,3,2,1]
 * 4
 * 4
 * 3
 * 2
 * 1
 * ==============================
 * true
 * 0
 * []
 *
 * @author Hutu
 * @since 2024-04-29 下午 09:47
 */
public class MyListTest {
    public static void main(String[] args) {
        // 定义类型为Integer的链表，并且增加5个元素
        MyList<Integer> list = new MyList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        // 对mylist进行测试
        System.out.println(list);
        System.out.println(list.size());
        System.out.println(list.isEmpty());
        System.out.println(list.peekFirst());
        System.out.println("==============================");
        System.out.println(list.popFirst());
        System.out.println(list.size());
        System.out.println(list);
        System.out.println(list.peekFirst());
        System.out.println(list.popFirst());
        System.out.println(list.popFirst());
        System.out.println(list.popFirst());
        System.out.println(list.popFirst());
        System.out.println("==============================");
        System.out.println(list.isEmpty());
        System.out.println(list.size());
        System.out.println(list);
    }
}
