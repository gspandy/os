package n.core.entity.support;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import n.core.entity.annotation.Checkable;
import n.core.entity.listener.CheckingEntityListener;

@MappedSuperclass
@EntityListeners(CheckingEntityListener.class)
public abstract class CheckableEntity<PK extends Serializable> extends AuditableEntity<PK> {

	private static final long serialVersionUID = 2469813719494517116L;

	@Checkable
	@Column(name = "CHECKSUM", length = 32, nullable = false)
	private String checksum;

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	

}
