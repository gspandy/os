package n.entity.info;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.annotation.Checked;
import n.core.entity.support.PersistableEntity;
import n.entity.authc.Company;
import n.entity.authc.User;

/**
 * 访客记录表
 * 
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_ACCESS_RECORD")
public class AccessRecord extends PersistableEntity<Integer> {

	private static final long serialVersionUID = 3034432338244912290L;

	/**
	 * 访客ID
	 */
	@Checked
	@Column(name = "VISITOR_ID", columnDefinition = "varchar(32)", nullable = false)
	private String visitorId;
	
	/**
	 * 用户名称
	 */
	@Checked
	@JoinColumn(name = "USER_NAME", columnDefinition = "varchar(20)")
	private String userName;
	/**
	 * 客服ID
	 */
	@Checked
	@OneToOne
	@JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "id")
	private User customerId;

	/**
	 * 在线状态（0-不在线，1-在线）
	 */
	@Column(name = "ONLINE_STATE", columnDefinition = "TINYINT(2)")
	private Boolean onlineState;

	/**
	 * 来源（公司ID）
	 */
	@Checked
	@OneToOne
	@JoinColumn(name = "COMPANY_ID", referencedColumnName = "id", columnDefinition = "INT")
	private Company companyId;

	/**
	 * 访客IP
	 */
	@Column(name = "VISITOR_IP", columnDefinition = "varchar(20)", nullable = false)
	private String visitorIp;

	/**
	 * 拒绝次数
	 */
	@Checked
	@Column(name = "REFUSE_TIMES", columnDefinition = "INT")
	private Integer refuseTimes;

	/**
	 * 进入时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENTRY_TIME", columnDefinition = "TIMESTAMP NULL")
	private Date entryTime;
	/**
	 * 等待时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "WAIT_TIME", columnDefinition = "TIMESTAMP NULL")
	private Date waitTime;
	/**
	 * 离开时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LEAVE_TIME", columnDefinition = "TIMESTAMP NULL")
	private Date leaveTime;
	/**
	 * 最后消息
	 */
	@Column(name = "LAST_MSG", columnDefinition = "LONGTEXT")
	private String lastMsg;

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public User getCustomerId() {
		return customerId;
	}

	public void setCustomerId(User customerId) {
		this.customerId = customerId;
	}

	public Boolean getOnlineState() {
		return onlineState;
	}

	public void setOnlineState(Boolean onlineState) {
		this.onlineState = onlineState;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public String getVisitorIp() {
		return visitorIp;
	}

	public void setVisitorIp(String visitorIp) {
		this.visitorIp = visitorIp;
	}

	public Integer getRefuseTimes() {
		return refuseTimes;
	}

	public void setRefuseTimes(Integer refuseTimes) {
		this.refuseTimes = refuseTimes;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public Date getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(Date waitTime) {
		this.waitTime = waitTime;
	}

	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastMsg() {
		return lastMsg;
	}

	public void setLastMsg(String lastMsg) {
		this.lastMsg = lastMsg;
	}

}
