package n.entity.sider;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import n.entity.sider.PromptConfig.PromptType;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PromptConfig.class)
public abstract class PromptConfig_ extends n.core.entity.support.PersistableEntity_ {

	public static volatile SingularAttribute<PromptConfig, String> userAccount;
	public static volatile SingularAttribute<PromptConfig, String> time;
	public static volatile SingularAttribute<PromptConfig, PromptType> type;
	public static volatile SingularAttribute<PromptConfig, Long> userId;
	public static volatile SingularAttribute<PromptConfig, String> content;

}

