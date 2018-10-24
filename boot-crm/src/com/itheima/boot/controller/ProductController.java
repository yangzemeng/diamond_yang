package com.itheima.boot.controller;

import com.itheima.boot.pojo.Product;
import com.itheima.boot.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author diamod
 * @date 2018-10-20:22:01
 */

@Controller
public class ProductController {
    @Autowired
   private ProductService productService;
    @RequestMapping("/product/list")
    @ResponseBody
    public List<Product> quaryList(){
        List<Product> productList = productService.getProductList();
        return productList;
    }
    @RequestMapping("/product/quary")
    @ResponseBody
    public List<Product> quaryBy(@RequestBody Product product) throws UnsupportedEncodingException {
        //乱码处理
        /*if (StringUtils.isNotBlank(product.getPname())) {
            product.setPname(new String(product.getPname().getBytes("iso8859-1"),"utf-8"
            ));
        }*/
        List<Product> productList=productService.quaryBy(product);
        return productList;
    }
    @RequestMapping("/product/insert")
    @ResponseBody
    public String insert(@RequestBody Product product){
       Integer i=productService.insert(product);

        String MSG;
        if (i>0){
            MSG="插入数据成功";
        }else{
            MSG="插入数据失败";
        }
        return MSG;
    }
    @RequestMapping("/product/update")
    @ResponseBody
    public String update(@RequestBody Product product){
      Integer s=productService.update(product);
        String MSG;
        if (s>0){
            MSG="修改数据成功";
        }else{
            MSG="修改数据失败";
        }
        return MSG;
    }
    @RequestMapping("/product/delete")
    @ResponseBody
    public String delete(@RequestParam("pid") Integer id){
        Integer s=productService.delete(id);
        String MSG;
        if (s>0){
            MSG="删除数据成功";
        }else{
            MSG="删除数据失败";
        }
        return MSG;
    }

}
