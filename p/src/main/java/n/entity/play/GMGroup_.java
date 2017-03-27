package n.entity.play;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import n.entity.play.GMGroup.TypeName;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GMGroup.class)
public abstract class GMGroup_ extends n.core.entity.support.CustomPKEntity_ {

	public static volatile SingularAttribute<GMGroup, Integer> deep;
	public static volatile SingularAttribute<GMGroup, String> name;
	public static volatile SingularAttribute<GMGroup, TypeName> typeName;

}

