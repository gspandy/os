package n.table.dto.sider;

import org.hibernate.validator.constraints.NotBlank;

import n.core.dto.TransientDTO;
import n.entity.sider.PromptConfig.PromptType;

public class PromptConfigCreateDTO extends TransientDTO<Integer> {

	private static final long serialVersionUID = 6518257143734278647L;
	@NotBlank(message="内容不能为空！")
	private String content;
	@NotBlank
	private PromptType type;
	private String time;
	
	
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
