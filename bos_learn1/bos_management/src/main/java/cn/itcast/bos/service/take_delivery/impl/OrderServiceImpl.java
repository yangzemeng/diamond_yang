package cn.itcast.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaDao;
import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.dao.take_delivery.OrderDao;
import cn.itcast.bos.dao.take_delivery.WorkBillDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.constants.Constants;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.service.take_delivery.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderdao;
	@Autowired
	private FixedAreaDao fixedAreaDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private WorkBillDao workbillDao;
	@Autowired
	@Qualifier(value = "jmsQueueTemplate")
	private JmsTemplate jmsTemplate;

	@Override
	public void save(Order order) {
		// 设置一些订单之中的其他参数
		order.setOrderTime(new Date());
		order.setOrderNum(UUID.randomUUID().toString());
		order.setStatus("1");// 设置订单的状态
		// orderdao.save(order);
		// 通过订单信息得到省市区
		Area sendArea = order.getSendArea();
		// 得到区域的持久化对象
		Area persisArea = areaDao.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(),
				sendArea.getDistrict());
		Area recArea = order.getRecArea();
		// 得到区域的持久化对象
		Area persisrecArea = areaDao.findByProvinceAndCityAndDistrict(recArea.getProvince(), recArea.getCity(),
				recArea.getDistrict());
		order.setRecArea(persisrecArea);
		order.setSendArea(persisArea);
		System.out.println(order);
		// 利用webservice技术将传过来的订单地址到crm系统之中进行查询，看否有相匹配的地址
		String fixedAreaId = WebClient.create(Constants.CRM_MANAGEMENT_URL
				+ "/crm_management/services/customerService/customer/findFixedAreaIdByAddress?address="
				+ order.getSendAddress()).accept(MediaType.APPLICATION_JSON).get(String.class);
		if (fixedAreaId != null) {
			// 通过订单的地址查询到定区的id，在通过定区的id查找该关联到该定区的快递员，将快递员关联到订单上，然后生成工单
			FixedArea fixedArea = fixedAreaDao.findOne(fixedAreaId);
			Courier courier = fixedArea.getCouriers().iterator().next();//如果没有关联快递员，是否会报空指针异常
			if (courier != null) {
				System.out.println("分单成功");
				saveOrder(order, courier);
				// 生成对应的工单
				gernataterWorkBill(order);
				return;
			}
		}
		// 如果订单之中的地址不是完全匹配，就需要通过省市区，查询分区的关键字，匹配地址，基于分区实现自动分单

		for (SubArea subArea : persisArea.getSubareas()) {
			// 得到区域之中的每一个分区判断订单地址是否包含分区的关键字
			if (order.getSendAddress().contains(subArea.getKeyWords())) {
				// 通过分区找到对应的定区，找到快递员
				 Iterator<Courier> iterator = subArea.getFixedArea().getCouriers().iterator();
				 if(iterator.hasNext()){
					 Courier courier = iterator.next();
					 if (courier != null) {
							System.out.println("自动分单完成");
							saveOrder(order, courier);
							// 生成对应的工单
							gernataterWorkBill(order);
							return;
						}
				 }
				
			}
		}
		// 还可以通过订单地址是否包含分区的辅助字
		for (SubArea subArea : persisArea.getSubareas()) {
			// 得到区域之中的每一个分区判断订单地址是否包含分区的关键字
			if (order.getSendAddress().contains(subArea.getAssistKeyWords())) {
				// 通过分区找到对应的定区，找到快递员
				 Iterator<Courier> iterator = subArea.getFixedArea().getCouriers().iterator();
				 if(iterator.hasNext()){
					 Courier courier = iterator.next();
					 if (courier != null) {
							System.out.println("自动分单完成");
							saveOrder(order, courier);
							// 生成对应的工单
							gernataterWorkBill(order);
							return;
						}
				 }
			}
		}
		// 自动分单失败，进入人工分单
		order.setOrderType("2");
		orderdao.save(order);

	}

	// 生成工单的方法，并且还要发送短信
	private void gernataterWorkBill(final Order order) {
		WorkBill workBill = new WorkBill();
		workBill.setBuildtime(new Date());
		workBill.setCourier(order.getCourier());
		workBill.setOrder(order);
		workBill.setPickstate("新单");
		workBill.setRemark(order.getRemark());
		final String smsNumber = RandomStringUtils.randomNumeric(4);
		workBill.setSmsNumber(smsNumber);
		workBill.setType("新");
		workbillDao.save(workBill);
		// 生成工单，发送短信给快递员
		jmsTemplate.send("bos_sms", new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("telephone", order.getCourier().getTelephone());
				mapMessage.setString("msg", "短信序号：" + smsNumber + ",取件地址：" + order.getSendAddress() + ",联系人:"
						+ order.getSendName() + ",手机:" + order.getSendMobile() + "，快递员捎话：" + order.getSendMobileMsg());
				return mapMessage;
			}
		});
		// 短信发送完成，修改工单状态
		workBill.setPickstate("已通知");

	}

	private void saveOrder(Order order, Courier courier) {
		order.setCourier(courier);
		// 设置分单的状态
		order.setOrderType("1");
		// 订单之中的内容完成，保存订单到数据库
		orderdao.save(order);
	}

	@Override
	public Order findByOrderNum(String orderNum) {
		// TODO Auto-generated method stub
		return orderdao.findByOrderNum(orderNum);
	}

}
