package com.itheima.springmvc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itheima.springmvc.pojo.Items;

/**
 * 商品列表Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class ItemController {

	//.action可以省略
	@RequestMapping("/itemList.action")
	public ModelAndView itemList() {
		//查询商品列表
		List<Items> itemList = new ArrayList<>();
		itemList.add(new Items(1, "imac", 20000, new Date(), "苹果本很贵"));
		itemList.add(new Items(2, "imac1", 20000, new Date(), "苹果本很贵"));
		itemList.add(new Items(3, "imac2", 20000, new Date(), "苹果本很贵"));
		itemList.add(new Items(4, "imac3", 20000, new Date(), "苹果本很贵"));
		itemList.add(new Items(5, "imac4", 20000, new Date(), "苹果本很贵"));
		//把商品列表传递给jsp
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemList", itemList);
		//设置展示数据的jsp
		//modelAndView.setViewName("/WEB-INF/jsp/itemList.jsp");
		//配置完视图解析器后只需要返回jsp的名称即可
		modelAndView.setViewName("itemList");
		//返回结果
		return modelAndView;
		
	}
}
