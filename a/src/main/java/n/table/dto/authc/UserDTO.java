package n.table.dto.authc;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import n.core.dto.PersistentDTO;
import n.entity.authc.Company;
import n.entity.authc.Layer;
import n.entity.authc.User.AccountState;
import n.entity.authc.User.AccountType;
import n.entity.authc.User.OnlineState;

/**
 * 用户DTO
 * @author onsoul@qq.com
 * 2016年8月15日 下午3:59:07
 */
public class UserDTO  extends PersistentDTO<Long> {
 
	private static final long serialVersionUID = -7915066523824664077L;

	private Long id;
	
	/**
	 * 用户ID/账户
	 */
	private String account;
	/**
	 * 账户类型(0-访客，1-客服，2-管理员，3-超级管理员)
	 */
	private AccountType accountType;
	/**
	 * 账户状态(0-正常，1-锁定，2-删除)
	 */
	private AccountState accountState;
	/**
	 * 姓名
	 */
	private String userName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 接待上限
	 */
	private Integer receiveNum;
	/**
	 * 自动分配(默认0自动，1不自动)
	 */
	private Boolean autoAllot;
	/**
	 * 在线状态
	 */
	private OnlineState onlineState;

	/**
	 * 电子邮件
	 */
	private String email;

	/**
	 * 电话
	 */
	private String mobile;
	/**
	 * 登录ip
	 */
	private String loginIp;
	/**
	 * 最后登录ip
	 */
	private String lastLoginIp;
	@Temporal(TemporalType.TIMESTAMP)
	private DateTime lastLoginTime;
	/**
	 * 登录时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private DateTime loginTime;
	/**
	 * 客户信息
	 */
	private Company company;
	/**
	 * 用户头像
	 */
	private String userIcon;
	
	private String autoReply;
	
	private String isShare;//1 共享了  其它未共享
	
	private Layer layer;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Boolean getAutoAllot() {
		return autoAllot;
	}

	public void setAutoAllot(Boolean autoAllot) {
		this.autoAllot = autoAllot;
	}

	public OnlineState getOnlineState() {
		return onlineState;
	}

	public void setOnlineState(OnlineState onlineState) {
		this.onlineState = onlineState;
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

	public DateTime getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(DateTime lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public AccountState getAccountState() {
		return accountState;
	}

	public void setAccountState(AccountState accountState) {
		this.accountState = accountState;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public DateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(DateTime loginTime) {
		this.loginTime = loginTime;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getAutoReply() {
		return autoReply;
	}

	public void setAutoReply(String autoReply) {
		this.autoReply = autoReply;
	}

	public String getIsShare() {
		return isShare;
	}

	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}

	public Layer getLayer() {
		return layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

}
