package com.hitler.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.hitler.entity.authc.Role;
import com.hitler.entity.authc.Role.RoleType;
import com.hitler.entity.authc.User;
import com.hitler.service.authc.IRoleService;
import com.hitler.service.authc.IUserService;
import com.hitler.web.test.BaseTest;

public class RoleServiceTest extends BaseTest {

	@Resource
	private IRoleService roleService;
	@Resource
	private IUserService userService;

	/**
	 * 初始化角色表数据
	 * @throws Exception
	 */
	@Test
	public void saveTest() throws Exception {
		if(userService.findByAccount("admin")!=null && roleService.findByRoleName("administrator") == null && roleService.findByRoleName("companyAdmin") == null && roleService.findByRoleName("services") == null &&roleService.findByRoleName("normal") == null){
			Role role = new Role();
			Role role1 = new Role();
			Role role2 = new Role();
			Role role3 = new Role();
			role.setRoleName("administrator");
			role.setRoleType(RoleType.sysdefault);
			role1.setRoleName("companyAdmin");
			role1.setRoleType(RoleType.sysdefault);
			role2.setRoleName("services");
			role2.setRoleType(RoleType.sysdefault);
			role3.setRoleName("normal");
			role3.setRoleType(RoleType.sysdefault);
			List<Role> ls = new LinkedList<>();
			ls.add(role);
			ls.add(role1);
			ls.add(role2);
			ls.add(role3);
			User user = userService.findByAccount("admin");
			for(Role dto:ls){
				dto = roleService.save(dto);
				roleService.insertUserRole(user.getId(), dto.getId());
			}
			System.err.println("初始化角色数据结束");
		}
	}
	
	
	@Test
	public void insertRoleUserTest() throws Exception{
//		User user = userService.findByAccount("admin");
		roleService.insertUserRole(2L, 3);
//		roleService.deleteUserRole(1L, 1);
	}

	@Test
	public void findAllRoleName() {
//		List<String> result = roleService.findAllRoleName();
//		for (String name : result) {
//			log.info("##"+name);
//		}
		List<Role> list = roleService.findAll();
		for (Role role : list) {
			System.err.println(role.getId()+""+role.getPermissions());
		}
	}

	@Test
	public void findRolesTest() throws Exception {
		List<Role> roles = roleService.findByUserId(1L);
		for (Role r : roles) {
			System.err.println(r.getRoleName()+" type:"+r.getRoleType());
		}
		Set<String> rolesset = roleService.findUserRolesByID(1L);
		System.err.println(rolesset);
	}
	
	@Test
	public void findPermission(){
//		Role role = roleService.find(1);
//		List<Permission> list = role.getPermissions();
//		for (Permission p : list) {
//			System.err.println(p.getPath());
//		}
	}
	
}
