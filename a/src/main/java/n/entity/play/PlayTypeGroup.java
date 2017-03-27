package n.entity.play;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.support.PersistableEntity;

/**
 * 玩法分组
 * 
 * @author
 * 
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_PLAY_TYPE_GROUP", indexes = {
		@Index(name = "IDX_GROUP_NAME", columnList = "GROUP_NAME")
	})
public class PlayTypeGroup extends PersistableEntity<Integer> {

	private static final long serialVersionUID = -3726366775412658366L;
	
	/**
	 * 分组ID
	 */
	@Column(name = "GROUP_ID", columnDefinition ="INT COMMENT '分组ID'", nullable = false)
	private Integer groupId;

	/**
	 * 分组名称
	 */
	@Column(name = "GROUP_NAME", columnDefinition ="varchar(20) COMMENT '分组名称'", nullable = false)
	private String groupName;
	/**
	 * 排序
	 */
	@Column(name = "DEEP", columnDefinition = "TINYINT COMMENT '排序'")
	private Integer deep;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getDeep() {
		return deep;
	}

	public void setDeep(Integer deep) {
		this.deep = deep;
	}
	 
}
