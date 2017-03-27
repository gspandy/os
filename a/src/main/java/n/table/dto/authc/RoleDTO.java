package n.table.dto.authc;

import n.core.dto.PersistentDTO;

public class RoleDTO extends PersistentDTO<Integer>{

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer roleType;
	
	private Integer parentId;
	
	private String roleName;

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	
}
