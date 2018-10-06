package com.dao;

import com.pojo.Orders;
import com.pojo.QueryVo;
import com.pojo.User;
import com.pojo.UserOrder;

import java.util.List;

/**
 * @author diamod
 * @date 2018-08-31:22:54
 */


public interface UserMapper {
    User getUserById(int id) throws Exception;
    List<User> getUserByUsername(String username) throws  Exception;
    User getUserByVo(QueryVo queryVo) throws  Exception;
    Integer getCount() throws  Exception;
    List<User> getList(User user) throws  Exception;
    List<User> getListFor(QueryVo queryVo) throws  Exception;
    List<UserOrder> getListUser() throws Exception;
    List<Orders> getListuser2() throws  Exception;
}
