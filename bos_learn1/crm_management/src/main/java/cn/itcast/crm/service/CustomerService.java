package cn.itcast.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import cn.itcast.crm.domain.Customer;

/**
 * 客户操作
 * 
 * @author itcast
 *
 */
public interface CustomerService {

	// 查询所有未关联客户列表
	@Path("/noassociationcustomers")
	@GET
	@Produces({ "application/xml", "application/json" })
	public List<Customer> findNoAssociationCustomers();

	// 已经关联到指定定区的客户列表
	@Path("/associationfixedareacustomers/{fixedareaid}")
	@GET
	@Produces({ "application/xml", "application/json" })
	public List<Customer> findHasAssociationFixedAreaCustomers(
			@PathParam("fixedareaid") String fixedAreaId);

	// 将客户关联到定区上 ， 将所有客户id 拼成字符串 1,2,3
	@Path("/associationcustomerstofixedarea")
	@PUT
	public void associationCustomersToFixedArea(
			@QueryParam("customerIdStr") String customerIdStr,
			@QueryParam("fixedAreaId") String fixedAreaId);

	@Path("/customer")
	@POST
	@Consumes({ "application/xml", "application/json" })
	public void regist(Customer customer);
	/*
	 * @path("/coustomer")
	 * @post
	 * @consumes({"application/xml", "application/json" })
	 * 
	 */
	
	
	@Path("/customer/telephone/{telephone}")
	@GET
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Customer findByTelephone(@PathParam("telephone") String telephone);
    
	@Path("/customer/updatetype/{telephone}")
	@GET
	public void updateType(@PathParam("telephone") String telephone);
	//http://localhost:9002/crm_management/services/customerService/customer/login?
	@Path("/customer/login")
	@GET
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json" })
	Customer findByTelephoneAndPassword(@QueryParam("telephone") String telephone,
			@QueryParam("password") String password);
    
	//根据订单的地址查询定区id
	@Path("/customer/findFixedAreaIdByAddress")
	@GET
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json" })
	String findFixedAreaIdByAddress(@QueryParam("address") String address);

}
