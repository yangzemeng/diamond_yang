package com.dao;

import com.pojo.Orders;

import java.util.List;

/**
 * @author diamod
 * @date 2018-09-01:18:46
 */


public interface OrderMapper {
   List<Orders> getOrders() throws Exception;
   List<Orders> getOrders2() throws  Exception;
}
