package n.entity.play;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PlayType.class)
public abstract class PlayType_ extends n.core.entity.support.CustomPKEntity_ {

	public static volatile SingularAttribute<PlayType, Integer> deep;
	public static volatile SingularAttribute<PlayType, PlayTypeGroup> playTypeGroup;
	public static volatile SingularAttribute<PlayType, String> name;
	public static volatile SingularAttribute<PlayType, String> ruleDes;
	public static volatile SingularAttribute<PlayType, GMGroup> gmGroup;
	public static volatile SingularAttribute<PlayType, PlayRule> playRule;
	public static volatile SingularAttribute<PlayType, Double> multiplyingPower;
	public static volatile SingularAttribute<PlayType, Integer> enabled;
	public static volatile SingularAttribute<PlayType, String> target;

}

