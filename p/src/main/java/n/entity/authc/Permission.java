package n.entity.authc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.support.CheckableEntity;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_PERMISSION")
public class Permission extends CheckableEntity<Integer> {
	 
	private static final long serialVersionUID = 1727025558539025470L;
	/**
	 * 权限名称
	 */
	@Column(name = "PERMISSION_NAME", length = 30, nullable = false)
	private String permissionName;
	/**
	 * 权限类型(1-权限，2-菜单)
	 */
	@Column(name = "PERMISSION_TYPE", columnDefinition = "INT", nullable=false )
	private Integer permissionType;
	/**
	 * 路径
	 */
	@Column(name = "PATH", length = 50, unique = true, nullable = false)
	private String path;
	/**
	 * 权限代码
	 */
	@Column(name = "CODE", length = 50, nullable = false)
	private String code;
	/**
	 * 父权限ID
	 */
	@Column(name = "PARENT_PERMISSION_ID", columnDefinition = "INT", nullable = true)
	private Integer parentPermissionId;
	/**
	 * 所在层级
	 */
	@Column(name = "FLOOR", columnDefinition = "INT", nullable = true)
	private Integer floor;
	/**
	 * 排序（深度）
	 */
	@Column(name = "DEEP", columnDefinition = "INT", nullable = true)
	private Integer deep;
	/**
	 * 是否显示(默认0-显示，1-隐藏)
	 */
	@Column(name = "IS_DISPLAY", columnDefinition = "INT default 0")
	private Integer isDisplay;
	/**
	 * 图标()
	 */
	@Column(name = "ICON", columnDefinition = "varchar(60)")
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
