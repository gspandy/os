package n.entity.play;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.annotation.Checked;
import n.core.entity.support.CustomPKEntity;

/**
 * 彩种分组
 * 
 * @author
 * 
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_GM_GROUP", indexes = { @Index(name = "IDX_GROUP_NAME", columnList = "GROUP_NAME") })
public class GMGroup extends CustomPKEntity<Integer> {

	private static final long serialVersionUID = -3726366775412658366L;
	
	/**
	 * 分组名称
	 */
	@Column(name = "GROUP_NAME", columnDefinition = "varchar(20) COMMENT '分组名称'", nullable = false)
	private String name;

	/**
	 * 排序
	 */
	@Column(name = "DEEP", columnDefinition = "TINYINT COMMENT '排序'")
	private Integer deep;

	@Checked
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE_NAME", columnDefinition = "varchar(20)", nullable = false)
	private TypeName typeName = TypeName.F3DP3; // 名称

	public TypeName getTypeName() {
		return typeName;
	}

	public void setTypeName(TypeName typeName) {
		this.typeName = typeName;
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

	public enum TypeName {
		HIGH, F11X5, F3DP3, KLC, PK10 
	}

}
