package n.core.entity.security;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import n.entity.authc.User.AccountState;
import n.entity.authc.User.AccountType;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 * 
 * @author 
 * 
 */
public class ShiroUser implements Serializable {
	private static final long serialVersionUID = 1L;
	public Long id;
	public String nickname;
	public String account;
	public String password;
	public String salt;
	public AccountType accountType;
	public Integer companyId;
	public String userIcon;
	//账户状态
	public AccountState accountState;
	//邮箱
	public String email;
	//联系方式
	public String mobile;
	//登录ip
	public String loginIp;
	//登录时间
	public DateTime loginTime;
	//上一次登录ip
	public String lastLoginIp;
	//上一次登录时间
	public DateTime lastLoginTime;
	//所属公司
	public String companyName;
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public ShiroUser(Long id, String nickname, String account, String password, String salt, AccountType accountType,
			Integer companyId, String userIcon, AccountState accountState, String email, String mobile, String loginIp,
			DateTime loginTime, String lastLoginIp, DateTime lastLoginTime, String companyName) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.account = account;
		this.password = password;
		this.salt = salt;
		this.accountType = accountType;
		this.companyId = companyId;
		this.userIcon = userIcon;
		this.accountState = accountState;
		this.email = email;
		this.mobile = mobile;
		this.loginIp = loginIp;
		this.loginTime = loginTime;
		this.lastLoginIp = lastLoginIp;
		this.lastLoginTime = lastLoginTime;
		this.companyName = companyName;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return nickname;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "nickname");
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "nickname");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public AccountState getAccountState() {
		return accountState;
	}

	public void setAccountState(AccountState accountState) {
		this.accountState = accountState;
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

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public DateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(DateTime loginTime) {
		this.loginTime = loginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public DateTime getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(DateTime lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
