package n.entity.authc;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.LastModifiedDate;

import n.core.entity.annotation.Checked;
import n.core.entity.support.CheckableEntity;

/**
 * 用户
 * 
 * @author jtwise
 * @date 2016年7月19日 下午7:15:27
 * @verion 1.0
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_USER")
public class User extends CheckableEntity<Long> {

	private static final long serialVersionUID = 2988763393025411477L;

	/**
	 * 用户ID/账户
	 */
	@Checked
	@Column(name = "ACCOUNT", length = 20, columnDefinition = "varchar(20)", nullable = false)
	private String account;
	/**
	 * 账户类型(0-访客，1-超级管理员，2-管理员，，3-客服)
	 */
	@Checked
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "ACCOUNT_TYPE", columnDefinition = "INT default 0", nullable = false)
	private AccountType accountType;
	/**
	 * 账户状态(0-正常，1-锁定，2-删除)
	 */
	@Checked
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "ACCOUNT_STATE", columnDefinition = "INT default 0", nullable = false)
	private AccountState accountState;
	/**
	 * 姓名
	 */
	@Checked
	@Column(name = "USER_NAME", length = 20, columnDefinition = "varchar(20)", nullable = true)
	private String userName;

	/**
	 * 昵称
	 */
	@Checked
	@Column(name = "NICK_NAME", length = 20, columnDefinition = "varchar(20)", nullable = true)
	private String nickName;

	/**
	 * 密码
	 */
	@Checked
	@Column(name = "PASSWORD", length = 20, columnDefinition = "varchar(50)")
	private String password;
	/**
	 * 头像
	 */
	@Column(name = "USER_ICON", columnDefinition = "varchar(250)")
	private String userIcon;
	
	/**
	 * 加盐
	 */
	@Checked
	@Column(name = "PASSWORD_SALT", columnDefinition = "varchar(32)", nullable = false)
	private String passwordSalt;

	/**
	 * 接待上限
	 */
	@Column(name = "RECEIVE_NUM", length = 20, columnDefinition = "INT", nullable = true)
	private Integer receiveNum;
	/**
	 * 当前接待上限
	 */
	@Column(name = "CUR_RECEIVE_NUM", length = 20, columnDefinition = "INT DEFAULT 0", nullable = true)
	private Integer curReceiveNum;
	/**
	 * 自动分配(0不自动，1自动)
	 */
	@Column(name = "AUTO_ALLOT", length = 20, columnDefinition = "TINYINT(2) DEFAULT 1", nullable = true)
	private Boolean autoAllot;
	/**
	 * 在线状态
	 */
	@Column(name = "ONLINE_STATE", length = 20, columnDefinition = "TINYINT(2) DEFAULT 0", nullable = true)
	@Enumerated(EnumType.ORDINAL)
	private OnlineState onlineState;

	/**
	 * 电子邮件
	 */
	@Checked
	@Column(name = "EMAIL", length = 20, columnDefinition = "varchar(25)", nullable = true)
	private String email;

	/**
	 * 电话
	 */
	@Checked
	@Column(name = "MOBILE", length = 20, columnDefinition = "varchar(20)", nullable = true)
	private String mobile;
	
	@Column(name = "AUTO_REPLY", columnDefinition = "varchar(1000)", nullable = true)
	private String autoReply;
	/**
	 * 登录ip
	 */
	@Checked
	@Column(name = "LOGIN_IP", length = 20, columnDefinition = "varchar(20)", nullable = true)
	private String loginIp;
	/**
	 * 最后登录ip
	 */
	@Checked
	@Column(name = "LAST_LOGIN_IP", length = 20, columnDefinition = "varchar(20)", nullable = true)
	private String lastLoginIp;
	/**
	 * 登录失败次数
	 */
	@Checked
	@Column(name = "LOGIN_FAILURE_TIMES", columnDefinition = "int", nullable = true)
	private String loginFailureTimes;
	

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "LAYER_ID")
	private Layer layer;
	/**
	 * 客户信息
	 */
	@ManyToOne
	@JoinColumn(name = "COMPANY_ID")
	private Company company;

	// foreignKey=@ForeignKey(name="FK_USER_ROLES")
	@ManyToMany(targetEntity = Role.class,fetch = FetchType.LAZY)
	private List<Role> roles;
	

	/**
	 * 最后登录时间
	 */
	@Column(name = "LAST_LOGIN_TIME")
	@Type(type = "com.hitler.core.entity.usertype.PersistentDateTimeAsMillisLong")
	private DateTime lastLoginTime;
	/**
	 * 登录时间
	 */
	@LastModifiedDate
	@Column(name = "LOGIN_TIME")
	@Type(type = "com.hitler.core.entity.usertype.PersistentDateTimeAsMillisLong")
	private DateTime loginTime;
	
	

	/**
	 * normal,//访客
	 * administrator,//超级管理员
	 * companyAdmin,//公司管理员
	 * services//客服
	 * @author jt_wangshuiping @date 2016年11月11日
	 *
	 */
	public enum AccountType{
		normal,//访客
		administrator,//超级管理员
		companyAdmin,//公司管理员
		services//客服
	}
	/**
	 * normal-正常，lock-锁定，del-删除
	 * @author jt_wangshuiping @date 2016年11月11日
	 *
	 */
	public enum AccountState{
		normal,
		lock,
		del
	}
	/**
	 * off-离线，online-在线，leave-离开
	 * @author jt_wangshuiping @date 2016年11月11日
	 *
	 */
	public enum OnlineState{
		off,//离线
		online,//在线
		leave//离开
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public DateTime getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(DateTime lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
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

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
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

	public String getLoginFailureTimes() {
		return loginFailureTimes;
	}

	public void setLoginFailureTimes(String loginFailureTimes) {
		this.loginFailureTimes = loginFailureTimes;
	}

	public DateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(DateTime loginTime) {
		this.loginTime = loginTime;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public Layer getLayer() {
		return layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	public Integer getCurReceiveNum() {
		return curReceiveNum;
	}

	public void setCurReceiveNum(Integer curReceiveNum) {
		this.curReceiveNum = curReceiveNum;
	}

	public String getAutoReply() {
		return autoReply;
	}

	public void setAutoReply(String autoReply) {
		this.autoReply = autoReply;
	}

}
