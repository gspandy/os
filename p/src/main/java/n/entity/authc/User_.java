package n.entity.authc;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.DateTime;

import n.entity.authc.User.AccountState;
import n.entity.authc.User.AccountType;
import n.entity.authc.User.OnlineState;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends n.core.entity.support.CheckableEntity_ {

	public static volatile SingularAttribute<User, AccountState> accountState;
	public static volatile SingularAttribute<User, String> nickName;
	public static volatile SingularAttribute<User, AccountType> accountType;
	public static volatile ListAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> mobile;
	public static volatile SingularAttribute<User, String> userName;
	public static volatile SingularAttribute<User, Boolean> autoAllot;
	public static volatile SingularAttribute<User, String> loginFailureTimes;
	public static volatile SingularAttribute<User, Layer> layer;
	public static volatile SingularAttribute<User, String> lastLoginIp;
	public static volatile SingularAttribute<User, DateTime> lastLoginTime;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, DateTime> loginTime;
	public static volatile SingularAttribute<User, String> loginIp;
	public static volatile SingularAttribute<User, Company> company;
	public static volatile SingularAttribute<User, Integer> receiveNum;
	public static volatile SingularAttribute<User, String> userIcon;
	public static volatile SingularAttribute<User, String> autoReply;
	public static volatile SingularAttribute<User, String> passwordSalt;
	public static volatile SingularAttribute<User, Integer> curReceiveNum;
	public static volatile SingularAttribute<User, String> account;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, OnlineState> onlineState;

}

