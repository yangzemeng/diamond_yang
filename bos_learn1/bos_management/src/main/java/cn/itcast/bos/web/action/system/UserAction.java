package cn.itcast.bos.web.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.UserService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class UserAction extends BaseAction<User>{
	@Autowired
	private UserService userService;
	private String[] roleIds;
	@Action(value="user_login",results={@Result(name="success",type="redirect",location="index.html"),
			@Result(name="login",type="redirect",location="login.html")})
	public String userLogin(){
		//用户名和密码都封装在model之中，现在需要基于shiro登陆
		Subject subject=SecurityUtils.getSubject();
		//将用户名和密码存储在token之中
		AuthenticationToken token=new UsernamePasswordToken(model.getUsername(), model.getPassword());
		try {
			//基于shiro进行登陆
			subject.login(token);
			//登陆成功将用户保存到session之中可以实现自动登陆功能
			return SUCCESS;
		} catch (Exception e) {
			//登陆失败
			e.printStackTrace();
			return LOGIN;
			
		}
		
	}
	@Action(value="user_logout",results={@Result(name="success",type="redirect",location="login.html")})
	public String logout(){
		Subject subject=SecurityUtils.getSubject();
		subject.logout();
		return SUCCESS;
	}	
    @Action(value="user_list",results={@Result(name="success",type="json")})
    public String userQuery(){
    	List<User> users=userService.findAll();
    	ActionContext.getContext().getValueStack().push(users);
    	return SUCCESS;
    }
    //保存操作
    @Action(value="user_save",results={@Result(name="success",type="redirect",location="pages/system/userlist.html")})
    public String usersave(){
    	userService.save(model,roleIds);
    	return SUCCESS;
    }
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
    

}
