package cn.itheima.mybatis.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itheima.mybatis.mapper.OrderMapper;
import cn.itheima.mybatis.mapper.UserMapper;
import cn.itheima.mybatis.po.Orders;
import cn.itheima.mybatis.po.User;

public class UserDaoTest {

	private ApplicationContext applicationContext;
	
	@Before
	public void init() throws Exception {
		//初始化spring容器
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
	}
	
	@Test
	public void testGetUserById() {
		UserDao userDao = applicationContext.getBean(UserDao.class);
		User user = userDao.getUserById(32);
		System.out.println(user);
	}
	@Test
	public void testUserMapper() {
		UserMapper userMapper = applicationContext.getBean(UserMapper.class);
		User user = userMapper.getUserById(10);
		System.out.println(user);
	}
	@Test
	public void testOrderMapper() {
		OrderMapper orderMapper = applicationContext.getBean(OrderMapper.class);
		List<Orders> orderList = orderMapper.getOrderList();
		for (Orders orders : orderList) {
			System.out.println(orders);
		}
	}

}
