package n.core.entity.support;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import n.core.entity.annotation.Checked;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity<PK extends Serializable> extends PersistableEntity<PK> {

	private static final long serialVersionUID = 9150700774816395865L;

	/**
	 * 创建者
	 */
	@Checked
	@CreatedBy
	@Column(name = "CREATED_BY", length = 16, nullable = false)
	private String createdBy;

	/**
	 * 创建时间
	 */
	@CreatedDate
	@Column(name = "CREATED_DATE", nullable = false)
	@Type(type = "n.core.entity.usertype.PersistentDateTimeAsMillisLong")
	private DateTime createdDate;

	/**
	 * 操作者
	 */
	@Checked
	@LastModifiedBy
	@Column(name = "LAST_MODIFIED_BY", length = 16, nullable = false)
	private String lastModifiedBy;

	/**
	 * 操作时间
	 */
	@LastModifiedDate
	@Column(name = "LAST_MODIFIED_DATE", nullable = true)
	@Type(type = "n.core.entity.usertype.PersistentDateTimeAsMillisLong")
	private DateTime lastModifiedDate; // TODO 李

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(DateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
