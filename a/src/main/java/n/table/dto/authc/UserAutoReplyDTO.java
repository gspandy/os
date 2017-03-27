package n.table.dto.authc;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import n.core.dto.TransientDTO;

/**
 * 用户昵称修改DTO
 * @author onsoul@qq.com 2016年8月15日 下午4:44:23
 */
public class UserAutoReplyDTO extends TransientDTO<Long> {
	
	private static final long serialVersionUID = 8386343228166934690L;
	@NotNull
	private Long id;
	@Length(min = 0, max = 1000)
	private String autoReply;
	
	
	public UserAutoReplyDTO(Long id, String autoReply) {
		super();
		this.id = id;
		this.autoReply = autoReply;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAutoReply() {
		return autoReply;
	}

	public void setAutoReply(String autoReply) {
		this.autoReply = autoReply;
	}


	


}
