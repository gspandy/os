package n.repository.authc;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import n.core.repository.support.GenericRepository;
import n.entity.authc.Role;
import n.entity.authc.Role.RoleType;

/**
 * 角色
 * 
 * @author onsoul 2015-6-17 下午10:24:55
 */
public interface IRoleRepository extends GenericRepository<Role, Integer> {
	
	@Query("SELECT roleName FROM Role")
	public List<String> findAllRoleName();

	public Role findByRoleName(String roleName);

	/**
	 * 用户id查询所有对应角色
	 * @param userId
	 * @return
	 */
	@Query(value = "select * from TB_ROLE r, TB_USER_TB_ROLE ur where r.id = ur.roles_id and ur.TB_USER_id = :userId", nativeQuery = true)
	public List<Role> findByUserId(@Param("userId")Long userId);
	/**
	 * 用户id查询所有对应角色名
	 * @param userId
	 * @return
	 */
	@Query(value = "select r.role_name from TB_ROLE r, TB_USER_TB_ROLE ur where r.id = ur.roles_id and ur.TB_USER_id = :userId", nativeQuery = true)
	public Set<String> findUserRolesByUserId(@Param("userId")Long userId);
	
	
	/**
	 * 插入UserRole
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@Modifying
	@Query(value = "insert into TB_USER_TB_ROLE values(:userId, :roleId)", nativeQuery  = true)
	public int insertUserRole(@Param("userId")Long userId, @Param("roleId") Integer roleId);
	/**
	 * 删除UserRole
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@Modifying
	@Query(value = "delete from TB_USER_TB_ROLE where TB_USER_id = :userId and roles_id = :roleId", nativeQuery  = true)
	public int deleteUserRole(@Param("userId")Long userId, @Param("roleId") Integer roleId);
	@Modifying
	@Query(value = "delete from TB_USER_TB_ROLE where TB_USER_id = :userId", nativeQuery  = true)
	public int deleteUserRole(@Param("userId")Long userId);
	
	public List<Role> findByRoleType(RoleType roleType);

	public List<Role> findByRoleNameIn(List<String> names);
	
}
