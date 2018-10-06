package com.itheima.springmvc.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itheima.springmvc.exception.CustomerException;
import com.itheima.springmvc.pojo.Items;
import com.itheima.springmvc.pojo.QueryVo;
import com.itheima.springmvc.service.ItemService;

/**
 * 商品处理Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/itemList")
	public ModelAndView getItemList() throws Exception {
		//自定义异常测试
		/*if (true) {
			throw new CustomerException("这是我们自定义的异常，哈哈哈。。。。。");
		}*/
		//int i = 1/0;
		//查询商品列表
		List<Items> itemList = itemService.getItemList();
		//把结果传递给页面
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemList", itemList);
		//设置逻辑视图
		modelAndView.setViewName("itemList");
		//返回结果
		return modelAndView;
	}
	
	/*@RequestMapping("/itemEdit")
	public String editItem(HttpServletRequest request, 
			HttpServletResponse response, HttpSession session, Model model) {
		//从request中取参数
		String strId = request.getParameter("id");
		int id = new Integer(strId);
		//调用服务
		Items items = itemService.getItemById(id);
		//把结果传递给页面
		//ModelAndView modelAndView = new ModelAndView();
		//modelAndView.addObject("item", items);
		//设置逻辑视图
		//modelAndView.setViewName("editItem");
		//return modelAndView;
		//设置返回结果
		model.addAttribute("item", items);
		//返回逻辑视图
		return "editItem";
	}
	*/
	@RequestMapping("/itemEdit")
	public String editItem1(@RequestParam(value="id",defaultValue="1",required=true)Integer ids, Model model) {
		Items items = itemService.getItemById(ids);
		//把数据传递给页面
		model.addAttribute("item", items);
		//返回逻辑视图
		return "editItem";
	}
	@RequestMapping("/itemEdit/{id}")
	//如果id和方法的形参一致@PathVariable中可以不写内容
	public String editItem(@PathVariable("id") Integer iid, Model model) {
		Items items = itemService.getItemById(iid);
		//把数据传递给页面
		model.addAttribute("item", items);
		//返回逻辑视图
		return "editItem";
	}
	@RequestMapping("/updateitem")
	public String updateItem(Items items, MultipartFile picture,HttpServletRequest request) throws Exception {
		//把图片保存到图片目录下
		//为每个文件生成一个新的文件名
		String picName = UUID.randomUUID().toString();
		//取文件扩展名
		String oriName = picture.getOriginalFilename();
		String extName = oriName.substring(oriName.lastIndexOf(".")+1);
		//获取绝对路径
		String realPath = request.getSession().getServletContext().getRealPath("/upload/");
		String contextPath = request.getContextPath()+"/upload/";
		picture.transferTo(new File(realPath+"/"+picName+extName));
		items.setPic(contextPath+"/"+picName+extName);
		itemService.updateItem(items);
		//返回成功页面
		//redirect页面跳转
		//return "redirect:/item/itemList.action";
		//forward跳转
		return "forward:/item/itemEdit.action";
	}
	
	@RequestMapping("/queryitem")
	public String queryItem(QueryVo queryVo, String[] ids) {
		//打印绑定结果
		System.out.println(queryVo.getItems().getId());
		System.out.println(queryVo.getItems().getName());
		
		return "success";
	}
	
	@RequestMapping("/itemList2")
	public void itemList2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*//查询商品列表
		List<Items> itemList = itemService.getItemList();
		//向页面传递参数
		request.setAttribute("itemList", itemList);
		//如果使用原始的方式做页面跳转，必须给的jsp的完整路径
		request.getRequestDispatcher("/WEB-INF/jsp/itemList.jsp").forward(request, response);*/
		PrintWriter writer = response.getWriter();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		writer.write("{\"id\":\"123\"}");
		
	}
	
	//json数据交互
	//@RequestBody:接收json数据并转换成pojo对象
	//@ResponseBody:响应json数据，把java对象转换成json并响应
	@RequestMapping("/jsontest")
	@ResponseBody
	public Items jsonTest(@RequestBody Items items) {
		return items;
	}
	
	

	
}
