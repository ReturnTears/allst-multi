package com.allst.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 原子更新数组
 *
 * @author June
 * @since 2021年07月
 */
public class AtomicArrayType {

    static int[] value = new int[]{1, 2};

    private static final AtomicIntegerArray aia = new AtomicIntegerArray(value);

    /**
     * 注意：
     * 数组value通过构造方法传递进去，然后AtomicIntegerArray会将当前数组
     * 复制一份，所以当AtomicIntegerArray对内部的数组元素进行修改时，不会影响传入的数组
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        aia.getAndSet(0, 3);
        System.out.println(aia.get(0));
        System.out.println(value[0]);
        aia.addAndGet(1, 2);
        System.out.println(aia.get(1));
        boolean b = aia.compareAndSet(1, 4, 7);
        if (b) {
            System.out.println("update success !");
        } else {
            System.out.println("update error !");
        }
    }
}
