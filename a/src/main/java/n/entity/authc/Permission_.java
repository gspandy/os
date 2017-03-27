package n.entity.authc;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permission.class)
public abstract class Permission_ extends n.core.entity.support.CheckableEntity_ {

	public static volatile SingularAttribute<Permission, Integer> permissionType;
	public static volatile SingularAttribute<Permission, String> path;
	public static volatile SingularAttribute<Permission, Integer> deep;
	public static volatile SingularAttribute<Permission, String> code;
	public static volatile SingularAttribute<Permission, String> icon;
	public static volatile SingularAttribute<Permission, Integer> parentPermissionId;
	public static volatile SingularAttribute<Permission, Integer> floor;
	public static volatile SingularAttribute<Permission, Integer> isDisplay;
	public static volatile SingularAttribute<Permission, String> permissionName;

}

