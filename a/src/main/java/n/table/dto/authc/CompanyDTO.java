package n.table.dto.authc;

import n.core.dto.PersistentDTO;
import n.entity.authc.Company.CState;

/**
 * 公司表
 * 
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
public class CompanyDTO extends PersistentDTO<Integer> {

	private static final long serialVersionUID = 2988763393025411477L;

	private Integer id;
	/**
	 * 公司账户
	 */
	private String companyAccount;
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 公司唯一代码
	 */
	private String code;

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
	private String address;
	
	/**
	 * 主页
	 */
	private String webSite;
	
	private CState cState;
	
	/**
	 * 是否启用（1-启用，0-禁用）
	 */
	private Boolean isEnable;

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

	public CState getcState() {
		return cState;
	}

	public void setcState(CState cState) {
		this.cState = cState;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
