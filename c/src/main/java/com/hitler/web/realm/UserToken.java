package com.hitler.web.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 4038540112578211319L;
	
	private String browserVersion; // 浏览器版本
	private String loginIp; // 登录IP
	private String sessionId; // 当前SESSIONID
	private String loginPassword; // 登录密码
	private boolean isBackPage;   //是否是后台界面登录
//	private Device device;        //登陆设备

	public UserToken() {
		super();
	}

	public UserToken(String username, String password,
			boolean rememberMe, String browserVersion, String loginIp,
			String sessionId, boolean isBackPage) {
		super(username, password, rememberMe);
		this.loginPassword = password;
		this.browserVersion = browserVersion;
		this.loginIp = loginIp;
		this.sessionId = sessionId;
		this.isBackPage = isBackPage;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public boolean isBackPage() {
		return isBackPage;
	}

	public void setBackPage(boolean isBackPage) {
		this.isBackPage = isBackPage;
	}
	
	@Override
	public void clear() {
		super.clear();
		this.browserVersion = null;
		this.loginIp = null;
		this.loginPassword = null;
	}
}
