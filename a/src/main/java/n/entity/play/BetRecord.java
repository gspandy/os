package n.entity.play;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;

import n.core.entity.annotation.Checked;
import n.core.entity.support.PersistableEntity;
import n.core.jutils.bean.BillNoUtils;

/**
 * 投注记录实体对象
 * 
 * @author yangJJ
 * @date 2016-07-15
 * @verson 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_BET_RECORD", indexes = { @Index(name = "IDX_BILLNO", columnList = "BILLNO")})
public class BetRecord extends PersistableEntity<Long> {

	private static final long serialVersionUID = -6378188431615185271L;

	/**
	 * 投注单号
	 */
	@Checked
	@Column(name = "BILLNO", columnDefinition = "varchar(24)", nullable = false)
	private String billNo = BillNoUtils.generateBillNo(BillNoUtils.PREFIX_TZ);

	/**
	 * 彩种期号ID
	 */
	@Checked
	@ManyToOne
	@JoinColumn(name = "LN_ID", referencedColumnName = "id", columnDefinition = "INT", nullable = false)
	private LN ln;

	/**
	 * 玩法类型
	 */
	@Checked
	@ManyToOne()
	@JoinColumn(name = "PLAY_TYPE_ID", referencedColumnName = "id", columnDefinition = "INT", nullable = false)
	private PlayType playType;

	/**
	 * 投注时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BET_TIME", columnDefinition = "TIMESTAMP NULL COMMENT '投注时间'", nullable = false)
	private Date betTime = new Date();

	/**
	 * 投注号码 - 0|0|0,1
	 */
	@Checked
	@Column(name = "BET_NUMBER", columnDefinition = "LONGTEXT", nullable = false)
	private String betNumber;

	/**
	 * 投注注数
	 */
	@Checked
	@Column(name = "BET_COUNT", columnDefinition = "INT COMMENT '投注注数'", nullable = false)
	private Integer betCount;

	/**
	 * 单注金额 - 2.00
	 */
	@Checked
	@Column(name = "BET_AMOUNT", columnDefinition = "DECIMAL(15,5) DEFAULT 0.0 COMMENT '单注金额'", nullable = false)
	private Double betAmount;

	/**
	 * 投注倍数
	 */
	@Checked
	@Column(name = "BET_MULTIPLE", columnDefinition = "INT DEFAULT 1 COMMENT '投注倍数'", nullable = false)
	private Integer betMultiple;

	/**
	 * 投注总额 - 4.00
	 */
	@Checked
	@Column(name = "BET_TOTAL", columnDefinition = "DECIMAL(15,5) DEFAULT 0.0 COMMENT '投注总额'", nullable = false)
	private Double betTotal;


	/**
	 * 投注信息加密 MD5(投注注数+投注总额 +投注位+所选返点)
	 * MD5(BET_COUNT+BET_TOTAL+BET_DIGITS+REBATE_POINT)
	 */
	@Checked
	@Column(name = "BET_SECRET", columnDefinition = "varchar(32) COMMENT 'BET_SECRET'")
	private String betSecret;

	/**
	 * 中奖状态
	 */
	@Checked
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "WIN_STATUS", columnDefinition = "TINYINT(2) COMMENT '开奖状态'")
	private WinStatus winStatus = WinStatus.NO_LOTTERY;

	/**
	 * 中奖注数
	 */
	@Checked
	@Column(name = "WIN_COUNT", columnDefinition = "INT COMMENT '中奖注数'")
	private Integer winCount = 0;

	/**
	 * 中奖金额
	 */
	@Checked
	@Column(name = "WIN_AMOUNT", columnDefinition = "DECIMAL(15,5) DEFAULT 0.0 COMMENT '中奖金额'")
	private Double winAmount = 0D;

	/**
	 * 中奖信息加密 MD5(中奖注数+中奖金额) MD5(WIN_COUNT+WIN_AMOUNT)
	 */
	@Checked
	@Column(name = "WIN_SECRET", columnDefinition = "varchar(32) COMMENT 'WIN_SECRET'")
	private String winSecret;

	/**
	 * 派奖状态
	 */
	@Checked
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "AWARD_STATUS", columnDefinition = "TINYINT(2) COMMENT '派奖状态'")
	private AwardStatus awardStatus = AwardStatus.NO_LOTTERY;

	/**
	 * 派奖时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AWARD_TIME", columnDefinition = "TIMESTAMP NULL COMMENT '派奖时间'")
	private Date awardTime;

	/**
	 * 订单状态
	 */
	@Checked
	@Column(name = "ORDER_STATUS", columnDefinition = "TINYINT(2) COMMENT '订单状态'")
	private OrderStatus orderStatus = OrderStatus.NORMAL; // 正常

	/**
	 * 投注方式
	 */

	@Checked
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "BET_WAY", columnDefinition = "TINYINT(2) COMMENT '投注方式'")
	private BetWay betWay;

	/**
	 * 追号记录ID
	 */

	@Checked
	@Column(name = "CHASE_RECORD_ID", columnDefinition = "INT COMMENT '追号记录ID'")
	private Integer chaseRecordId = 0;

	/**
	 * 追号期号ID
	 */

	@Checked
	@Column(name = "CHASE_ISSUE_ID", columnDefinition = "INT COMMENT '追号期号ID'")
	private Integer chaseIssueId = 0;

	/**
	 * 追号号码ID
	 */

	@Checked
	@Column(name = "CHASE_NUMBER_ID", columnDefinition = "INT COMMENT '追号号码ID'")
	private Integer chaseNumberId = 0;

	/**
	 * 开奖号码
	 */

	@Checked
	@Column(name = "LOTTERY_NUMBER", columnDefinition = "varchar(100) COMMENT '开奖号码'")
	private String lotteryNumber = "";

	/**
	 * 投注返点派发状态
	 */

	@Checked
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "BET_REBATE_STATUS", columnDefinition = "TINYINT(2) COMMENT '投注返点派发状态'")
	private RebateStatus betRebateStatus = RebateStatus.NOT_DO;

	/**
	 * 投注返点金额
	 */

	@Checked
	@Column(name = "REBATE_AMOUNT", columnDefinition = "DECIMAL(15,5) DEFAULT 0.0 COMMENT '投注返点金额'")
	private Double rebateAmount = 0D;

	/**
	 * 代理返点派发状态
	 */

	@Checked
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "AGENT_REBATE_STATUS", columnDefinition = "TINYINT(2) COMMENT '代理返点派发状态'")
	private RebateStatus agentRebateStatus = RebateStatus.NOT_DO;

	/**
	 * 投注设备
	 */
	@Checked
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "DEVICE", columnDefinition = "TINYINT(2) COMMENT '投注设备'")
	private Device device = Device.COMPUTER;

	/**
	 * 操作者(撤单者）
	 */

	@Checked
	/* @LastModifiedBy */
	@Column(name = "LAST_MODIFIED_BY", columnDefinition = "varchar(16) COMMENT '操作者'")
	private String lastModifiedBy;

	/**
	 * 操作时间（撤单时间）
	 */
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED_DATE", columnDefinition = "TIMESTAMP NULL COMMENT '操作时间'")
	private Date lastModifiedDate;
	

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public LN getLn() {
		return ln;
	}

	public void setLn(LN ln) {
		this.ln = ln;
	}

	public String getBetSecret() {
		return betSecret;
	}

	public void setBetSecret(String betSecret) {
		this.betSecret = betSecret;
	}

	public String getWinSecret() {
		return winSecret;
	}

	public void setWinSecret(String winSecret) {
		this.winSecret = winSecret;
	}

	/**
	 * 中奖状态
	 */
	public static enum WinStatus {
		NO_LOTTERY, // 未派奖
		WIN, // 赢
		FAIL; // 输
	}

	/**
	 * 派奖状态
	 */
	public static enum AwardStatus {
		NO_LOTTERY, // 未派奖
		LOTTERY, // 已派奖
		UNLOTTERY; // 撤消派奖
	}

	/**
	 * 订单状态
	 */
	public static enum OrderStatus {
		NORMAL, // 正常
		UNCORD, // 撤单
		CANCEL, // 作废
		BETTING, // 投注中
		UNCORDING, // 撤单中
		REBATE, // 返点中
		PRIZE, // 派奖中
		CODE, // 打码中
		BETFAIL // 投注失败
	}

	/**
	 * 投注方式
	 */
	public static enum BetWay {
		ORDINARY, // 普通
		CHASE // 追号
	}

	/**
	 * 返点发放状态
	 */
	public static enum RebateStatus {
		NOT_DO, // 未发放
		ALREADY, // 已发放
		UNDO, // 撤消
		NONE // 试玩
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Date getBetTime() {
		return betTime;
	}

	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}

	public Integer getBetCount() {
		return betCount;
	}

	public void setBetCount(Integer betCount) {
		this.betCount = betCount;
	}

	public Double getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Double betAmount) {
		this.betAmount = betAmount;
	}

	public Double getBetTotal() {
		return betTotal;
	}

	public void setBetTotal(Double betTotal) {
		this.betTotal = betTotal;
	}

	public String getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(String betNumber) {
		this.betNumber = betNumber;
	}

	 
	public WinStatus getWinStatus() {
		return winStatus;
	}

	public void setWinStatus(WinStatus winStatus) {
		this.winStatus = winStatus;
	}

	public Integer getWinCount() {
		return winCount;
	}

	public void setWinCount(Integer winCount) {
		this.winCount = winCount;
	}

	public Double getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Double winAmount) {
		this.winAmount = winAmount;
	}

	public AwardStatus getAwardStatus() {
		return awardStatus;
	}

	public void setAwardStatus(AwardStatus awardStatus) {
		this.awardStatus = awardStatus;
	}

	public Date getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BetWay getBetWay() {
		return betWay;
	}

	public void setBetWay(BetWay betWay) {
		this.betWay = betWay;
	}

	public Integer getChaseRecordId() {
		return chaseRecordId;
	}

	public void setChaseRecordId(Integer chaseRecordId) {
		this.chaseRecordId = chaseRecordId;
	}

	public Integer getChaseIssueId() {
		return chaseIssueId;
	}

	public void setChaseIssueId(Integer chaseIssueId) {
		this.chaseIssueId = chaseIssueId;
	}

	public Integer getChaseNumberId() {
		return chaseNumberId;
	}

	public void setChaseNumberId(Integer chaseNumberId) {
		this.chaseNumberId = chaseNumberId;
	}

	public String getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public RebateStatus getBetRebateStatus() {
		return betRebateStatus;
	}

	public void setBetRebateStatus(RebateStatus betRebateStatus) {
		this.betRebateStatus = betRebateStatus;
	}

	public RebateStatus getAgentRebateStatus() {
		return agentRebateStatus;
	}

	public void setAgentRebateStatus(RebateStatus agentRebateStatus) {
		this.agentRebateStatus = agentRebateStatus;
	}

	public Double getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(Double rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public Integer getBetMultiple() {
		return betMultiple;
	}

	public void setBetMultiple(Integer betMultiple) {
		this.betMultiple = betMultiple;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public enum Device {
		COMPUTER, PHONE, PAD, OTHERS;
	}
}
