package n.entity.sider;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.annotation.Checked;
import n.core.entity.support.CheckableEntity;

/**
 * 系统配置表
 * @author
 *
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TB_PREFERENCE")
public class Preference extends CheckableEntity<Integer> {

	private static final long serialVersionUID = -8061702407266997556L;

	/**
	 * 配置名称
	 */
	@Checked
	@Column(name = "NAME", length = 20, nullable = false)
	private String name;
	/**
	 * 配置说明
	 */
	@Column(name = "DESCRIPTION", length = 200, nullable = false)
	private String description;

	/**
	 * 键
	 */
	@Checked
	@Column(name = "CODE", length = 200, nullable = false, unique = true)
	private String code;

	/**
	 * 值
	 */
	@Checked
	@Column(name = "VALUE", length = 200, nullable = false)
	private String value;
	
	/**
	 * 类型
	 */
	@Checked
	@Column(name = "TYPE", length = 200, nullable = false)
	@Enumerated(EnumType.STRING)
	private PreferenceType type;
	/**
	 * 配置类型
	 * @author jt_wangshuiping @date 2016年12月8日
	 *
	 */
	public enum PreferenceType{
		config,					//普通配置
		timeout					//超时类型
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PreferenceType getType() {
		return type;
	}

	public void setType(PreferenceType type) {
		this.type = type;
	}

}
