package n.table.dto.authc;

import java.util.Date;

import n.core.dto.PersistentDTO;

public class MessageRecordDto extends PersistentDTO<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 976354742245594831L;
	private Integer id;
	private String msg;
	private Date sendTime;
	private String fromUser;
	private Boolean msgType;
	private String userName;
	private Integer companyId;
	private Long userId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public Boolean getMsgType() {
		return msgType;
	}

	public void setMsgType(Boolean msgType) {
		this.msgType = msgType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	

	

}
