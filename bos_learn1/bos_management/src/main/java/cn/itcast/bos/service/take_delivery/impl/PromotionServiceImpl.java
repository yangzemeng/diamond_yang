package cn.itcast.bos.service.take_delivery.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.PromotionDao;
import cn.itcast.bos.domain.page.PageBean;
import cn.itcast.bos.domain.take_delivery.Promotion;
import cn.itcast.bos.service.take_delivery.Promotionservice;
@Service
@Transactional
public class PromotionServiceImpl implements Promotionservice{
    @Autowired
    private PromotionDao promotionDao;

	@Override
	public void save(Promotion model) {
		promotionDao.save(model);
		
	}

	@Override
	public Page<Promotion> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return promotionDao.findAll(pageable);
	}

	@Override
	public PageBean<Promotion> findPageData(int page, int rows) {
		Pageable pageable=new PageRequest(page-1, rows);
		Page<Promotion> pagedata=promotionDao.findAll(pageable);
		PageBean<Promotion> pageBean=new PageBean<>();
		pageBean.setTotalCount(pagedata.getTotalElements());
		pageBean.setPageData(pagedata.getContent());
		
		return pageBean;
	}

	@Override
	public Promotion findById(Integer id) {
		// TODO Auto-generated method stub
		return promotionDao.findOne(id);
	}

	@Override
	public void updateStatus(Date date) {
		promotionDao.updateStatus(date);
		
	}
}
