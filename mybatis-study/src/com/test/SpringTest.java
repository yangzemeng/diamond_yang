package com.test;

import cn.itcast.ssm.mapper.UserMapper;
import cn.itcast.ssm.po.User;
import cn.itcast.ssm.po.UserExample;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author diamod
 * @date 2018-09-02:10:59
 */


public class SpringTest {
    private ApplicationContext applicationContext;

    @Before
    public void init() throws Exception {
        //初始化spring容器
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }
    @Test
    public void TestSelectByExample(){
        UserMapper mapper = applicationContext.getBean(UserMapper.class);
        UserExample userExample=new UserExample();
        List<User> users = mapper.selectByExample(userExample);
        for (User user : users) {
            System.out.println(user);
        }
    }

}
