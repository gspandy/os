package com.hitler.web.realm;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.hitler.entity.authc.User.OnlineState;
import com.hitler.service.authc.IUserService;
import com.hitler.web.Global;

public class ShiroSessionListener implements SessionListener {

	private static Logger logger = LoggerFactory.getLogger(ShiroSessionListener.class);

	@Resource
	private IUserService userService;

	@Override
	public void onStart(Session session) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStop(Session session) {
		clearAuth(session);
	}

	@Override
	public void onExpiration(Session session) {
		clearAuth(session);
	}

	/**
	 * 清除权限缓存，更改账号状态
	 */
	public void clearAuth(Session currentSession) {
		Object uid = currentSession.getAttribute(Global.USERID);

		if (null != uid && !"".equals(uid.toString())) {
			Long id = (Long) uid;
			String cSID = currentSession.getId().toString();
			String oldSID = Global.sessionMap.get(id);
			if (StringUtils.isEmpty(oldSID) || cSID.equals(oldSID)) {
				try {
					Global.sessionMap.remove(id);
					userService.online(userService.find(id), OnlineState.off);
					userService.clearCurReceiveNum(id);// 清空当前分配游客数//redis缓存的数据先不处理
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("", e);
				}
				logger.info("### userid:{} 清除权限缓存, 更新状态为离线", id);
			}
		}
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
