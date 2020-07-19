package com.example.demo;

import org.springframework.beans.BeanUtils;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Person p1 = new Person();
        p1.setName("wuhan");
        p1.setAge(20);
        
        Person p2 = new Person();
        BeanUtils.copyProperties(p1, p2);
        System.out.println("name:" + p2.getName());
        System.out.println("age:" + p2.getAge());
    }

}
