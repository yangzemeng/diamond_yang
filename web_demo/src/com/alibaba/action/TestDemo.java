package com.alibaba.action;

/**
 * @author diamod
 * @date 2018-08-28:10:30
 */


public class TestDemo {
    public static void main(String[] args) {
        Student student = new Student();
        student.setAge(18);
        student.setName("diamond");
        System.out.println("年龄：" + student.getAge() + "名字：" + student.getName());
        //自动补全返回值
        String a="hello";
        String b="world";
        String c=add(a,b);


    }

    private static String add(String a, String b) {

        return a+b;
    }
}
