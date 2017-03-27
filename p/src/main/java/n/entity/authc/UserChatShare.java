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
@Table(name = "TB_USER_CHAT_SHARE")
public class UserChatShare extends CheckableEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9194887371154363072L;
	@Column(name = "USER_ID", length = 20, columnDefinition = "int", nullable = false)
	private Long userId;
	@Column(name = "SHARE_USER_ID", length = 20, columnDefinition = "int", nullable = false)
	private Long shareUserId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(Long shareUserId) {
		this.shareUserId = shareUserId;
	}

}
