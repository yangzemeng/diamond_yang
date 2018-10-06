package com.itheima.springmvc.service;

import com.itheima.springmvc.po.Items;

import java.util.List;

/**
 * @author diamod
 * @date 2018-09-03:17:21
 */


public interface ItemService {
    List<Items>  getList();
    Items getById(int id);

    void update(Items items);
}
