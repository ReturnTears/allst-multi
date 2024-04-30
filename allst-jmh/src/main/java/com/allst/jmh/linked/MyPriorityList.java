package com.allst.jmh.linked;

import java.util.Comparator;

/**
 * 增加泛型约束，每一个被加入该链表中的元素都必须实现Comparable接口，就像基本数据类型String一样
 *
 * @author Hutu
 * @since 2024-04-30 下午 09:27
 */
public class MyPriorityList<E extends Comparable<E>> {
    private static class Node<T extends Comparable<T>> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", next=" + next +
                    '}';
        }
    }

    private Node<E> header;

    private int size;
    // 增加了Comparator接口属性
    private final Comparator<E> comparator;

    // 在构造函数中强制要求必须要有Comparator接口
    public MyPriorityList(Comparator<E> comparator) {
        this.comparator = comparator;
        this.header = null;
    }

    public void add(E e) {
        // 定义一个新的node节点，其指向下一个节点的引用值为null
        final Node<E> newNode = new Node<>(e);
        // 当前链表节点引用
        Node<E> current = this.header;
        // 上一个节点的引用，初始值为null，在稍后的计算中会得到
        Node<E> previous = null;
        // 循环遍历链表（当前节点不为null，即不是空的链表）
        while (current != null && e.compareTo(current.getData()) > 0) {
            // 前一个节点为当前节点
            previous = current;
            // 当前节点为当前节点的下一个节点
            current = current.getNext();
        }
        // 如果链表为空
        if (previous == null) {
            // 链表的当前节点引用将直接作为新构造的节点
            this.header = newNode;
        } else {
            // 将新的节点插入前一个节点之后
            previous.setNext(newNode);
        }
        // 新节点的下一个节点为current节点
        newNode.setNext(current);
        this.size++;
    }
}
