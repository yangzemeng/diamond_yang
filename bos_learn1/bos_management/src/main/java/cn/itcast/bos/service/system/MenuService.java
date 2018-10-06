package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;

public interface MenuService {

	List<Menu> findAll();

	void save(Menu model);

	List<Menu> findByuser(User user);

}
