package com.itheima.springmvc.controller;

import com.itheima.springmvc.po.Items;
import com.itheima.springmvc.po.User;
import com.itheima.springmvc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author diamod
 * @date 2018-09-03:17:31
 */

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @RequestMapping("/itemList.action")
    public ModelAndView itemList(){
        //调用service查询数据库
        List<Items> itemist = itemService.getList();
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("itemList",itemist);
        modelAndView.setViewName("itemList");

        return modelAndView;
    }
    @RequestMapping("/itemEdit.action")
    public ModelAndView getById(Integer id){
        Items item = itemService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("item",item);
        modelAndView.setViewName("editItem");
        return modelAndView;
    }
    @RequestMapping("/updateitem.action")
    public String updateById(Items items, MultipartFile picture, HttpServletRequest request) throws IOException {
        //为每个文件生成一个新的文件名
        String picName = UUID.randomUUID().toString();
        //取文件扩展名
        String oriName = picture.getOriginalFilename();
        String extName = oriName.substring(oriName.lastIndexOf(".")+1);
        //获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload2/");
        String contextPath = request.getContextPath()+"/upload2/";
        picture.transferTo(new File(realPath+"/"+picName+extName));
        items.setPic(contextPath+"/"+picName+extName);
        itemService.update(items);

        return "forward:/itemEdit.action";
    }
    @RequestMapping("/queryitem.action")
    public String query(String[] ids){
        System.out.println(ids);
        return null;

    }
}
