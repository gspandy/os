package n.table.dto.authc;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import n.core.dto.TransientDTO;
import n.entity.authc.Company;
import n.entity.authc.Layer;
import n.entity.authc.Role;
import n.entity.authc.User.AccountState;
import n.entity.authc.User.AccountType;

/**
 * 用户创建DTO
 * @author onsoul@qq.com 2016年8月15日 下午4:44:23
 */
public class CustomerCreateDTO extends TransientDTO<Long> {
	
	private static final long serialVersionUID = 8386343228166934690L;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_-]{6,16}$", message="账号格式不正确！长度必须在6-16位之间")
	private String account;
	/**
	 * 账户类型(0-访客，1-超级管理员，2-管理员，，3-客服)
	 */
	private AccountType accountType;
	
	private AccountState accountState = AccountState.normal;
	@NotBlank
	@Length(min = 3, max = 50, message="姓名长度必须为3-50个字符")
	private String userName;
	@NotBlank
	@Length(min = 3, max = 50, message="昵称长度必须为3-50个字符")
	private String nickName;
	
	private String password;
	
	private String passwordSalt;
	/**
	 * 自动分配(0不自动，1自动)
	 */
	private Boolean autoAllot;
	
	
	@NotNull(message="接待上限不能为空！")
	private Integer receiveNum;
	
	private Integer curReceiveNum = 0;
	
	private String email;
	
	private String mobile;
	
//	@NotNull
	private Company company;
	
	private Layer layer;

	private List<Role> roles;
	
	private String perms;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(Integer receiveNum) {
		this.receiveNum = receiveNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public Boolean getAutoAllot() {
		return autoAllot;
	}

	public void setAutoAllot(Boolean autoAllot) {
		this.autoAllot = autoAllot;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public Layer getLayer() {
		return layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	public AccountState getAccountState() {
		return accountState;
	}

	public void setAccountState(AccountState accountState) {
		this.accountState = accountState;
	}

	public Integer getCurReceiveNum() {
		return curReceiveNum;
	}

	public void setCurReceiveNum(Integer curReceiveNum) {
		this.curReceiveNum = curReceiveNum;
	}
	
}
