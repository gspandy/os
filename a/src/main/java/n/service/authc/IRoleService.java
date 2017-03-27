package n.service.authc;

import java.util.List;
import java.util.Set;

import n.core.service.support.IGenericService;
import n.entity.authc.Role;
import n.entity.authc.Role.RoleType;

/**
 * 会员服务
 * 
 * @author jtwise
 * @date 2016年7月19日 上午11:08:46
 * @verion 1.0
 */
public interface IRoleService extends IGenericService<Role, Integer> {

	public Role findByRoleName(String roleName);
	
	public List<Role> findByRoleType(RoleType roleType);
	
	public List<Role> findByRoleNameIn(List<String> names);
	
	public List<String> findAllRoleName();
	
	public Set<String> findUserRolesByID(Long uid);
 	/**
	 * 通过userid查询角色信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Role> findByUserId(Long userId) throws Exception;
	/**
	 * 创建用户-角色关系
	 * @param userId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public boolean insertUserRole(Long userId, Integer roleId) throws Exception;
	/**
	 * 删除用户-角色关系
	 * @param userId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteUserRole(Long userId, Integer roleId) throws Exception;
}
