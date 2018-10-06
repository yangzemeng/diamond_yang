package com.pojo;

/**
 * @author diamod
 * @date 2018-09-01:21:09
 */


public class UserOrder extends Orders{
    private String username;
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                "} " + super.toString();
    }

  }
