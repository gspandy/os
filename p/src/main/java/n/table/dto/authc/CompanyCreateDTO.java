package n.table.dto.authc;

import javax.validation.constraints.NotNull;

import n.core.dto.TransientDTO;

/**
 * 公司表
 * 
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
public class CompanyCreateDTO extends TransientDTO<Integer> {

	private static final long serialVersionUID = 2988763393025411477L;

	/**
	 * 公司账户
	 */
	@NotNull
	private String companyAccount;
	/**
	 * 公司名称
	 */
	@NotNull(message="公司名称不能为空！")
	private String companyName;

	/**
	 * 公司电话
	 */
	private String telNum;

	/**
	 * 电子邮件
	 */
	private String email;

	/**
	 * 地址
	 */
	@NotNull
	private String address;
	/**
	 * 主页
	 */
	@NotNull
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

}
