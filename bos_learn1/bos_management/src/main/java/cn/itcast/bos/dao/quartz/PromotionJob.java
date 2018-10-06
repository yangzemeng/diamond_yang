package cn.itcast.bos.dao.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.service.take_delivery.Promotionservice;
import cn.itcast.bos.service.take_delivery.WayBillService;

public class PromotionJob implements Job{
	@Autowired
    private Promotionservice promotionService;
	@Autowired
	private WayBillService waybillService;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("活动过期");
		promotionService.updateStatus(new Date());
		waybillService.syncIndex();
	}

}
