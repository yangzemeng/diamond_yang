package com.itheima.mybatis.mapper;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itheima.mybatis.po.User;
import com.itheima.mybatis.po.UserExample;
import com.itheima.mybatis.po.UserExample.Criteria;

public class UserMapperTest {
	
private ApplicationContext applicationContext;
	
	@Before
	public void init() throws Exception {
		//初始化spring容器
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
	}

	@Test
	public void testDeleteByPrimaryKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		UserMapper userMapper = applicationContext.getBean(UserMapper.class);
		User user = new User();
		user.setUsername("武大郎");
		user.setSex("1");
		user.setBirthday(new Date());
		user.setAddress("河北清河县");
		userMapper.insert(user);
	}

	@Test
	public void testSelectByExample() {
		UserMapper userMapper = applicationContext.getBean(UserMapper.class);
		UserExample example = new UserExample();
		//Criteria criteria = example.createCriteria();
		//criteria.andUsernameLike("%张%");
		//执行查询
		List<User> list = userMapper.selectByExample(example);
		for (User user : list) {
			System.out.println(user);
		}
	}

	@Test
	public void testSelectByPrimaryKey() {
		UserMapper userMapper = applicationContext.getBean(UserMapper.class);
		User user = userMapper.selectByPrimaryKey(32);
		System.out.println(user);
	}

	@Test
	public void testUpdateByPrimaryKey() {
		fail("Not yet implemented");
	}

}
