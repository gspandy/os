package n.entity.play;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.annotation.Checked;
import n.core.entity.support.CustomPKEntity;

/**
 * 玩法
 * 
 * @author
 * 
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_PLAY_TYPE", indexes = { @Index(name = "IDX_ID", columnList = "ID"),
		@Index(name = "IDX_PLAY_TYPE_GROUP", columnList = "PLAY_TYPE_GROUP") })
public class PlayType extends CustomPKEntity<Integer> {

	private static final long serialVersionUID = 6943593253681341354L;

	/**
	 * 彩种分组
	 */
	@Checked
	@ManyToOne
	@JoinColumn(name = "GM_GROUP", referencedColumnName = "id", columnDefinition = "INT COMMENT '彩种分组ID'", nullable = false)
	private GMGroup gmGroup;

	/**
	 * 玩法分组
	 */
	@Checked
	@ManyToOne
	@JoinColumn(name = "PLAY_TYPE_GROUP", referencedColumnName = "id", columnDefinition = "INT COMMENT '玩法分组ID'", nullable = false)
	private PlayTypeGroup playTypeGroup;

	/**
	 * 玩法名称
	 */
	@Checked
	@Column(name = "NAME", columnDefinition = "varchar(20) COMMENT '玩法名称'", nullable = false)
	private String name;

	// 玩法规则
	@Checked
	@Column(name = "PLAY_RULE", columnDefinition = "varchar(20)", nullable = false)
	@Enumerated(EnumType.STRING)
	private PlayRule playRule;

	// 规则目标字符串
	@Checked
	@Column(name = "TARGET", columnDefinition = "varchar(64)", nullable = false)
	private String target;

	// 玩法规则说明
	@Checked
	@Column(name = "RULE_DES", columnDefinition = "varchar(128) COMMENT '玩法名称'", nullable = false)
	private String ruleDes;

	// 玩法倍率
	@Checked
	@Column(name = "MULTIPLYING_POWER", columnDefinition = "DECIMAL(15,2) DEFAULT 0.0 ", nullable = false)
	private Double multiplyingPower;

	/**
	 * 排序
	 */
	@Checked
	@Column(name = "DEEP", columnDefinition = "TINYINT COMMENT '排序'")
	private Integer deep = 0;
	/**
	 * 启用状态
	 */
	@Column(name = "ENABLED", columnDefinition = "TINYINT(2) COMMENT '启用状态'", nullable = false)
	private Integer enabled;
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Double getMultiplyingPower() {
		return multiplyingPower;
	}

	public void setMultiplyingPower(Double multiplyingPower) {
		this.multiplyingPower = multiplyingPower;
	}

	public PlayRule getPlayRule() {
		return playRule;
	}

	public void setPlayRule(PlayRule playRule) {
		this.playRule = playRule;
	}

	public String getRuleDes() {
		return ruleDes;
	}

	public void setRuleDes(String ruleDes) {
		this.ruleDes = ruleDes;
	}

	public GMGroup getGmGroup() {
		return gmGroup;
	}

	public void setGmGroup(GMGroup gmGroup) {
		this.gmGroup = gmGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDeep() {
		return deep;
	}

	public void setDeep(Integer deep) {
		this.deep = deep;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public PlayTypeGroup getPlayTypeGroup() {
		return playTypeGroup;
	}

	public void setPlayTypeGroup(PlayTypeGroup playTypeGroup) {
		this.playTypeGroup = playTypeGroup;
	}

	public static enum Type {
		MENU, PLAY
	}

}
