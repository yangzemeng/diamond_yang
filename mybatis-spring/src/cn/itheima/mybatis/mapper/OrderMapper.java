package cn.itheima.mybatis.mapper;

import java.util.List;

import cn.itheima.mybatis.po.OrderUser;
import cn.itheima.mybatis.po.Orders;
import cn.itheima.mybatis.po.User;

public interface OrderMapper {
	List<Orders> getOrderList();
	List<Orders> getOrderListResultMap();
	List<OrderUser> getOrderUserList();
	List<Orders> getOrderUserResultMap();
	List<User> getUserWithOrders();
}
