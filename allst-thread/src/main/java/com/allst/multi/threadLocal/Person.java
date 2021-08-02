package com.allst.multi.threadLocal;

/**
 * @author June
 * @since 2021年08月
 */
public class Person {

    private ThreadLocal<String> name = new ThreadLocal<>();

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void remove() {
        this.name.remove();
    }
}
