package n.table.dto.info;

import java.util.Date;

/**
 * 离线消息
 * 
 * @author jt_wangshuiping @date 2017年1月21日
 *
 */
public class LeaveMsgDTO {

	private String content;
	private Date sendTime;

	
	public LeaveMsgDTO() {
		super();
	}

	public LeaveMsgDTO(String content, Date sendTime) {
		super();
		this.content = content;
		this.sendTime = sendTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}
