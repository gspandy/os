package n.entity.play;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import n.entity.play.LN.LotteryType;
import n.entity.play.LN.Status;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LN.class)
public abstract class LN_ extends n.core.entity.support.PersistableEntity_ {

	public static volatile SingularAttribute<LN, Game> game;
	public static volatile SingularAttribute<LN, Date> scheduledTime;
	public static volatile SingularAttribute<LN, String> issue;
	public static volatile SingularAttribute<LN, Date> operateTime;
	public static volatile SingularAttribute<LN, Integer> crawlerTimeCost;
	public static volatile SingularAttribute<LN, String> remark;
	public static volatile SingularAttribute<LN, LotteryType> lotteryType;
	public static volatile SingularAttribute<LN, String> operator;
	public static volatile SingularAttribute<LN, Double> winMoneyTotal;
	public static volatile SingularAttribute<LN, String> crawlerUrlDescription;
	public static volatile SingularAttribute<LN, Double> betMoneyTotal;
	public static volatile SingularAttribute<LN, Double> rebateMoneyTotal;
	public static volatile SingularAttribute<LN, Integer> betOrders;
	public static volatile SingularAttribute<LN, Date> closeTime;
	public static volatile SingularAttribute<LN, String> lotteryNumber;
	public static volatile SingularAttribute<LN, Double> betAmountMax;
	public static volatile SingularAttribute<LN, Date> lotteryTime;
	public static volatile SingularAttribute<LN, Date> openTime;
	public static volatile SingularAttribute<LN, Integer> winOrders;
	public static volatile SingularAttribute<LN, Status> status;

}

