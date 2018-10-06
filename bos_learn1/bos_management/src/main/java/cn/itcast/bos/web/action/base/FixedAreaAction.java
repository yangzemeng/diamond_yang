package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Controller;

import com.itheima.crm.domain.Customer;
import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea>{
	private String courierId;//将快递员关联到定区所需的参数
	private String takeTimeId;//将工作时间填入到快递员列表
	private String[] customerIds;//定区关联客户所需要的参数
	@Autowired
	private FixedAreaService fixedAreaService;
	@Action(value="fixed_area",results={@Result(name="success",type="redirect",location="pages/base/fixed_area.html")})
	public String  save(){
		fixedAreaService.save(model);
		
		return SUCCESS;
	}
	@Action(value = "fixedArea_pageQuery", results = { @Result(name = "success", type = "json") })
	public String pageQuery() {
		Pageable pageable=new PageRequest(page-1, rows);
        Specification<FixedArea> specification=new Specification<FixedArea>() {
            
			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list=new ArrayList<>();
				if(StringUtils.isNotBlank(model.getId())){
					Predicate p1 = cb.like(root.get("id").as(String.class),"%"+model.getId()+"%");
				list.add(p1);
				}
				if(StringUtils.isNotBlank(model.getCompany())){
					Predicate p2 = cb.like(root.get("company").as(String.class),"%"+model.getCompany()+"%");
				list.add(p2);
				}
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
			
		};
		Page<FixedArea> pagedata=fixedAreaService.findData(specification,pageable);
		pushToValueStack(pagedata);
		return SUCCESS;
	}
	//查询未关联的客户
	@Action(value = "fixedArea_findNoAssociationCustomer", results = { @Result(name = "success", type = "json") })
    public String findNoAssociationCustomer(){
		Collection<? extends Customer> collection = WebClient.create("http://localhost:9002/crm_management/services/customerService/noassociationcustomers")
		.accept(MediaType.APPLICATION_JSON_TYPE).getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(collection);
		return SUCCESS;
	}
	//查询已经关联的客户
	@Action(value = "fixedArea_findHasAssociationCustomer", results = { @Result(name = "success", type = "json") })
    public String findHasAssociationCustomer(){
		Collection<? extends Customer> collection = WebClient.create("http://localhost:9002/crm_management/services/customerService/associationfixedareacustomers/"+model.getId())
		.accept(MediaType.APPLICATION_JSON_TYPE).getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(collection);
		System.out.println(model.getId());
		return SUCCESS;
	}
	@Action(value="decidedzone_assigncustomerstodecidedzone",results={@Result(name="success",type="redirect",location="pages/base/fixed_area.html")})
	public String associationCustomerToFixedArea(){
		String customerIdStr = StringUtils.join(customerIds,",");
		WebClient.create("http://localhost:9002/crm_management/services/customerService/associationcustomerstofixedarea?customerIdStr="+customerIdStr+"&fixedAreaId="+model.getId())
		.put(null);
		return SUCCESS;
	}
	@Action(value="fixedArea_associationCourierToFixedArea",results={@Result(name="success",type="redirect",location="pages/base/fixed_area.html")})
	public String associationCourierToFixedArea(){
		fixedAreaService.associationCourierToFixedArea(model,courierId,takeTimeId);
		
		return SUCCESS;
	}
	
	
	
	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}
	public void setCourierId(String courierId) {
		this.courierId = courierId;
	}
	public void setTakeTimeId(String takeTimeId) {
		this.takeTimeId = takeTimeId;
	}
	
	
}
