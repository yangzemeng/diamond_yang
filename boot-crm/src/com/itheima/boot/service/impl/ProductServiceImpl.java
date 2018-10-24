package com.itheima.boot.service.impl;

import com.itheima.boot.dao.ProductDao;
import com.itheima.boot.pojo.Product;
import com.itheima.boot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author diamod
 * @date 2018-10-20:22:13
 */

@Service
public class ProductServiceImpl implements ProductService {
   @Autowired
   private ProductDao productDao;
    @Override
    public List<Product> getProductList() {
        List<Product> productList = productDao.getProductList();
        return productList;
    }

    @Override
    public List<Product> quaryBy(Product product) {
       List<Product>  proList=productDao.quaryBy(product);
        return proList;
    }

    @Override
    public Integer insert(Product product) {
      Integer s= productDao.insert(product);
        return s;
    }

    @Override
    public Integer update(Product product) {
       Integer s= productDao.update(product);
        return s;
    }

    @Override
    public Integer delete(Integer id) {
       Integer s= productDao.delete(id);
        return s;
    }
}
