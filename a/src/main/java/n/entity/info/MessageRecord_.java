package n.entity.info;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MessageRecord.class)
public abstract class MessageRecord_ extends n.core.entity.support.PersistableEntity_ {

	public static volatile SingularAttribute<MessageRecord, String> msg;
	public static volatile SingularAttribute<MessageRecord, Integer> companyId;
	public static volatile SingularAttribute<MessageRecord, Boolean> msgType;
	public static volatile SingularAttribute<MessageRecord, String> visitorIp;
	public static volatile SingularAttribute<MessageRecord, String> fromUser;
	public static volatile SingularAttribute<MessageRecord, String> fromNickName;
	public static volatile SingularAttribute<MessageRecord, Long> fromId;
	public static volatile SingularAttribute<MessageRecord, Long> userId;
	public static volatile SingularAttribute<MessageRecord, Boolean> status;
	public static volatile SingularAttribute<MessageRecord, Date> sendTime;

}

