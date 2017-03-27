package n.table.dto.authc;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.esotericsoftware.kryo.NotNull;

import n.core.dto.TransientDTO;
/**
 * 更新自己DTO
 * @author jt_wangshuiping @date 2016年12月13日
 *
 */
public class UselfUpdateDTO extends TransientDTO<Long> {

	private static final long serialVersionUID = 2903766045507559936L;

	@NotNull
	private Long id;
	@NotBlank
	@Length(min = 3, max = 50)
	private String userName;
	@NotBlank
	@Length(min = 3, max = 50)
	private String nickName;
	
	private String mobile;
	
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
