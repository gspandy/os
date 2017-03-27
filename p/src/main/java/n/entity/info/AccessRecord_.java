package n.entity.info;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import n.entity.authc.Company;
import n.entity.authc.User;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AccessRecord.class)
public abstract class AccessRecord_ extends n.core.entity.support.PersistableEntity_ {

	public static volatile SingularAttribute<AccessRecord, Date> leaveTime;
	public static volatile SingularAttribute<AccessRecord, Company> companyId;
	public static volatile SingularAttribute<AccessRecord, Date> entryTime;
	public static volatile SingularAttribute<AccessRecord, String> visitorIp;
	public static volatile SingularAttribute<AccessRecord, Integer> refuseTimes;
	public static volatile SingularAttribute<AccessRecord, User> customerId;
	public static volatile SingularAttribute<AccessRecord, String> lastMsg;
	public static volatile SingularAttribute<AccessRecord, String> userName;
	public static volatile SingularAttribute<AccessRecord, Date> waitTime;
	public static volatile SingularAttribute<AccessRecord, String> visitorId;
	public static volatile SingularAttribute<AccessRecord, Boolean> onlineState;

}

