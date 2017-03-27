package n.entity.authc;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import n.entity.authc.Role.RoleType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ extends n.core.entity.support.CheckableEntity_ {

	public static volatile ListAttribute<Role, Permission> permissions;
	public static volatile SingularAttribute<Role, String> roleName;
	public static volatile SingularAttribute<Role, RoleType> roleType;
	public static volatile SingularAttribute<Role, Integer> parentId;

}

