package cn.itheima.mybatis.dao;

import java.util.List;

import cn.itheima.mybatis.po.User;

public interface UserDao {

	User getUserById(int id);
	List<User> getUserByName(String username);
	void insertUser(User user);
}
