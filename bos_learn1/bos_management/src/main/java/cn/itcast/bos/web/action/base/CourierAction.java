package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.CDATA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.CourierService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class CourierAction extends BaseAction<Courier> {
	/*private int page;
	private int rows;*/
    private String ids;
	@Autowired
	private CourierService courierService;
	//进行快递员的保存与修改
	@Action(value = "courier_save", results = {
			@Result(name = "success", type = "redirect", location = "/pages/base/courier.html") })
	public String save() {
		courierService.save(model);
		return SUCCESS;
	}
	//批量删除快递员，只是对其状态进行改变
	@Action(value = "courier_delBatch", results = {
			@Result(name = "success", type = "redirect", location = "/pages/base/courier.html") })
     public String delBatch(){
		//对字符串进行切割
		courierService.delbatch(ids);
		
		return SUCCESS;
	}
	//批量还原快递员，只是对其状态进行改变
	@Action(value = "courier_doRestore", results = {
			@Result(name = "success", type = "redirect", location = "/pages/base/courier.html") })
	public String doRestore(){
		courierService.doRestore(ids);
		return SUCCESS;
	}
	//进行分页查询快递员信息，并且还提供有条件查询
	@Action(value = "courier_pageQuery", results = { @Result(name = "success", type = "json") })
	public String pageQuery() {
		Specification<Courier> specification = new Specification<Courier>() {

			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 将查询的条件对象封装到list集合中
				List<Predicate> list = new ArrayList<>();
				// 判断CourierNum参数是否为空
				// where courierNum=courier.getCourierNum().trim()
				if (StringUtils.isNotBlank(model.getCourierNum())) {
					// 进行快递员工号查询
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), model.getCourierNum().trim());
					list.add(p1);
				}
				// 进行公司的模糊查询,相当于company=?,此时后面的值为前台传过来的
				if (StringUtils.isNotBlank(model.getCompany())) {
					Predicate p2 = cb.like(root.get("company").as(String.class), "%" + model.getCompany() + "%");
					list.add(p2);
				}
				if (StringUtils.isNotBlank(model.getType())) {
					Predicate p3 = cb.like(root.get("type").as(String.class), "%" + model.getType() + "%");
					list.add(p3);
				}
				// 收派标准的多表联查，join连接查询
				// 多表查询 standardJoin==Root<Standard> t_courier c inner join
				// fetch t_standard s on c.standard=s.id
				if (model.getStandard() != null && StringUtils.isNotBlank(model.getStandard().getName())) {
					// 将courier与standard进行关联，其中的standard为courier中的成员属性名
					Join<Courier, Standard> standardRoot = root.join("standard", JoinType.INNER);
					Predicate p4 = cb.like(standardRoot.get("name").as(String.class),
							"%" + model.getStandard().getName() + "%");
					list.add(p4);
				}

				return cb.and(list.toArray(new Predicate[list.size()]));
			}
		};
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Courier> courierData = courierService.findData(specification, pageable);
		/*Map<String, Object> result = new HashMap<>();
		result.put("total", courierData.getTotalElements());
		result.put("rows", courierData.getContent());
		ActionContext.getContext().getValueStack().push(result);*/
		pushToValueStack(courierData);

		return SUCCESS;
	}
	@Action(value = "courier_findnoassociation", results = { @Result(name = "success", type = "json") })
	public String findnoAssociation(){
		List<Courier> couriers=courierService.findnoAssocition();
		ActionContext.getContext().getValueStack().push(couriers);
		return SUCCESS;
	}

/*	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}*/

	public void setIds(String ids) {
		this.ids = ids;
	}
    
}
