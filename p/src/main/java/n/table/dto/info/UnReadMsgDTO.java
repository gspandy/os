package n.table.dto.info;
/**
 * 所有未读消息实体
 * @author jt_wangshuiping @date 2017年1月9日
 *
 */
public class UnReadMsgDTO {

	private Integer size;
	
	private String fromUserName;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	
	
}
