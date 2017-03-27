package n.entity.play;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.annotation.Checked;
import n.core.entity.support.PersistableEntity;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_GAME")
public class Game extends PersistableEntity<Integer> {

	private static final long serialVersionUID = -3209115803847910307L;

	/**
	 * 彩种分组ID
	 */
	@Checked
	@ManyToOne
	@JoinColumn(name = "GM_GROUP", referencedColumnName = "id", columnDefinition = "TINYINT", nullable = false)
	private GMGroup gmGroup;
	
	@Checked
	@Enumerated(EnumType.STRING)
	@Column(name = "NAME", columnDefinition = "varchar(20)", nullable = false)
	private Name name = Name.PCOO; // 名称

	@Column(name = "CODE", columnDefinition = "varchar(32)", nullable = true)
	private String code; // 代码

	@Checked
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", columnDefinition = "varchar(8)", nullable = false)
	private Type type;

	@Checked
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "ENABLED", columnDefinition = "INT default 0", nullable = false)
	private Enabled enabled;

	@Column(name = "DEEP", length = 9, columnDefinition = "INT", nullable = true)
	private Integer deep;

	@Column(name = "ISSUES_DAILY", length = 9, columnDefinition = "INT", nullable = true)
	private Integer issuesDaily;

	public enum Name {
		PCOO
	}

	public enum Type {
		CR, OP
	}

	public enum Enabled {
		ON, OFF
	}
	
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public GMGroup getGmGroup() {
		return gmGroup;
	}

	public void setGmGroup(GMGroup gmGroup) {
		this.gmGroup = gmGroup;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Enabled getEnabled() {
		return enabled;
	}

	public void setEnabled(Enabled enabled) {
		this.enabled = enabled;
	}

	public Integer getDeep() {
		return deep;
	}

	public void setDeep(Integer deep) {
		this.deep = deep;
	}

	public Integer getIssuesDaily() {
		return issuesDaily;
	}

	public void setIssuesDaily(Integer issuesDaily) {
		this.issuesDaily = issuesDaily;
	}

}
