package com.itheima.springmvc.service.impl;

import com.itheima.springmvc.mapper.ItemsMapper;
import com.itheima.springmvc.po.Items;
import com.itheima.springmvc.po.ItemsExample;
import com.itheima.springmvc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author diamod
 * @date 2018-09-03:17:38
 */

@Service
@Transactional
public class ItemserviceImpl implements ItemService {
    @Autowired
    private ItemsMapper itemsMapper;
    @Override
    public List<Items> getList() {
        ItemsExample itemsExample=new ItemsExample();
        List<Items> itemsList = itemsMapper.selectByExampleWithBLOBs(itemsExample);
        return itemsList;
    }

    @Override
    public Items getById(int id) {
        Items items = itemsMapper.selectByPrimaryKey(id);
        return items;
    }

    @Override
    public void update(Items items) {
        itemsMapper.updateByPrimaryKeySelective(items);
    }
}
