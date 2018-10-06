package cn.itcast.bos.service.base.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
@Service
@Transactional
public class CourierServiceimpl implements CourierService{
    @Autowired
	private CourierDao courierDao;

	@Override
	@RequiresPermissions(value="courier:add")
	public void save(Courier courier) {
		courierDao.save(courier);
		
	}



	@Override
	public Page<Courier> findData(Specification<Courier> specification, Pageable pageable) {
		// TODO Auto-generated method stub
		return courierDao.findAll(specification,pageable);
	}



	@Override
	public void delbatch(String ids) {
		String[] split = ids.split(",");
		for(int i=0;i<split.length;i++){
			if(StringUtils.isNotBlank(split[i])){
				int id = Integer.parseInt(split[i]);
				courierDao.update(id);
			}
		}
		
	}



	@Override
	public void doRestore(String ids) {
		String[] split = ids.split(",");
		for(int i=0;i<split.length;i++){
			int id = Integer.parseInt(split[i]);
			courierDao.doRestore(id);
		}
		
	}



	@Override
	public List<Courier> findnoAssocition() {
		Specification<Courier> specification=new Specification<Courier>() {

			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.isEmpty(root.get("fixedAreas").as(Set.class));
				return predicate;
			}
		};
		return courierDao.findAll(specification);
	}



}
