package com.wise.core.entity.support;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 持久化辅助类,
 * 当子类继承此类时,主键不自增长.
 *
 * @author  jtwise
 * @date 2016年7月19日 下午6:04:15
 * @verion 1.0
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CustomPKEntity<PK extends Serializable> implements Persistable<PK> {
 
	private static final long serialVersionUID = 4129264629296054711L;
	
	@Id
	private PK id;
	
	@Override
	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}
	@Override
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
		CustomPKEntity<?> that = (CustomPKEntity<?>) obj;
		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += null == getId() ? 0 : getId().hashCode() * 31;
		return hashCode;
	}
}
