package n.entity.info;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import n.core.entity.annotation.Checked;
import n.core.entity.support.PersistableEntity;

/**
 * 消息记录表
 * 
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_MESSAGE_RECORD")
public class MessageRecord extends PersistableEntity<Integer> {

	private static final long serialVersionUID = 3034432338244912290L;

	/**
	 * 发送人id
	 */
	@Checked
	@Column(name = "FROM_ID", columnDefinition = "bigint", nullable = true)
	private Long fromId;
	/**
	 * 发送人
	 */
	@Checked
	@Column(name = "FROM_USER", columnDefinition = "varchar(20)", nullable = false)
	private String fromUser;

	/**
	 * 用户昵称
	 */
	@Column(name = "FROM_NICK_NAME", columnDefinition = "varchar(20)", nullable = false)
	private String fromNickName;

	/**
	 * 客服id
	 */
	@Checked
	@Column(name = "USER_ID", columnDefinition = "bigint", nullable = false)
	private Long userId;

	/**
	 * 消息内容
	 */
	@Column(name = "MSG_CONTENT", columnDefinition = "LONGTEXT", nullable = false)
	private String msg;

	/**
	 * 是否已读（默认0-未读，1-已读）
	 */
	@Checked
	@Column(name = "READ_STATE", columnDefinition = "TINYINT(2) DEFAULT 0")
	private Boolean status = Boolean.FALSE;

	/**
	 * 消息类型(默认0-字符串消息，1-图片)
	 */
	@Checked
	@Column(name = "MSG_TYPE", columnDefinition = "TINYINT(2) DEFAULT 0")
	private Boolean msgType = Boolean.FALSE;

	/**
	 * 发送消息时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SEND_TIME", columnDefinition = "TIMESTAMP NULL")
	private Date sendTime;
	
	@Column(name="COMPANY_ID",columnDefinition="int", nullable = false)
	private Integer companyId;

	@Column(name="VISITOR_IP",columnDefinition="varchar(50)", nullable = false)
	private String visitorIp;
	

	
	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getFromNickName() {
		return fromNickName;
	}

	public void setFromNickName(String fromNickName) {
		this.fromNickName = fromNickName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Boolean getMsgType() {
		return msgType;
	}

	public void setMsgType(Boolean msgType) {
		this.msgType = msgType;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getVisitorIp() {
		return visitorIp;
	}

	public void setVisitorIp(String visitorIp) {
		this.visitorIp = visitorIp;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}
	

}
