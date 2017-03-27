package n.table.dto.authc;

import n.core.dto.PersistentDTO;

public class PermissionDTO extends PersistentDTO<Integer> {

	private static final long serialVersionUID = 1L;
	private Integer id;
	/**
	 * 权限名称
	 */
	private String permissionName;
	/**
	 * 权限类型
	 */
	private Integer permissionType;
	/**
	 * 路径
	 */
	private String path;
	/**
	 * 权限代码
	 */
	private String code;
	/**
	 * 父权限ID
	 */
	private Integer parentPermissionId;
	/**
	 * 所在层级
	 */
	private Integer floor;
	/**
	 * 排序（深度）
	 */
	private Integer deep;
	/**
	 * 是否显示(默认0-显示，1-隐藏)
	 */
	private Integer isDisplay;
	/**
	 * 图标()
	 */
	private String icon;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(Integer permissionType) {
		this.permissionType = permissionType;
	}

	public Integer getParentPermissionId() {
		return parentPermissionId;
	}

	public void setParentPermissionId(Integer parentPermissionId) {
		this.parentPermissionId = parentPermissionId;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public Integer getDeep() {
		return deep;
	}

	public void setDeep(Integer deep) {
		this.deep = deep;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
