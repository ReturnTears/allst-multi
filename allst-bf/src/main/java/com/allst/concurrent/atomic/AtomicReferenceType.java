package com.allst.concurrent.atomic;

import com.allst.concurrent.pojo.User;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子更新引用类型
 *
 * @author June
 * @since 2021年07月
 */
public class AtomicReferenceType {

    private static final AtomicReference<User> atomicUserRef = new AtomicReference<>();

    public static void main(String[] args) {
        User user = new User("KangKang", 20);
        atomicUserRef.set(user);
        User updatedUser = new User("JunJun", 22);
        atomicUserRef.compareAndSet(user, updatedUser);
        System.out.println(atomicUserRef.get().getName());
        System.out.println(atomicUserRef.get().getAge());
    }
}
