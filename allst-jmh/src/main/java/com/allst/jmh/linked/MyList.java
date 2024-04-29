package com.allst.jmh.linked;

/**
 * @author Hutu
 * @since 2024-04-29 下午 09:21
 */
public class MyList<E> {

    /*
    链表中有一个非常重要的元素Head，它代表当前节点元素的引用，当链表被初始化时，当前节点属性指向为NULL
     */
    // 当前节点引用
    private Node<E> header;
    // 链表元素的个数
    private int size;

    public MyList() {
        // 初始化头节点
        // header = new Node<>(null, null);
        // 当前元素节点为指向Null的属性
        this.header = null;
    }

    // 判断当前列表是否为空
    public boolean isEmpty() {
        // 只需要判断当前节点引用是否为null即可得知
        return header == null;
    }

    // 清楚链表中的所有元素
    public void clear() {
        // 显示设定当前size为0
        this.size = 0;
        // 将当前节点引用设置为null即可，由于其他元素ROOT不可达，因此在稍后的垃圾回收中会被回收
        this.header = null;
    }

    /**
     * 向链表头部增加元素
     * 在非常熟练的情况下，this.header = new Node<>(e,header);
     */
    public void add(E e) {
        // 定义新的node节点，并且其next引用指向当前节点所引用的header
        Node<E> node = new Node<>(e, header);
        // 将当前节点header指向新的node节点
        this.header = node;
        // 元素数量加一
        this.size++;
    }

    /**
     * 链表的peekFirst操作
     * peek操作不会对当前链表产生任何副作用，其只是简单地返回当前链表头部的数据
     */
    public E peekFirst() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("the linked list is empty now, can`t support peek operation");
        }
        return header.getValue();
    }

    /**
     * 链表元素的弹出操作
     */
    public E popFirst() {
        // 判断当前链表是否为空
        if (isEmpty()) {
            // 如果为空则直接抛出异常
            throw new IndexOutOfBoundsException("The linked list is empty now, can't support pop operation");
        }
        // 获取当前节点的数据，作为该方法的最终返回值
        final E value = header.getValue();
        // 将链表的当前节点引用直接指向当前节点的下一个节点
        this.header = header.getNext();
        // 元素数量减一
        this.size--;
        // 返回数据
        return value;
    }

    // --------------- 链表的其他方法
    // 获取当前链表的元素个数
    public int size() {
        // 直接返回内部成员属性size即可
        return this.size;
    }

    // 重写toString方法，将列表中的每一个元素进行连接后输出
    @Override
    public String toString() {
        Node<E> node = this.header;
        final StringBuilder builder = new StringBuilder("[");
        while (node != null) {
            builder.append(node.getValue().toString()).append(",");
            node = node.getNext();
        }
        if (builder.length() > 1)
            builder.deleteCharAt(builder.length() - 1);

        builder.append("]");
        return builder.toString();
    }


    // Node 是一个泛型类， 可用于存储任意类型元素的节点
    class Node<T> {
        // 数据属性
        private final T value;
        // 执行下一个节点的引用
        private final Node<T> next;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getNext() {
            return next;
        }

    }
}