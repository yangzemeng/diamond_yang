package cn.itcast.bos.web.action.transit;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.transit.TransitInfo;
import cn.itcast.bos.service.transit.TransitService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class TransitAction extends BaseAction<TransitInfo> {
    private String waybillids;
    @Autowired
    private TransitService transitService;
	@Action(value = "transit_creat", results = { @Result(name = "success", type = "json") })
	public String creat() {
		Map<String, Object> result=new HashMap<>();
		try {
			transitService.creat(waybillids);
			result.put("success", true);
			result.put("msg", "开启中转配送");
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "中专配送失败:"+e.getMessage());
		}
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}
	public void setWaybillids(String waybillids) {
		this.waybillids = waybillids;
	}
	
}
