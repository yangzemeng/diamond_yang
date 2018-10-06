package com.test;

import com.dao.OrderMapper;
import com.dao.UserMapper;
import com.pojo.Orders;
import com.pojo.QueryVo;
import com.pojo.User;
import com.pojo.UserOrder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author diamod
 * @date 2018-08-31:18:32
 */


public class MybatisTest {
    SqlSessionFactory build=null;
    @Before
    public void innit() throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder=new SqlSessionFactoryBuilder();
        //得到sessionfactory对象
        InputStream inputStream= Resources.getResourceAsStream("SqlMapConfig.xml");
        //自动补全类名自动补全变量名称 : Ctrl + Alt + v
        //自动补全属性名称 : Ctrl + Alt + f
         build = sqlSessionFactoryBuilder.build(inputStream);
    }
    @Test
    public void testDemo1() throws IOException {
        //创建sqlsessionFactorybuilder对象

        //得到sqlsession对象
        SqlSession sqlSession = build.openSession();
       User user = sqlSession.selectOne("getUserById", 10);
        System.out.println(user);
        sqlSession.close();
    }
    @Test
    public void getByName(){
        SqlSession sqlSession = build.openSession();
        List<User> list = sqlSession.selectList("getUserByUsername", "张");

        for (User user:list){
            System.out.println(user);
        }
        sqlSession.close();
    }
    @Test
    public void insertUser(){
        //执行增删改的时候要记得commit
        SqlSession sqlSession=null;
        try {
            sqlSession = build.openSession();
            User user = new User();
            user.setUsername("diamond");
            user.setAddress("云梦");
            user.setBirthday(new Date());
            user.setSex("男");
            sqlSession.insert("test.insertUser",user);
            System.out.println(user.getId());
            sqlSession.commit();
        }catch (Exception e){
           e.printStackTrace();
        }finally {
            if (sqlSession !=null){
                sqlSession.close();
            }

        }


    }
    @Test
    public void update(){
        SqlSession sqlSession=null;
        try {
            sqlSession = build.openSession();
            User user = new User();
            user.setUsername("andy");
            user.setId(31);
            sqlSession.update("test.updateUser",user);
            sqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (sqlSession !=null){
                sqlSession.close();
            }

        }
    }
    @Test
    public void testdemo2() throws Exception {
        SqlSession sqlSession = build.openSession();
      //通过代理对象来处理
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(10);
        System.out.println(user);
    }
    @Test
    public void testdemo3() throws Exception {
        SqlSession sqlSession = build.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.getUserByUsername("张");
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void testDemo4() throws Exception {
        SqlSession sqlSession = build.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        QueryVo queryVo = new QueryVo();
        User user = new User();
        user.setId(32);
        queryVo.setUser(user);
        User userByVo = mapper.getUserByVo(queryVo);
        System.out.println(userByVo);
       sqlSession.close();
    }
    @Test
    public void testDemo5() throws Exception {
        SqlSession sqlSession = build.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Integer count = mapper.getCount();
        System.out.println(count);
    }
    @Test
    public void testDemo6() throws Exception {
        SqlSession sqlSession = build.openSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        List<Orders> orders = mapper.getOrders2();
        for (Orders order : orders) {
            System.out.println(order);
        }

    }
    @Test
    public void getListTest() throws Exception {
        SqlSession sqlSession = build.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        QueryVo queryVo = new QueryVo();
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(10);
        list.add(16);

        queryVo.setIds(list);
        List<User> listFor = mapper.getListFor(queryVo);
        for (User user1 : listFor) {
            System.out.println(user1);
        }
    }
    @Test
    public void getListUser() throws Exception {
        SqlSession sqlSession = build.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<Orders> listUser = mapper.getListuser2();
        for (Orders userOrder : listUser) {
            System.out.println(userOrder);
        }


    }


}
