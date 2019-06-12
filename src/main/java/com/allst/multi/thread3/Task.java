package com.allst.multi.thread3;

/**
 * @author June
 * @version 1.0
 * @date 2018-06-30
 */
public class Task implements Comparable<Task> {

    private int id ;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Task o) {
        return this.id > o.id ? 1 : (this.id < o.id ? -1 : 0);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
