package bos_fore.action;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.constants.Constants;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.crm.domain.Customer;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order>{
	private String sendAreaInfo;
	private String recAreaInfo;
	@Action(value="order_add",results={@Result(name="success",type="redirect",location="index.html")})
	public String saveOrder(){
		//进行寄件人省市区的封装，可以进行字符串的判断，但是个人觉得没有多大必要
		Area sendArea=new Area();
		String[] send = sendAreaInfo.split("/");
		sendArea.setProvince(send[0]);
		sendArea.setCity(send[1]);
		sendArea.setDistrict(send[2]);
		//进行收件人省市区的封装
		Area recArea=new Area();
		String[] rec = recAreaInfo.split("/");
		recArea.setProvince(rec[0]);
		recArea.setCity(rec[1]);
		recArea.setDistrict(rec[2]);
		model.setSendArea(sendArea);
		model.setRecArea(recArea);
		//得到当前登录的客户信息
	 Customer customer=	(Customer) ServletActionContext.getRequest().getSession().getAttribute("customer");
		//将客户的id封装到订单中
	  model.setCustomer_id(customer.getId());
	 //保存订单到bos系统之中
		WebClient.create(Constants.BOS_MANAGEMENT_URL
				+ "/bos_management/services/orderService/order").type(MediaType.APPLICATION_JSON).post(model);
		System.out.println(model.getRecCompany()+"=="+sendAreaInfo);
		return SUCCESS;
	}
	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}
	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}

}
