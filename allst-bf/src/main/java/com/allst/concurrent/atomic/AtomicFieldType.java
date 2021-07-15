package com.allst.concurrent.atomic;

import com.allst.concurrent.pojo.User;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 原子更新字段类型
 * 要想原子地更新字段类需要两步。第一步，因为原子更新字段类都是抽象类，每次使用的
 * 时候必须使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性。第
 * 二步，更新类的字段（属性）必须使用public volatile修饰符。
 *
 * @author June
 * @since 2021年07月
 */
public class AtomicFieldType {

    private static final AtomicIntegerFieldUpdater<User> atomic = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

    public static void main(String[] args) {
        User user = new User("Hello", 12);
        System.out.println(atomic.getAndIncrement(user));
        System.out.println(atomic.get(user));
    }
}
