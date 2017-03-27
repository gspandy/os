package n.table.dto.play;

import java.util.Date;

import n.core.dto.PersistentDTO;
import n.core.jutils.bean.BillNoUtils;
import n.entity.play.LN;
import n.entity.play.PlayType;
import n.entity.play.BetRecord.AwardStatus;
import n.entity.play.BetRecord.BetWay;
import n.entity.play.BetRecord.Device;
import n.entity.play.BetRecord.OrderStatus;
import n.entity.play.BetRecord.RebateStatus;
import n.entity.play.BetRecord.WinStatus;

public class BetRecordDTO extends PersistentDTO<Long> {

	private static final long serialVersionUID = -6372566394134679196L;

	@Override
	public Long getId() {
		return null;
	}

	/** * 投注单号 */
	private String billNo = BillNoUtils.generateBillNo(BillNoUtils.PREFIX_TZ);

	/** * 彩种期号ID */
	private LN ln;

	/** * 玩法类型 */
	private PlayType playType;
	private Date betTime = new Date();
	
	private String betNumber;
	/** * 投注注数 */
	private Integer betCount;
	/** * 单注金额 - 2.00  */
	private Double betAmount;
	
	/** * 投注倍数 */
	private Integer betMultiple;

	/**  * 投注总额 - 4.00 */
	private Double betTotal;

	/**  * 中奖状态 */
	private WinStatus winStatus = WinStatus.NO_LOTTERY;

	/** * 中奖注数 */
	private Integer winCount = 0;

	/**  * 中奖金额 */
	private Double winAmount = 0D;

	/**  * 中奖信息加密 MD5(中奖注数+中奖金额) MD5(WIN_COUNT+WIN_AMOUNT) */
	private String winSecret;

	/** * 派奖状态 */
	private AwardStatus awardStatus = AwardStatus.NO_LOTTERY;

	/** * 派奖时间 */
	private Date awardTime;

	/** * 订单状态  */
	private OrderStatus orderStatus = OrderStatus.NORMAL; // 正常

	/** * 投注方式 */
	private BetWay betWay;

	/** * 追号记录ID */
	private Integer chaseRecordId = 0;

	/** * 追号期号ID */
	private Integer chaseIssueId = 0;

	/** * 追号号码ID */
	private Integer chaseNumberId = 0;

	/** * 开奖号码 */
	private String lotteryNumber = "";

	/** * 投注返点派发状态  */
	private RebateStatus betRebateStatus = RebateStatus.NOT_DO;

	/** * 投注返点金额 */
	private Double rebateAmount = 0D;

	/**  * 代理返点派发状态 */
	private RebateStatus agentRebateStatus = RebateStatus.NOT_DO;

	/**  * 投注设备 */
	private Device device = Device.COMPUTER;

	/** * 操作者(撤单者） */
	private String lastModifiedBy;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public LN getLn() {
		return ln;
	}

	public void setLn(LN ln) {
		this.ln = ln;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	public Date getBetTime() {
		return betTime;
	}

	public void setBetTime(Date betTime) {
		this.betTime = betTime;
	}

	public String getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(String betNumber) {
		this.betNumber = betNumber;
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

	public Integer getBetMultiple() {
		return betMultiple;
	}

	public void setBetMultiple(Integer betMultiple) {
		this.betMultiple = betMultiple;
	}

	public Double getBetTotal() {
		return betTotal;
	}

	public void setBetTotal(Double betTotal) {
		this.betTotal = betTotal;
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

	public String getWinSecret() {
		return winSecret;
	}

	public void setWinSecret(String winSecret) {
		this.winSecret = winSecret;
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

	public Double getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(Double rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public RebateStatus getAgentRebateStatus() {
		return agentRebateStatus;
	}

	public void setAgentRebateStatus(RebateStatus agentRebateStatus) {
		this.agentRebateStatus = agentRebateStatus;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

}
