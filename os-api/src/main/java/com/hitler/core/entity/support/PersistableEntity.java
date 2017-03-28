package com.hitler.core.entity.support;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.domain.Persistable;

@MappedSuperclass
public abstract class PersistableEntity<PK extends Serializable> implements Persistable<PK> {

	private static final long serialVersionUID = 8251232611423298391L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private PK id;

	@Version
	@Column(name = "VERSION", nullable = false)
	private Integer version;

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean isNew() {
		return null == getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!getClass().equals(obj.getClass())) {
			return false;
		}
		PersistableEntity<?> that = (PersistableEntity<?>) obj;
		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += null == getId() ? 0 : getId().hashCode() * 31;
		return hashCode;
	}

}
