package n.table.dto.authc;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import n.core.dto.TransientDTO;

/**
 * 公司表
 * 
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
public class CompanyUpdateDTO extends TransientDTO<Integer> {

	private static final long serialVersionUID = 2988763393025411477L;
	
	@NotNull(message = "id不能为空！")
	private Integer id;
	/**
	 * 公司账户
	 */
	@Pattern(regexp = "^[a-zA-Z0-9_-]{6,16}$", message="客户id格式不正确！长度必须在6-16位之间")
	private String companyAccount;
	/**
	 * 公司名称
	 */
	@NotNull(message="公司名称不能为空！")
	private String companyName;

	/**
	 * 公司电话
	 */
	@NotNull
	@Pattern(regexp="^((\\d{3}-\\d{8}|\\d{4}-\\d{7,8})|(1[3|5|7|8][0-9]{9}))$", message = "电话号码格式不正确！")
	private String telNum;

	/**
	 * 电子邮件
	 */
//	@NotNull
	private String email;

	/**
	 * 地址
	 */
	@NotNull(message="地址不能为空！")
	private String address;
	/**
	 * 主页
	 */
	@NotNull(message="主页信息不能为空！")
	private String webSite;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
