package n.entity.authc;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import n.entity.authc.Company.CState;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Company.class)
public abstract class Company_ extends n.core.entity.support.CheckableEntity_ {

	public static volatile SingularAttribute<Company, String> webSite;
	public static volatile SingularAttribute<Company, String> code;
	public static volatile SingularAttribute<Company, String> address;
	public static volatile SingularAttribute<Company, String> companyName;
	public static volatile SingularAttribute<Company, String> telNum;
	public static volatile SingularAttribute<Company, CState> cState;
	public static volatile SingularAttribute<Company, String> companyAccount;
	public static volatile SingularAttribute<Company, String> email;
	public static volatile SingularAttribute<Company, String> url;
	public static volatile SingularAttribute<Company, Boolean> isEnable;

}

