package n.table.dto.ply;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotBlank;

import n.core.dto.PersistentDTO;
import n.core.entity.annotation.Checked;

public class ThirdPlyDTO extends PersistentDTO<Integer>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1615407419575000768L;

	@Override
	public Integer getId() {
		return null;
	}
	
	/**
	 * 支付方名称
	 */
	@NotBlank
	private String name;
 

	/**
	 * 支付方简称
	 */
	@NotBlank
	private String shortName;

	/**
	 * 银行Logo图片路径
	 */
	@NotBlank
	private String icon;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
