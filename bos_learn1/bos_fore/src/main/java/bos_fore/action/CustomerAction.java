package bos_fore.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionContext;
import bos_fore.utils.MailUtils;
import cn.itcast.bos.domain.constants.Constants;
import cn.itcast.crm.domain.Customer;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
//发送验证码到手机
public class CustomerAction extends BaseAction<Customer>{
	@Autowired
	private RedisTemplate<String, String> redistemplate;
	@Autowired
	@Qualifier(value="jmsQueueTemplate")
	private JmsTemplate jmsTemplate;
	//向新用户发送验证码
	@Action(value="customer_sendCheckCode")
	public String sendCheckCode() throws IOException, ClientException{
		final String checkCode = RandomStringUtils.randomNumeric(6);
		
		ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(), checkCode);
		//调用sms服务发送短信
		//String sendMsg = ALiYunMsgUtils.sendMsg(model.getTelephone(), checkCode);
		//System.out.println(sendMsg);
		jmsTemplate.send("bos_sms", new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage=session.createMapMessage();
				mapMessage.setString("telephone", model.getTelephone());
				mapMessage.setString("msg", checkCode);
				return mapMessage;
			}
		});
		//对异常进行捕获，如果activeMQ宕机，自己发送消息给客户
		System.out.println(checkCode);
		return NONE;
	}
	//进行短信验证，验证成功进行数据的插入，并发送邮件到邮箱，并将激活码存入到redis中
	private String checkCode;
	@Action(value="Customer_regist",results={@Result(name="success",type="redirect",location="signup-success.html")
	,@Result(name="input",type="redirect",location="signup.html")})
	public String customerRegist(){
		//先进行短信验证，如果不通过就跳回注册页面
		String code=(String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
		if(checkCode ==null || ! checkCode.equals(code)){
			return INPUT;
		}else{
			//调用webservice，将数据存入到数据库
			WebClient.create("http://localhost:9002/crm_management/services/customerService/customer").type(MediaType.APPLICATION_JSON).post(model);
			System.out.println("注册成功");
			//生成激活码
			String active=RandomStringUtils.randomNumeric(32);
			//发送邮件，并将数据存入到redis
			String content="尊敬的用户请在24小时内点击以下链接进行邮箱账户绑定<br/><a href='"+MailUtils.activeUrl+"?telephone="+model.getTelephone()+"&activecode="+active+"'>顺丰快递邮箱地址绑定</a>";
			MailUtils.sendMail("顺丰快递激活码", content, model.getEmail());
			redistemplate.opsForValue().set(model.getTelephone(), active, 24,TimeUnit.HOURS);
			return SUCCESS;
		}
		
	}
	//手机号的校验
	@Action(value="customer_TestTelephone",results={@Result(name="success",type="json")})
	public String testTelephone(){
		Customer customer = WebClient.create("http://localhost:9002/crm_management/services/customerService/customer/telephone/"+model.getTelephone()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
		Map<String,String> result=new HashMap<>();
		if (customer == null) {
			result.put("telephoneMsg", "1");//代表手机号尚未使用
			
		}else{
			result.put("telephoneMsg", "0");//代表手机号已经被使用
		}
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}
	private String activecode;
	
	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}
	//进行邮箱绑定激活
	@Action(value="customer_mailActive")
	public String activeMail() throws IOException{
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		//通过判断resis中的activecode与链接传过来的是否相等
		String redisActive = redistemplate.opsForValue().get(model.getTelephone());
		if(redisActive==null || !redisActive.equals(activecode)){
			//还要进行判断，有可能已经激活过，导致激活码被删除
			//不相等的时候
			ServletActionContext.getResponse().getWriter().println("激活码无效，请重新进行绑定");
			//跳转到一个页面
		}else{
			//如果相等，为防止客户重复绑定，通过type来判断是否进行过绑定
			Customer customer = WebClient.create("http://localhost:9002/crm_management/services/customerService/customer/telephone/"+model.getTelephone()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
		    //查询到内容的时候，一定要判断内容是否为空，否则后面对对象进行操作的时候有可能报空指针异常
			if(customer !=null && (customer.getType()==null || customer.getType()!=1)){
		    	WebClient.create("http://localhost:9002/crm_management/services/customerService/customer/updatetype/"+model.getTelephone()).get();
		    	ServletActionContext.getResponse().getWriter().println("邮箱绑定成功");
		    }else{
		    	ServletActionContext.getResponse().getWriter().println("邮箱已经绑定，请勿重复绑定");
		    	//跳转到首页
		    }
			//将redis的内容进行清空
		    redistemplate.delete(model.getTelephone());
		}
		
		return NONE;
	}
	@Action(value="customer_login",results={@Result(name="success",type="redirect",location="index.html#/myhome"),
			@Result(name="login",type="redirect",location="login.html")})
	public String login(){
		Customer customer =WebClient.create(Constants.CRM_MANAGEMENT_URL+"/crm_management/services/customerService/customer/login?telephone="+model.getTelephone()+"&password="+model.getPassword()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
		
		if(customer==null){
			return "login";
		}
		//将客户信息保存到session中
		ServletActionContext.getRequest().getSession().setAttribute("customer", customer);
		return SUCCESS;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
}
