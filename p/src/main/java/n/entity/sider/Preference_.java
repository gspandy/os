package n.entity.sider;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import n.entity.sider.Preference.PreferenceType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Preference.class)
public abstract class Preference_ extends n.core.entity.support.CheckableEntity_ {

	public static volatile SingularAttribute<Preference, String> code;
	public static volatile SingularAttribute<Preference, String> name;
	public static volatile SingularAttribute<Preference, String> description;
	public static volatile SingularAttribute<Preference, PreferenceType> type;
	public static volatile SingularAttribute<Preference, String> value;

}

