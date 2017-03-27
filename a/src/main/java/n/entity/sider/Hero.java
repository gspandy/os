package n.entity.sider;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "TB_HERO")
public class Hero extends CheckableEntity<Integer> {

	private static final long serialVersionUID = -8061702407266997556L;

	/**
	 * 配置名称
	 */
	@Checked
	@Column(name = "NAME", length = 20, nullable = false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
