package com.itheima.boot.dao;

import com.itheima.boot.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    List<Product> findBy(@Param("params") Map<String, String> map);
}
