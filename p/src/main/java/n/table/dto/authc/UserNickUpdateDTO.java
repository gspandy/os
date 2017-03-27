package n.table.dto.authc;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import n.core.dto.TransientDTO;

/**
 * 用户昵称修改DTO
 * @author onsoul@qq.com 2016年8月15日 下午4:44:23
 */
public class UserNickUpdateDTO extends TransientDTO<Long> {
	
	private static final long serialVersionUID = 8386343228166934690L;
	@NotNull
	private Long id;
	@NotBlank
	@Length(min = 3, max = 50)
	private String nickName;
	
	
	public UserNickUpdateDTO(Long id, String nickName) {
		super();
		this.id = id;
		this.nickName = nickName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


}
