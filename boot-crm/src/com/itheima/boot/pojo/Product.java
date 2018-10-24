package com.itheima.boot.pojo;

/**
 * @author diamod
 * @date 2018-10-20:22:03
 */


public class Product {
    private Integer pid;
    private String pname;
    private String pcolor;
    private String pnum;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPcolor() {
        return pcolor;
    }

    public void setPcolor(String pcolor) {
        this.pcolor = pcolor;
    }

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    public Product(Integer pid, String pname, String pcolor, String pnum) {
        this.pid = pid;
        this.pname = pname;
        this.pcolor = pcolor;
        this.pnum = pnum;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", pcolor='" + pcolor + '\'' +
                ", pnum='" + pnum + '\'' +
                '}';
    }

    public Product() {
    }
}
