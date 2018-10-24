package com.itheima.boot.dao;

import com.itheima.boot.pojo.Product;

import java.util.List;

/**
 * @author diamod
 * @date 2018-10-20:22:16
 */


public interface ProductDao {
    List<Product> getProductList();

    List<Product> quaryBy(Product product);

    Integer insert(Product product);

    Integer update(Product product);

    Integer delete(Integer id);
}
