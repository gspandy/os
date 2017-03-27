package n.repository.authc;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import n.core.repository.support.GenericRepository;
import n.entity.authc.Permission;

/**
 * 角色
 * 
 * @author onsoul 2015-6-17 下午10:24:55
 */
public interface IPermissionRepository extends GenericRepository<Permission, Integer> {

	Permission findByPath(String path);
	/**
	 * userId查找对应权限code
	 * @param userId
	 * @return
	 */
	@Query(value = "select p.code from TB_PERMISSION p  inner join TB_ROLE_TB_PERMISSION rp on p.id = rp.permissions_id  inner join ("
			+ "	select ur.roles_id from TB_ROLE r, TB_USER_TB_ROLE ur where r.id = ur.roles_id and ur.TB_USER_id = :userId) roles "
			+ "on rp.TB_ROLE_id = roles.roles_id GROUP BY p.id", nativeQuery = true)
	public Set<String> findUserPermissionByUID(@Param("userId")Long userId);
	/**
	 * userId查找对应权限
	 * @param userId
	 * @return
	 */
//	@Query(value = "select p.* from TB_PERMISSION p  inner join TB_ROLE_TB_PERMISSION rp on p.id = rp.permissions_id  inner join ("
//			+ "	select ur.roles_id from TB_ROLE r, TB_USER_TB_ROLE ur where r.id = ur.roles_id and ur.TB_USER_id = :userId) roles "
//			+ "on rp.TB_ROLE_id = roles.roles_id GROUP BY p.id", nativeQuery = true)
	@Query(value = "select DISTINCT p.* from tb_permission p "
		+ " left join tb_role_tb_permission rp on rp.permissions_id = p.id " 
		+ " left join tb_user_tb_role ur on ur.roles_id = rp.TB_ROLE_id "
		+ " where ur.TB_USER_id = :userId ", nativeQuery = true)
	public List<Permission> findByUserId(@Param("userId") Long userId);
	
	/**
	 * roleId查找对应权限
	 * @param roleId
	 * @return
	 */
//	@Query("from Permission p , RolePermission rp where p.id = rp.permissionId and rp.roleId = :roleId")
	@Query(value = "select * from TB_PERMISSION p, TB_ROLE_TB_PERMISSION rp where p.id = rp.permissions_id and rp.TB_ROLE_id = :roleId", nativeQuery = true)
	public List<Permission> findByRoleId(@Param("roleId")Integer roleId);
	/**
	 * 插入RolePermission
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	@Modifying
	@Query(value = "insert into TB_ROLE_TB_PERMISSION(TB_ROLE_id,permissions_id) values(:roleId, :permissionId)", nativeQuery = true)
	public int insertRolePermission(@Param("roleId")Integer roleId, @Param("permissionId") Integer permissionId);
	/**
	 * 删除RolePermission
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	@Modifying
	@Query(value = "delete from TB_ROLE_TB_PERMISSION where TB_ROLE_id = :roleId and permissions_id = :permissionId", nativeQuery = true)
	public int deleteRolePermission(@Param("roleId")Integer roleId, @Param("permissionId") Integer permissionId);
	
	Permission findByCode(String code);
	
	@Modifying
	@Query(value = "delete from TB_ROLE_TB_PERMISSION where TB_ROLE_id = :roleId", nativeQuery = true)
	public int deleteRolePermissionByRoleId(@Param("roleId")Integer roleId);
	
	
	public List<Permission> findByCodeIn(List<String> codes);
}
