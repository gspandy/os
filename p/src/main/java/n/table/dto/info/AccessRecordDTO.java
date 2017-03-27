package n.table.dto.info;

import java.util.Date;

import n.core.dto.PersistentDTO;
import n.entity.authc.Company;
import n.entity.authc.User;

/**
 * 访客记录DTO
 * 
 * @author jt_wangshuiping @date 2016年10月25日
 *
 */
public class AccessRecordDTO extends PersistentDTO<Integer> {

	private static final long serialVersionUID = -5792167605590087771L;

	@Override
	public Integer getId() {
		return id;
	}

	private Integer id;

	private User visitorId;

	private User customerId;

	private Boolean onlineState;

	private Company companyId;

	private String visitorIp;

	private Integer refuseTimes;

	private Date entryTime;

	private Date waitTime;

	private Date leaveTime;

	private String userName;

	private String lastMsg;

	public User getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(User visitorId) {
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
