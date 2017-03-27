package n.table.dto.authc;

import org.hibernate.validator.constraints.NotBlank;

import com.esotericsoftware.kryo.NotNull;

import n.core.dto.TransientDTO;
/**
 * 密码修改dto
 * @author jt_wangshuiping @date 2016年12月5日
 *
 */
public class UserPasswordDTO extends TransientDTO<Long> {

	private static final long serialVersionUID = 2708531528207387465L;
	@NotNull
	private Long id;
	@NotBlank
	private String password;
	
	public UserPasswordDTO() {
		super();
	}
	public UserPasswordDTO(Long id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
