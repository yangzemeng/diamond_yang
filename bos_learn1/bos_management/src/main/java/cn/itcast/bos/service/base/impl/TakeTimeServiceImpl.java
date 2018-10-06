package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.TakeTimeDao;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.TakeTimeService;
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService{
    @Autowired
	private TakeTimeDao takeTimeDao;

	@Override
	public List<TakeTime> findAll() {
		// TODO Auto-generated method stub
		return takeTimeDao.findAll();
	}

	@Override
	public Page<TakeTime> Querypage(Pageable pageable) {
		// TODO Auto-generated method stub
		return takeTimeDao.findAll(pageable);
	}
}
