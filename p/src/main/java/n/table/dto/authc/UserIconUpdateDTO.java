package n.table.dto.authc;

import org.hibernate.validator.constraints.NotBlank;

import com.esotericsoftware.kryo.NotNull;

import n.core.dto.TransientDTO;
/**
 * 头像修改dto
 * @author jt_wangshuiping @date 2016年12月5日
 *
 */
public class UserIconUpdateDTO extends TransientDTO<Long> {

	private static final long serialVersionUID = 5688046505359631787L;
	@NotNull
	private Long id;
	@NotBlank
	private String userIcon;
	
	public UserIconUpdateDTO() {
		super();
	}

	public UserIconUpdateDTO(Long id, String userIcon) {
		super();
		this.id = id;
		this.userIcon = userIcon;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	
}
