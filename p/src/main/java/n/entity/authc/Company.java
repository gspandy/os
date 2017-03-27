package n.entity.authc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import n.core.entity.annotation.Checked;
import n.core.entity.support.CheckableEntity;

/**
 * 公司表
 * 
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_COMPANY")
public class Company extends CheckableEntity<Integer> {

	private static final long serialVersionUID = 2988763393025411477L;

	/**
	 * 公司账户
	 */
	@Checked
	@Pattern(regexp = "^[a-zA-Z0-9_-]{6,16}$", message="客户id格式不正确！长度必须在6-16位之间")
	@Column(name = "COMPANY_ACCOUNT", length = 20, columnDefinition = "varchar(20)", nullable = false)
	private String companyAccount;
	
	/**
	 * 公司名称
	 */
	@Checked
	@NotNull(message = "公司名称不能为空！")
	@Column(name = "COMPANY_NAME", length = 50, columnDefinition = "varchar(50)", nullable = false)
	private String companyName;
	
	/**
	 * 公司唯一代码
	 */
	@Checked
	@Column(name = "CODE", length = 50, columnDefinition = "varchar(32)", unique=true , nullable = false)
	private String code;

	/**
	 * 公司电话
	 */
	@Checked
	@Pattern(regexp="^((\\d{3}-\\d{8}|\\d{4}-\\d{7,8})|(1[3|5|7|8][0-9]{9}))$", message = "联系方式格式不正确！")
	@Column(name = "TEL_NUM", length = 20, columnDefinition = "varchar(20)", nullable = true)
	private String telNum;

	/**
	 * 电子邮件
	 */
	@Checked
	@Column(name = "EMAIL", length = 25, columnDefinition = "varchar(25)", nullable = true)
	private String email;

	/**
	 * 地址
	 */
	@Checked
	@Column(name = "ADDRESS", length = 50, columnDefinition = "varchar(50)", nullable = true)
	private String address;
	
	/**
	 * 主页
	 */
	@Checked
	@Column(name = "WEB_SITE", length = 250, columnDefinition = "varchar(250)", nullable = true)
	private String webSite;
	
	/**
	 * 生成的链接地址
	 */
	@Column(name = "URL", columnDefinition = "varchar(50)", nullable = true)
	private String url;
	
	/**
	 * 其他状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "C_STATE", columnDefinition = "varchar(50) default 'normal'", nullable = true)
	private CState cState = CState.normal;
	
	/**
	 * 是否启用（1-启用，0-禁用）
	 */
	@Column(name = "IS_ENABLE", columnDefinition = "int default 1", nullable = true)
	private Boolean isEnable = Boolean.TRUE;

	/**
	 * 公司状态 normal-正常， del-删除
	 * @author jt_wangshuiping @date 2017年1月3日
	 *
	 */
	public enum CState{
		normal,
		del
	}
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
