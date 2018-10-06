package cn.itcast.bos.web.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.MenuService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class MenuAction extends BaseAction<Menu>{
	@Autowired
	private MenuService menuService;
    @Action(value="menu_list",results={@Result(name="success",type="json")})
	public String menulist(){
     List<Menu>	menus=menuService.findAll();
     ActionContext.getContext().getValueStack().push(menus);
		return SUCCESS;
	}
    @Action(value="menu_save",results={@Result(name="success",type="redirect",location="pages/system/menu.html")})
    public String menuSave(){
    	menuService.save(model);
    	return SUCCESS;
    }
    @Action(value="menu_showMenu",results={@Result(name="success",type="json")})
    public String showMenu(){
    	//根据当前登陆的用户查询对应角色和权限
	   Subject subject=SecurityUtils.getSubject();
	   //从subject之中取出user对象
	   User user=(User) subject.getPrincipal();
	   List<Menu> menus=menuService.findByuser(user);
	   ActionContext.getContext().getValueStack().push(menus);
    	return SUCCESS;
    }
    
}
