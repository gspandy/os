package n.table.dto.authc;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import n.core.dto.TransientDTO;

/**
 * 用户昵称修改DTO
 * @author onsoul@qq.com 2016年8月15日 下午4:44:23
 */
public class UserMobileUpdateDTO extends TransientDTO<Long> {
	
	private static final long serialVersionUID = 8386343228166934690L;
	@NotNull
	private Long id;
	@NotBlank
	private String mobile;
	
	
	public UserMobileUpdateDTO(Long id, String mobile) {
		super();
		this.id = id;
		this.mobile = mobile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
