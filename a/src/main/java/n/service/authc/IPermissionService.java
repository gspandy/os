package n.service.authc;

import java.util.List;
import java.util.Set;

import n.core.service.support.IGenericService;
import n.entity.authc.Permission;
import n.table.dto.authc.PermissionTreeDTO;

/**
 * 权限服务层
 * 
 * @author jtwise
 * @date 2016年7月19日 上午11:08:46
 * @verion 1.0
 */
public interface IPermissionService extends IGenericService<Permission, Integer> {

	Permission findByPath(String path);
	
	public Set<String> findUserPermissionByUID(Long userId);
	/**
	 * 通过角色查找对应权限
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<Permission> findByRoleId(Integer roleId) throws Exception;

	/**
	 * 通过用户id查找对应权限
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Permission> findByUserId(Long userId) throws Exception;
	/**
	 * 创建角色-权限关系
	 * @param roleId
	 * @param permissionId
	 * @return
	 * @throws Exception
	 */
	public boolean insertRolePermission(Integer roleId, Integer permissionId) throws Exception;
	/**
	 * 删除角色-权限关系
	 * @param roleId
	 * @param permissionId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteRolePermission(Integer roleId, Integer permissionId) throws Exception;
	public boolean deleteByRoleId(Integer roleId) throws Exception;
	
	/**
	 * 权限分配
	 * @param roleId
	 * @param permissions
	 * @return
	 * @throws Exception
	 */
	public void permissionSave(Integer roleId, String permissions) throws Exception;

	public Permission findByCode(String code);
	 
	/**
	 * 权限树
	 */
	public List<PermissionTreeDTO> permissionTree();
	/**
	 * code集合查询
	 * @param codes
	 * @return
	 */
	public List<Permission> findByCodeIn(List<String> codes);
	/**
	 * 查询所有该节点下的子权限
	 * @param id
	 * @return
	 */
	public List<Permission> findBySpec(Integer id);
	
}
