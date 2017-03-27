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

import n.core.entity.annotation.Checked;
import n.core.entity.support.PersistableEntity;

/**
 * 期号
 * 
 * @author onsoul
 *
 */

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_LN", indexes = { 
	@Index(name = "IDX_GAME_ID", columnList = "GAME_ID"),
	@Index(name = "IDX_ISSUE", columnList = "ISSUE")
})

public class LN extends PersistableEntity<Long> {

	private static final long serialVersionUID = -1006569943661487697L;

	@Checked
	@ManyToOne
	@JoinColumn(name = "GAME_ID")
	private Game game;

	/**
	 * 期号
	 */
	@Checked
	@Column(name = "ISSUE", columnDefinition = "varchar(20)", nullable = false)
	private String issue;

	/**
	 * 开盘时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPEN_TIME", columnDefinition = "TIMESTAMP", nullable = true)
	private Date openTime;

	/**
	 * 封盘时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CLOSE_TIME", columnDefinition = "TIMESTAMP", nullable = true)
	private Date closeTime;

	/**
	 * 预定开奖时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SCHEDULED_TIME", columnDefinition = "TIMESTAMP", nullable = true)
	private Date scheduledTime;

	/**
	 * 实际开奖时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOTTERY_TIME", columnDefinition = "TIMESTAMP", nullable = true)
	private Date lotteryTime;

	/**
	 * 开奖号码
	 */
	@Checked
	@Column(name = "LOTTERY_NUMBER", columnDefinition = "varchar(96)")
	private String lotteryNumber = "";

	/**
	 * 备注
	 */
	@Checked
	@Column(name = "REMARK", columnDefinition = "varchar(24)")
	private String remark;

	/**
	 * 开奖方式
	 */
	@Checked
	@Enumerated(EnumType.STRING)
	@Column(name = "LOTTERY_TYPE", columnDefinition = "varchar(8)", nullable = false)
	private LotteryType lotteryType = LotteryType.AUTO;

	/**
	 * 状态
	 */
	@Checked
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "STATUS", columnDefinition = "TINYINT(2)", nullable = false)
	private Status status = Status.NOT_OPEN;

	 /**
     * 最大投注额
     */
	@Checked
	@Column(name = "BET_AMOUNT_MAX", columnDefinition="DECIMAL(15,5) DEFAULT 0.0")
    private Double betAmountMax;
	
    /**
     * 注单数
     */
	@Checked
	@Column(name = "BET_ORDERS", columnDefinition="INT")
    private Integer betOrders;

    /**
     * 投注总额
     */
	@Checked
	@Column(name = "BET_MONEY_TOTAL", columnDefinition="DECIMAL(15,5) DEFAULT 0.0")
    private Double betMoneyTotal;
    
    /**
     * 中奖注单数
     */
	@Checked
	@Column(name = "WIN_ORDERS")
    private Integer winOrders;

    /**
     * 中奖总额
     */
	@Checked
	@Column(name = "WIN_MONEY_TOTAL", columnDefinition="DECIMAL(15,5) DEFAULT 0.0")
    private Double winMoneyTotal;
    
    /**
     * 返点总额(投注返点+代理返点)
     */
	@Checked
	@Column(name = "REBATE_MONEY_TOTAL", columnDefinition="DECIMAL(15,5) DEFAULT 0.0")
    private Double rebateMoneyTotal;
	
	/**
	 * 操作者
	 */
	@Checked
	@Column(name = "OPERATOR", columnDefinition="varchar(20)") 
	private String operator;
	
	/**
	 * 操作时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPERATE_TIME", columnDefinition="TIMESTAMP")
	private Date operateTime;
	
	/**
	 * 开奖号码采集来源
	 */
	@Column(name = "CRAWLER_URL_DESCRIPTION", columnDefinition = "varchar(20)")
	private String crawlerUrlDescription;
	
	/**
	 * 开奖号码采集耗时
	 */
	@Checked
	@Column(name = "CRAWLER_TIME_COST", columnDefinition="INT DEFAULT 0")
	private Integer crawlerTimeCost;
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Date getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Date scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public Date getLotteryTime() {
		return lotteryTime;
	}

	public void setLotteryTime(Date lotteryTime) {
		this.lotteryTime = lotteryTime;
	}

	public String getLotteryNumber() {
		return lotteryNumber;
	}

	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Double getBetAmountMax() {
		return betAmountMax;
	}

	public void setBetAmountMax(Double betAmountMax) {
		this.betAmountMax = betAmountMax;
	}

	public Integer getBetOrders() {
		return betOrders;
	}

	public void setBetOrders(Integer betOrders) {
		this.betOrders = betOrders;
	}

	public Double getBetMoneyTotal() {
		return betMoneyTotal;
	}

	public void setBetMoneyTotal(Double betMoneyTotal) {
		this.betMoneyTotal = betMoneyTotal;
	}

	public Integer getWinOrders() {
		return winOrders;
	}

	public void setWinOrders(Integer winOrders) {
		this.winOrders = winOrders;
	}

	public Double getWinMoneyTotal() {
		return winMoneyTotal;
	}

	public void setWinMoneyTotal(Double winMoneyTotal) {
		this.winMoneyTotal = winMoneyTotal;
	}

	public Double getRebateMoneyTotal() {
		return rebateMoneyTotal;
	}

	public void setRebateMoneyTotal(Double rebateMoneyTotal) {
		this.rebateMoneyTotal = rebateMoneyTotal;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getCrawlerUrlDescription() {
		return crawlerUrlDescription;
	}

	public void setCrawlerUrlDescription(String crawlerUrlDescription) {
		this.crawlerUrlDescription = crawlerUrlDescription;
	}

	public Integer getCrawlerTimeCost() {
		return crawlerTimeCost;
	}

	public void setCrawlerTimeCost(Integer crawlerTimeCost) {
		this.crawlerTimeCost = crawlerTimeCost;
	}

	public enum Status {
		NOT_OPEN,		//未开盘
		OPEN,			//开盘
		CLOSED,         //封盘
		NOT_LT,			//未开奖
		LT_ED,         	//已开奖
		NOT_PRIZE,      //未派奖
		PRIZE,			//派奖中
		UNDO_PRIZE,     //撤消派奖
		LT_FAIL,        //开奖失败
		UNDO_REBATE,    //撤消返点
		UNDO_LN			//本期作废
		
	}

	public enum LotteryType {
		MANUAL, AUTO // 手动，自动
	}

}
