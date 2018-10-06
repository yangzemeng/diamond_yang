package cn.itcast.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.PermissionService;
import cn.itcast.bos.service.system.RoleService;
import cn.itcast.bos.service.system.UserService;
//@Service(value="bosRealm")
public class BosRealm extends AuthorizingRealm{
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("权限数据管理");
	     //用户权限校验的地方
		SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
		//根据当前登陆的用户查询对应角色和权限
		Subject subject=SecurityUtils.getSubject();
		//从subject之中取出user对象
		User user=(User) subject.getPrincipal();
		//调用业务层，查询角色
		List<Role> roles=roleService.findByUser(user);
		for (Role role : roles) {
			authorizationInfo.addRole(role.getKeyword());
		}
		//调用业务层，查询权限
		List<Permission> permissions=permissionService.findByUser(user);
		for (Permission permission : permissions) {
			authorizationInfo.addStringPermission(permission.getKeyword());
		}
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//shiro认证管理
		//将token进行转换
		System.out.println("权限数据管理");
		UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken) token;
		//根据用户名查询用户信息
		
		User user=userService.findByUsername(usernamePasswordToken.getUsername());
		
		if(user==null){
			return null;
		}else{
			//第三个参数为realm的名称
			//用户名存在，当返回的时候如果密码和用户输入的一致，用户登陆成功，如果不一致说明密码错误
			return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
		}
		
	}

}
