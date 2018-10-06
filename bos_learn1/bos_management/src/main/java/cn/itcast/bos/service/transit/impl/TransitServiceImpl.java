package cn.itcast.bos.service.transit.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.WayBillDao;
import cn.itcast.bos.dao.transit.TransitDao;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.domain.transit.TransitInfo;
import cn.itcast.bos.service.transit.TransitService;

@Service
@Transactional
public class TransitServiceImpl implements TransitService{
    @Autowired
    private TransitDao transitDao;
    @Autowired
    private WayBillDao wayBillDao;
	@Override
	public void creat(String waybillids) {
		//从数据库查询运单信息，储存到中转运输单之中
		if(StringUtils.isNoneBlank(waybillids)){
			String[] waybill = waybillids.split(",");
			
			for (String string : waybill) {
				WayBill bill = wayBillDao.findOne(Integer.parseInt(string));
				//当运单状态为待发货的时候，进行中转业务
				if(bill.getSignStatus()==1){
					TransitInfo transitInfo=new TransitInfo();
					transitInfo.setWayBill(bill);
					transitInfo.setStatus("出入库中转中");
					transitDao.save(transitInfo);
				}
				 bill.setSignStatus(2);
			}
		}
		
	}

}
