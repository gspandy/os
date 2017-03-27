package n.entity.play;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import n.entity.play.Game.Enabled;
import n.entity.play.Game.Name;
import n.entity.play.Game.Type;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Game.class)
public abstract class Game_ extends n.core.entity.support.PersistableEntity_ {

	public static volatile SingularAttribute<Game, Integer> deep;
	public static volatile SingularAttribute<Game, String> code;
	public static volatile SingularAttribute<Game, Name> name;
	public static volatile SingularAttribute<Game, GMGroup> gmGroup;
	public static volatile SingularAttribute<Game, Type> type;
	public static volatile SingularAttribute<Game, Enabled> enabled;
	public static volatile SingularAttribute<Game, Integer> issuesDaily;

}

