package n.table.dto.authc;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import n.core.dto.TransientDTO;

/**
 * 用户基本资料修改DTO
 * @author onsoul@qq.com 2016年8月15日 下午4:44:23
 */
public class UserBaseUpdateDTO extends TransientDTO<Long> {
	
	private static final long serialVersionUID = 8386343228166934690L;
	
	private Long id;
	@NotBlank
	@Length(min = 3, max = 50)
	private String nickName;
	
	@Pattern(regexp="^((\\d{3}-\\d{8}|\\d{4}-\\d{7,8})|(1[3|5|7|8][0-9]{9}))$", message = "电话号码格式不正确！")
	private String mobile;
	
	@Email(message="邮箱格式不正确！")
	private String email;
	
	public UserBaseUpdateDTO() {
		
	}

	public UserBaseUpdateDTO(Long id, String nickName, String mobile, String email) {
		super();
		this.id = id;
		this.nickName = nickName;
		this.mobile = mobile;
		this.email = email;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
