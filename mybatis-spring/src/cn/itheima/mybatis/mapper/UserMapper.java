package cn.itheima.mybatis.mapper;

import java.util.List;

import cn.itheima.mybatis.po.QueryVo;
import cn.itheima.mybatis.po.User;

public interface UserMapper {

	User getUserById(int id);
	List<User> getUserByName(String username);
	void insertUser(User user);
	User getUserByQueryVo(QueryVo queryVo);
	Integer getUserCount();
	List<User> findUserList(User user);
	List<User> findUserByIds(QueryVo queryVo);
	
}
