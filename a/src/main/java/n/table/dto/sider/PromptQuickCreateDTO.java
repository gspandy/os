package n.table.dto.sider;

import org.hibernate.validator.constraints.NotBlank;

import n.core.dto.TransientDTO;
import n.entity.sider.PromptConfig.PromptType;

public class PromptQuickCreateDTO extends TransientDTO<Integer> {

	private static final long serialVersionUID = 6518257143734278667L;
	@NotBlank(message="内容不能为空！")
	private String content;
	
	private PromptType type = PromptType.quick;
	
	private Long userId;
	
	private String userAccount;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public PromptType getType() {
		return type;
	}
	public void setType(PromptType type) {
		this.type = type;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
}
