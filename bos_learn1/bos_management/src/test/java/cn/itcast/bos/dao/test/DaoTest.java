package cn.itcast.bos.dao.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bos.dao.base.StandardDao;
import cn.itcast.bos.service.take_delivery.WayBillService;

@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class DaoTest {
	    @Autowired
        private StandardDao standardDao;
	    @Autowired
	    private WayBillService waybill;
	    @Test
	    public void demo(){
	    	waybill.syncIndex();
	    
	    }
	    
}
