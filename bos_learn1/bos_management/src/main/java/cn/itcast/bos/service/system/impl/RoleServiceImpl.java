package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuDao;
import cn.itcast.bos.dao.system.PermissionDao;
import cn.itcast.bos.dao.system.RoleDao;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;

import cn.itcast.bos.service.system.RoleService;
@Service
@Transactional
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private MenuDao menuDao;
	@Override
	public List<Role> findByUser(User user) {
		if(user.getUsername().equals("admin")){
			return roleDao.findAll();
		}else{
			return roleDao.findByUser(user.getId());
		}
		
	}
	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return roleDao.findAll();
	}
	@Override
	public void save(Role model, String[] permissionIds, String menuIds) {
		roleDao.save(model);
		//添加权限到角色
		if(permissionIds != null){
			for (String string : permissionIds) {
				Permission permission = permissionDao.findOne(Integer.parseInt(string));
				model.getPermissions().add(permission);
			}
		}
		if(StringUtils.isNoneBlank(menuIds)){
			String[] split = menuIds.split(",");
			for (String string : split) {
				Menu menu = menuDao.findOne(Integer.parseInt(string));
				model.getMenus().add(menu);
			}
		}
		
	}

}
