package n.table.dto.authc;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import n.core.dto.TransientDTO;

public class PermissionUpdateDTO extends TransientDTO<Integer> {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Integer id;
	/**
	 * 权限名称
	 */
	@NotNull
	@Length(min=3,max=15)
	private String permissionName;
	/**
	 * 权限类型
	 */
	@NotNull
	private Integer permissionType;
	/**
	 * 路径
	 */
	@NotNull
	private String path;
	/**
	 * 权限代码
	 */
	@NotNull
	private String code;
	/**
	 * 父权限ID
	 */
	@NotNull
	private Integer parentPermissionId;
	/**
	 * 所在层级
	 */
	@NotNull
	private Integer floor;
	/**
	 * 排序（深度）
	 */
	@NotNull
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

	public void setId(Integer id) {
		this.id = id;
	}

}
