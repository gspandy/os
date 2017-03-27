package n.entity.authc;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import n.entity.authc.Layer.LayerState;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Layer.class)
public abstract class Layer_ extends n.core.entity.support.PersistableEntity_ {

	public static volatile SingularAttribute<Layer, String> code;
	public static volatile SingularAttribute<Layer, String> comments;
	public static volatile SingularAttribute<Layer, String> imgPath;
	public static volatile SingularAttribute<Layer, Company> company;
	public static volatile SingularAttribute<Layer, String> layerName;
	public static volatile SingularAttribute<Layer, LayerState> state;
	public static volatile SingularAttribute<Layer, String> points;

}

