package n.entity.ply;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import n.entity.ply.Bank.AllowBindingStatus;
import n.entity.ply.Bank.TransferStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bank.class)
public abstract class Bank_ extends n.core.entity.support.CheckableEntity_ {

	public static volatile SingularAttribute<Bank, TransferStatus> transferStatus;
	public static volatile SingularAttribute<Bank, String> name;
	public static volatile SingularAttribute<Bank, String> logoFilePath;
	public static volatile SingularAttribute<Bank, AllowBindingStatus> allowBindingStatus;
	public static volatile SingularAttribute<Bank, String> rechargeDemoUrl;
	public static volatile SingularAttribute<Bank, String> shortName;
	public static volatile SingularAttribute<Bank, String> url;

}

