package n.entity.ply;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.annotation.Checked;
import n.core.entity.support.CheckableEntity;

/**
 * 第三方支付
 * @author onsoul
 *
 */

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_THIRD_PLY")
public class ThirdPly extends CheckableEntity<Integer>{
	private static final long serialVersionUID = 7186813288153756819L;

	/**
	 * 支付方名称
	 */
	@Checked
	@Column(name = "NAME", columnDefinition = "varchar(10) COMMENT '名称'", nullable = false)
	private String name;
	
	
	/**
	 * 支付方编号
	 */
	@Checked
	@Column(name = "CODE", columnDefinition = "varchar(10) COMMENT '代码'", nullable = false)
	private String code;

	/**
	 * 支付方简称
	 */
	@Checked
	@Column(name = "SHORT_NAME", columnDefinition = "varchar(10) COMMENT '简称'")
	private String shortName;

	/**
	 * 银行Logo图片路径
	 */
	@Checked
	@Column(name = "ICON", columnDefinition = "varchar(50) COMMENT 'Logo图片路径'")
	private String icon;

	/**
	 * 网银地址
	 */
	@Checked
	@Column(name = "URL", columnDefinition = "varchar(200) COMMENT '网银地址'", nullable = false)
	private String url;
	
	@Checked
	@Column(name = "SOLT", columnDefinition = "varchar(128) COMMENT '网银地址'", nullable = false)
	private String solt;
	

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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSolt() {
		return solt;
	}

	public void setSolt(String solt) {
		this.solt = solt;
	}

}
