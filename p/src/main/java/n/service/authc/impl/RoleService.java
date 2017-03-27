package n.service.authc.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.authc.Role;
import n.entity.authc.Role.RoleType;
import n.repository.authc.IRoleRepository;
import n.service.authc.IRoleService;

/**
 * 角色
 * @author
 * @version 1.0 2015-04-27
 * 
 */

@Service
public class RoleService extends GenericService<Role, Integer>
		implements IRoleService {
	
	public RoleService() {
		super(Role.class);
	}

	@Autowired
	public IRoleRepository repository;

	@Override
	protected GenericRepository<Role, Integer> getRepository() {
		return repository;
	}

	@Override
	public List<String> findAllRoleName() {
		return repository.findAllRoleName();
	}

	@Override
	public List<Role> findByUserId(Long userId) throws Exception{
		return repository.findByUserId(userId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean insertUserRole(Long userId, Integer roleId) throws Exception {
		return repository.insertUserRole(userId, roleId) == 0 ? false : true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean deleteUserRole(Long userId, Integer roleId) throws Exception {
		if(null == roleId){
			return repository.deleteUserRole(userId) == 0 ? false : true;
		}
		return repository.deleteUserRole(userId, roleId) == 0 ? false : true;
	}

	@Override
	public Set<String> findUserRolesByID(Long uid) {
		return repository.findUserRolesByUserId(uid);
	}

	@Override
	public Role findByRoleName(String roleName) {
		return repository.findByRoleName(roleName);
	}

	@Override
	public List<Role> findByRoleType(RoleType roleType) {
		return repository.findByRoleType(roleType);
	}

	@Override
	public List<Role> findByRoleNameIn(List<String> names) {
		return repository.findByRoleNameIn(names);
	}
	 
}
