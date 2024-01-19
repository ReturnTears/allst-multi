package com.allst.async.chapter4;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Java8 Stream
 *
 * @author Hutu
 * @since 2024-01-20 上午 12:07
 */
public class Java8StreamCode {
    public static void main(String[] args) {
        // noStream(makeList());
        useStream(makeList());
    }

    public static List<Person> makeList() {
        List<Person> personList = Lists.newArrayList();
        Person p1 = new Person();
        p1.setAge(10);
        p1.setName("zlx");
        personList.add(p1);

        p1 = new Person();
        p1.setAge(12);
        p1.setName("jiaduo");
        personList.add(p1);

        p1 = new Person();
        p1.setAge(5);
        p1.setName("ruoran");
        personList.add(p1);
        return personList;
    }

    public static void useStream(List<Person> personList) {
        List<String> nameList = personList.stream().filter(person -> person.getAge() >= 10)// 1.过滤大于等于10的
                .map(Person::getName)// 2.使用map映射元素
                .collect(Collectors.toList());// 3.收集映射后元素
        nameList.forEach(System.out::println);
    }

    public static void noStream(List<Person> personList) {
        List<String> nameList = new ArrayList<>();
        for (Person person : personList) {
            if (person.getAge() >= 10) {
                nameList.add(person.getName());
            }
        }
        for (String name : nameList) {
            System.out.println(name);
        }
    }

}
