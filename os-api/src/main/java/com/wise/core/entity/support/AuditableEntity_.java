package com.wise.core.entity.support;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.DateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AuditableEntity.class)
public abstract class AuditableEntity_ extends com.wise.core.entity.support.PersistableEntity_ {

	public static volatile SingularAttribute<AuditableEntity, DateTime> createdDate;
	public static volatile SingularAttribute<AuditableEntity, String> createdBy;
	public static volatile SingularAttribute<AuditableEntity, DateTime> lastModifiedDate;
	public static volatile SingularAttribute<AuditableEntity, String> lastModifiedBy;

}

