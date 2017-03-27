package n.entity.play;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import n.entity.play.BetRecord.AwardStatus;
import n.entity.play.BetRecord.BetWay;
import n.entity.play.BetRecord.Device;
import n.entity.play.BetRecord.OrderStatus;
import n.entity.play.BetRecord.RebateStatus;
import n.entity.play.BetRecord.WinStatus;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BetRecord.class)
public abstract class BetRecord_ extends n.core.entity.support.PersistableEntity_ {

	public static volatile SingularAttribute<BetRecord, LN> ln;
	public static volatile SingularAttribute<BetRecord, Double> rebateAmount;
	public static volatile SingularAttribute<BetRecord, OrderStatus> orderStatus;
	public static volatile SingularAttribute<BetRecord, Date> awardTime;
	public static volatile SingularAttribute<BetRecord, AwardStatus> awardStatus;
	public static volatile SingularAttribute<BetRecord, String> winSecret;
	public static volatile SingularAttribute<BetRecord, Integer> betMultiple;
	public static volatile SingularAttribute<BetRecord, String> billNo;
	public static volatile SingularAttribute<BetRecord, String> betSecret;
	public static volatile SingularAttribute<BetRecord, RebateStatus> agentRebateStatus;
	public static volatile SingularAttribute<BetRecord, BetWay> betWay;
	public static volatile SingularAttribute<BetRecord, Integer> winCount;
	public static volatile SingularAttribute<BetRecord, Integer> betCount;
	public static volatile SingularAttribute<BetRecord, String> betNumber;
	public static volatile SingularAttribute<BetRecord, Double> winAmount;
	public static volatile SingularAttribute<BetRecord, WinStatus> winStatus;
	public static volatile SingularAttribute<BetRecord, Date> lastModifiedDate;
	public static volatile SingularAttribute<BetRecord, String> lastModifiedBy;
	public static volatile SingularAttribute<BetRecord, Integer> chaseRecordId;
	public static volatile SingularAttribute<BetRecord, Double> betAmount;
	public static volatile SingularAttribute<BetRecord, PlayType> playType;
	public static volatile SingularAttribute<BetRecord, Integer> chaseIssueId;
	public static volatile SingularAttribute<BetRecord, Integer> chaseNumberId;
	public static volatile SingularAttribute<BetRecord, Date> betTime;
	public static volatile SingularAttribute<BetRecord, String> lotteryNumber;
	public static volatile SingularAttribute<BetRecord, Double> betTotal;
	public static volatile SingularAttribute<BetRecord, RebateStatus> betRebateStatus;
	public static volatile SingularAttribute<BetRecord, Device> device;

}

