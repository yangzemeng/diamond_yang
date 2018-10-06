package cn.itcast.bos.web.action.take_delivery;


import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.service.take_delivery.WayBillService;
import cn.itcast.bos.web.action.common.BaseAction;
@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill>{
	@Autowired
	private WayBillService wayBillService;
	//打印日志相关的内容
	private static final Logger LOGGER=Logger.getLogger(WayBill.class);
	@Action(value="waybill_save",results={@Result(name="success",type="json")})
	public String saveWayBill(){
		Map<String, Object> map=new HashMap<>();
		//去除没有id的order对象
		if(model.getOrder() != null && (model.getOrder().getId()==null ||model.getOrder().getId()==0)){
			model.setOrder(null);
		}
		try {
			wayBillService.save(model);
			map.put("success", true);
			map.put("msg", "保存成功");
			LOGGER.info("保存订单成功,订单号为："+model.getWayBillNum());
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "保存运单失败");
			LOGGER.info("保存订单失败,订单号为："+model.getWayBillNum());
		}
		
		//将数据压入值站
		ActionContext.getContext().getValueStack().push(map);
		return SUCCESS;
	}
	@Action(value="wayBill_PageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		//按降序排列
		Pageable pageable=new PageRequest(page-1, rows,new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
		Page<WayBill> pageData=wayBillService.findAll(model,pageable);
		pushToValueStack(pageData);
		return SUCCESS;
	}
	//运单快速录入的回显
	@Action(value="wayBill_findBywayBillNum",results={@Result(name="success",type="json")})
	public String findBywayBillNum(){
		//查询出快速录入的运单信息，进行数据回显
		WayBill wayBill=wayBillService.findBywayBillNum(model.getWayBillNum());
		Map<String, Object> result=new HashMap<>();
		if(wayBill==null){
			result.put("success", false);
		}else{
			result.put("success", true);
			result.put("wayBillData", wayBill);
		}
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}
}
