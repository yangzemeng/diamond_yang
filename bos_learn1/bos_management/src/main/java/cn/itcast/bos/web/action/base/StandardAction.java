package cn.itcast.bos.web.action.base;


import java.util.List;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;


import com.opensymphony.xwork2.util.ValueStack;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
import cn.itcast.bos.web.action.common.BaseAction;
@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class StandardAction extends BaseAction<Standard>{
   //名称也有影响，
	/*private int  page;
    private int  rows;*/
	
    @Autowired
    private StandardService standardservice;
	
	
	@Action(value="standard_save",results={@Result(name="success",type="redirect",location="/pages/base/standard.html")})
	public String save(){
		standardservice.save(model);
		return SUCCESS;
	}
	
	@Action(value="standard_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		Pageable pageAble=new PageRequest(page-1,rows);
		Page<Standard> pageData=standardservice.findPageData(pageAble);
		/*Map<String ,Object> map=new HashMap<>();
		map.put("total",pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		ValueStack valueStack = ActionContext.getContext().getValueStack();
		valueStack.push(map);*/
		pushToValueStack(pageData);
		return SUCCESS;
	}
	@Action(value="standard_findAll",results={@Result(name="success",type="json")})
    public String findAll(){
	List<Standard>	standards=standardservice.findAll();
	ValueStack valueStack = ActionContext.getContext().getValueStack();
	 valueStack.push(standards);
		return SUCCESS;
	}
	public void setStandardservice(StandardService standardservice) {
		this.standardservice = standardservice;
	}
	
	/*public void setPage(int  page) {
		this.page = page;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}*/


}
