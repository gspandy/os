package n.entity.authc;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.support.CheckableEntity;

/**
 * 角色类
 * 
 * @author onsoul
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_ROLE")
public class Role extends CheckableEntity<Integer> {

	private static final long serialVersionUID = 1L;

	@Column(name = "ROLE_TYPE", length = 20, columnDefinition="INT", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private RoleType roleType; // 类型

	@Column(name = "ROLE_NAME", length = 32, columnDefinition = "varchar(20)",nullable = true)
	private String roleName; // 名称
	
	@Column(name = "PARENT_ID",columnDefinition = "INT",nullable = true)
	private Integer parentId; // 父节点

	// ,foreignKey=@ForeignKey(name="FK_ROLE_PERMISSIONS")(fetch=FetchType.EAGER, targetEntity = Permission.class)
	@ManyToMany
	private List<Permission> permissions; // 一个角色拥有多个权限

	/**
	 * 系统默认角色(sysdefault:0),自定义角色(userdefined:1)
	 * @author jt_wangshuiping @date 2016年11月22日
	 *
	 */
	public enum RoleType{
		sysdefault, userdefined
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
