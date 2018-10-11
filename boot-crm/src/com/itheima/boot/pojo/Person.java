package com.itheima.boot.pojo;

/**
 * @author diamod
 * @date 2018-10-10:15:13
 */


public class Person {
    Integer id;
    String name;
    Integer age;
    String  address;
    String psddword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPsddword() {
        return psddword;
    }

    public void setPsddword(String psddword) {
        this.psddword = psddword;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", psddword='" + psddword + '\'' +
                '}';
    }
}
