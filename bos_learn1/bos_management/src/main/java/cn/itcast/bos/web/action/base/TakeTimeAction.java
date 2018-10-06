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

import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.TakeTimeService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class TakeTimeAction extends BaseAction<TakeTime>{
	@Autowired
	private TakeTimeService takeTimeService;
	@Action(value = "takeTime_findAll", results = { @Result(name = "success", type = "json") })
    public String takeTimeFindall(){
		List<TakeTime> list=takeTimeService.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}
	@Action(value = "takeTime_Querypage", results = { @Result(name = "success", type = "json") })
    public String takeTime_Querypage(){
		Pageable pageable=new PageRequest(page-1, rows);
		Page<TakeTime> pageData=takeTimeService.Querypage(pageable);
			
		pushToValueStack(pageData);
		return SUCCESS;
	}
}
