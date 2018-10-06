package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuDao;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.MenuService;
@Service
@Transactional
public class MenuServiceImpl implements MenuService{
    @Autowired
    private MenuDao menuDao;
	@Override
	public List<Menu> findAll() {
		// TODO Auto-generated method stub
		return menuDao.findAll();
	}
	@Override
	public void save(Menu model) {
		//避免父菜单为空，明天和威威讨论
		if(model.getParentMenu()!=null && model.getParentMenu().getId()==0){
			model.setParentMenu(null);
		}
		menuDao.save(model);
	}
	@Override
	public List<Menu> findByuser(User user) {
		if(user.getUsername().equals("admin")){
			return menuDao.findAll();
		}
		return menuDao.findByUser(user.getId());
	}

}
