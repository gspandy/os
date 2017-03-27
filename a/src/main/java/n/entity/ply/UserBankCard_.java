package n.entity.ply;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import n.entity.authc.User;
import n.entity.ply.UserBankCard.CardStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserBankCard.class)
public abstract class UserBankCard_ extends n.core.entity.support.PersistableEntity_ {

	public static volatile SingularAttribute<UserBankCard, Bank> bank;
	public static volatile SingularAttribute<UserBankCard, Date> lastModifiedTime;
	public static volatile SingularAttribute<UserBankCard, String> accountName;
	public static volatile SingularAttribute<UserBankCard, String> accountNo;
	public static volatile SingularAttribute<UserBankCard, String> branchName;
	public static volatile SingularAttribute<UserBankCard, Date> unfreezeTime;
	public static volatile SingularAttribute<UserBankCard, String> secret;
	public static volatile SingularAttribute<UserBankCard, User> user;
	public static volatile SingularAttribute<UserBankCard, CardStatus> status;

}

