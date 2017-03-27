package n.table.dto.sider;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import n.core.dto.TransientDTO;

public class PromptQuickUpdateDTO extends TransientDTO<Integer>  {

	private static final long serialVersionUID = 6518257143734278649L;
	@NotNull
	private Integer id;
	@NotBlank(message="内容不能为空！")
	private String content;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
