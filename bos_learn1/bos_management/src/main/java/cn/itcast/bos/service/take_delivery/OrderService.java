package cn.itcast.bos.service.take_delivery;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import cn.itcast.bos.domain.take_delivery.Order;

public interface OrderService {
	@Path("/order")
	@POST
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json" })
	void save(Order order);

	Order findByOrderNum(String orderNum);
}
