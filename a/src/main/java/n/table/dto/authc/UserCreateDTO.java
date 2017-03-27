package n.table.dto.authc;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import n.core.dto.TransientDTO;
import n.entity.authc.Company;
import n.entity.authc.Role;
import n.entity.authc.User.AccountState;
import n.entity.authc.User.AccountType;

/**
 * 用户创建DTO
 * @author onsoul@qq.com 2016年8月15日 下午4:44:23
 */
public class UserCreateDTO extends TransientDTO<Long> {
	
	private static final long serialVersionUID = 8386343228166934690L;
	
	@NotBlank
	@Length(min = 6, max = 20)
	private String account;
	
	private AccountType accountType;
	
	private AccountState accountState = AccountState.normal;
	@NotBlank
	@Length(min = 3, max = 50)
	private String userName;
	@NotBlank
	@Length(min = 3, max = 50)
	private String nickName;
	
	@NotBlank
	@Length(min = 6, max = 50)
	private String password;
	@NotBlank
	@Length(min = 6, max = 50)
	private String repassword;
	
	private String passwordSalt;
	
	private Boolean autoAllot = Boolean.FALSE;
	
	private String email;
	
	private String mobile;
	
//	@NotNull
	private Company company;
	

	private List<Role> roles;
	
	
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

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public AccountState getAccountState() {
		return accountState;
	}

	public void setAccountState(AccountState accountState) {
		this.accountState = accountState;
	}

}
