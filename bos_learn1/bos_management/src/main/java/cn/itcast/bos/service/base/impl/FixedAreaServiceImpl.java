package cn.itcast.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.dao.base.TakeTimeDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedAreaService;
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService{
    @Autowired
	private FixedAreaDao fixedAreaDao;
    @Autowired
    private CourierDao courierDao;
    @Autowired
    private TakeTimeDao takeTimedao;

	@Override
	public void save(FixedArea model) {
		fixedAreaDao.save(model);
		
	}

	@Override
	public Page<FixedArea> findData(Specification<FixedArea> specification, Pageable pageable) {
		// TODO Auto-generated method stub
		return fixedAreaDao.findAll(specification, pageable);
	}

	@Override
	public void associationCourierToFixedArea(FixedArea model, String courierId, String takeTimeId) {
		//通过快递员的id查出快递员
		Courier courier = courierDao.findOne(Integer.parseInt(courierId));
		FixedArea fixedArea = fixedAreaDao.findOne(model.getId());
		fixedArea.getCouriers().add(courier);//定区关联客户
		TakeTime takeTime = takeTimedao.findOne(Integer.parseInt(takeTimeId));
		courier.setTakeTime(takeTime);
	}
}
