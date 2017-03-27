package com.hitler.web.controller.oc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.hitler.core.entity.security.ShiroUser;
import com.hitler.core.jutils.base.RegexUtils;
import com.hitler.core.jutils.bean.BeanMapper;
import com.hitler.core.jutils.date.DateUtils;
import com.hitler.core.web.controller.GenericController;
import com.hitler.entity.authc.Layer;
import com.hitler.entity.authc.User;
import com.hitler.entity.authc.User.AccountType;
import com.hitler.entity.info.MessageRecord;
import com.hitler.io.topic.Topic;
import com.hitler.service.authc.IUserService;
import com.hitler.service.info.IMessageRecordService;
import com.hitler.table.dto.authc.MessageRecordDto;
import com.hitler.table.dto.authc.UserAutoReplyDTO;
import com.hitler.table.dto.authc.UserCreateDTO;
import com.hitler.table.dto.authc.UserDTO;
import com.hitler.table.dto.authc.UserIconUpdateDTO;
import com.hitler.table.dto.authc.UserMobileUpdateDTO;
import com.hitler.table.dto.authc.UserNickUpdateDTO;
import com.hitler.table.dto.authc.UserPasswordDTO;
import com.hitler.table.dto.info.LeaveMsgDTO;
import com.hitler.table.dto.info.LeaveMsgNum;
import com.hitler.web.Global;
import com.hitler.web.realm.SaltUtils;
import com.hitler.web.realm.UserToken;

/**
 * 用户控制器
 * 
 * @author onsoul@qq.com 2016年8月15日 下午4:42:42
 */
@Controller
@RequestMapping("oc/jt-user")
public class JTUserController extends GenericController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IMessageRecordService messageRecordService;

	@Resource
	private Topic topic;

	/*
	 * @Autowired private IDistributedSession distributedSession;
	 */

	// 注册
	@RequestMapping("/signup")
	@ResponseBody
	public Map<String, Object> signup(@Valid UserCreateDTO userCreateDTO) {
		Map<String, Object> map = new HashMap<>();
		if (!userCreateDTO.getRepassword().equals(userCreateDTO.getPassword())) {
			map.put("msg", "两次密码不一致。！");
			map.put("code", false);
			return map;
		}
		if (userService.findByAccount(userCreateDTO.getAccount()) != null) {
			map.put("msg", "该账号已存在！");
			map.put("code", false);
			return map;
		}
		try {
			userCreateDTO.setPasswordSalt(SaltUtils.getSalt(userCreateDTO.getAccount(), userCreateDTO.getPassword()));
			userCreateDTO
					.setPassword(SaltUtils.encodeMd5Hash(userCreateDTO.getPassword(), userCreateDTO.getPasswordSalt()));
			userCreateDTO.setAccountType(AccountType.normal);
			userService.register(userCreateDTO);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "注册失败！");
			map.put("code", false);
			return map;
		}
		map.put("msg", "注册成功！");
		map.put("code", true);
		return map;
	}

	// 登录
	@RequestMapping("/signin")
	@ResponseBody
	public boolean signin(UserToken token, HttpServletRequest request) {
		try {
			log.info("user {} singin...", token.getUsername());
			// TODO 还原加密、认证、授权
			// 获取ip
			String ip = request.getRemoteAddr();
			String header = request.getHeader("User-Agent");
			StringTokenizer st = new StringTokenizer(header, ";");
			String userBrower = st.nextToken();
			String userOs = st.nextToken();
			System.out.println("### remoteBrower:"+userBrower+"   Os:"+userOs);
			
			token.setLoginIp(ip);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			if (subject.isAuthenticated()) {
				Session session = subject.getSession();
				ShiroUser u = (ShiroUser) subject.getPrincipal();
				/*
				 * SessionInfo sinfo = distributedSession.make(u.getId(),
				 * request.getLocalAddr(), ip, request.getSession().getId());
				 * distributedSession.shared("S"+u.getId(), sinfo);
				 */
				session.setAttribute(Global.USERID, u.id);
				String uSID = session.getId().toString();
				String oldSID = Global.sessionMap.get(u.id);
				if (StringUtils.isNotEmpty(oldSID) && !uSID.equals(oldSID)) {
					// sessionId不同，发送唯一在线消息
					/*Protocol only = MakeMsg.onlyOline(u.id, u.nickname);
					topic.production(only, TopicName.USER_STATUS_);*/
					log.info("用户{}多地点登录，实施唯一在线", u.account);
				}
				Global.sessionMap.put(u.id, uSID);
				return true;
			}
		} catch (Exception e) {
			log.info("##user {} login error.", token.getUsername());
			return false;
		}
		return false;
	}

	// 查看用户信息
	@RequestMapping("/info/{id}")
	// @RequiresRoles("services")
	@ResponseBody
	public Object info(@PathVariable Long id) {
		log.info("### get user [ {} ] info.", id);
		if (RegexUtils.isInteger("" + id)) {
			return userService.findByID(id);
		}
		return null;
	}

	@RequestMapping(value = "/info")
	@ResponseBody
	public UserDTO self() {
		Subject sub = SecurityUtils.getSubject();
		ShiroUser su = (ShiroUser) sub.getPrincipal();
		log.info("### get login user [ {} ] info.", su.account);
		return userService.findByID(su.id);
	}

	@RequestMapping(value = "/update/info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateInfo(String oldData, String newData, Integer type) {
		Map<String, Object> map = Maps.newHashMap();
		Subject sub = SecurityUtils.getSubject();
		ShiroUser su = (ShiroUser) sub.getPrincipal();
		if (null == su) {
			map.put("msg", "登录超时！");
			map.put("code", false);
			return map;
		}
		switch (type) {
		case 1:// nickName
			if (null != newData && !"".equals(newData)) {
				if(!RegexUtils.find(newData, "/^.{3,16}$/")){
					map.put("msg", "昵称长度必须为3-16个字符！");
					map.put("code", false);
					return map;
				}
				UserNickUpdateDTO dto = new UserNickUpdateDTO(su.id, newData);
				try {
					userService.update(dto);
				} catch (Exception e) {
					e.printStackTrace();
					map.put("msg", "更新昵称失败！");
					map.put("code", false);
					return map;
				}
			}
			break;
		case 2:// mobile
			if (null != newData && !"".equals(newData)) {
				UserMobileUpdateDTO dto = new UserMobileUpdateDTO(su.id, newData);
				try {
					userService.update(dto);
				} catch (Exception e) {
					e.printStackTrace();
					map.put("msg", "更新手机号失败！");
					map.put("code", false);
					return map;
				}
			}
			break;
		case 3:// autoReply
			if (null != newData && !"".equals(newData)) {
				if(!RegexUtils.find(newData, "/^.{0,1000}$/")){
					map.put("msg", "自动回复内容不能超过1000个字符！");
					map.put("code", false);
					return map;
				}
				UserAutoReplyDTO dto = new UserAutoReplyDTO(su.id, newData);
				try {
					userService.update(dto);
				} catch (Exception e) {
					e.printStackTrace();
					map.put("msg", "更新自动回复失败！");
					map.put("code", false);
					return map;
				}
			}
			break;
		default:
			break;
		}

		map.put("msg", "更新成功！");
		map.put("code", true);
		return map;
	}

	/**
	 * 修改密码
	 * 
	 * @param account
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@RequestMapping(value = "/update/pwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePwd(@RequestParam String oldPwd, @RequestParam String newPwd,
			@RequestParam String reqNewPwd) {
		Map<String, Object> map = Maps.newHashMap();
		Subject sub = SecurityUtils.getSubject();
		ShiroUser su = (ShiroUser) sub.getPrincipal();
		if (null == su) {
			map.put("msg", "登录超时！");
			map.put("code", false);
			return map;
		}
		if (!SaltUtils.encodeMd5Hash(oldPwd, su.salt).equals(su.password)) {
			map.put("msg", "原密码错误！");
			map.put("code", false);
			return map;
		}
		if (StringUtils.isBlank(newPwd) || StringUtils.isBlank(reqNewPwd)) {
			map.put("msg", "新密码不能为空！");
			map.put("code", false);
			return map;
		}
		if (!newPwd.equals(reqNewPwd)) {
			map.put("msg", "两次密码不一致！");
			map.put("code", false);
			return map;
		}
		UserPasswordDTO dto = new UserPasswordDTO(su.id, SaltUtils.encodeMd5Hash(newPwd, su.salt));
		try {
			userService.update(dto);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("#用户{} 修改密码失败", su.account);
			map.put("msg", "修改失败！");
			map.put("code", false);
			return map;
		}
		sub.logout();
		map.put("msg", "修改成功！请重新登陆");
		map.put("code", true);

		return map;
	}

	/**
	 * 上传头像
	 * 
	 * @param icon
	 * @return
	 */
	@RequestMapping(value = "/update/icon", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateIcon(@RequestParam String icon) {
		Map<String, Object> map = Maps.newHashMap();
		ShiroUser su = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (StringUtils.isBlank(icon)) {
			map.put("msg", "请选择图片");
			map.put("code", false);
			return map;
		}
		UserIconUpdateDTO dto = new UserIconUpdateDTO(su.id, icon);
		try {
			userService.update(dto);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("#用户{} 上传头像失败", su.account);
			map.put("msg", "上传失败！");
			map.put("code", false);
			return map;
		}
		map.put("msg", "上传成功！");
		map.put("code", true);
		return map;
	}

	/**
	 * 查看客服成员列表
	 * 
	 * @return
	 */
	@RequestMapping("/ser/list")
	// @RequiresPermissions("services-manage/view")
	@ResponseBody
	public List<UserDTO> serList() {
		Subject sub = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) sub.getPrincipal();
		log.info("### get user [ {} ],companyId [{}] info.", u.id, u.companyId);
		if (null != u.companyId) {
			return userService.findUserListByCID(u.companyId);
		}
		return null;
	}

	// 注销
	@RequestMapping("/logout")
	@ResponseBody
	public boolean logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null && subject.isAuthenticated()) {
			initProtocol(subject);
			subject.logout();
			return true;
		}
		return false;
	}

	// 初始化客服退出的时候提示的消息协议
	private void initProtocol(Subject subject) {
		 //TODO 客服退出时处理
	}

	// 查看聊天记录
	@RequestMapping("/chatRecord")
	@ResponseBody
	public Page<MessageRecordDto> queryMessageRecord(@RequestParam String sendTime, @RequestParam String sendTimeEnd, @RequestParam String username,
			@RequestParam Long userId, @RequestParam Integer companyId, Pageable pageable) {
		Date sendTimeStart = DateUtils.str2Date(sendTime + " 00:00:00");
		Date sendEnd = DateUtils.str2Date(sendTimeEnd + " 23:59:59");
		// Page<MessageRecord>
		// page=messageRecordService.queryMessageRecord(username, userId,
		// companyId, sendTimeStart, sendTimeEnd, pageable);
		Page<MessageRecord> page = null;
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser)subject.getPrincipal();
		if(userId > -1){
			page = messageRecordService.servMessageRecord(username, userId, u.account, u.id, companyId, sendTimeStart, sendEnd, pageable);
		} else {
			page = messageRecordService.queryMessageRecordAll(username, companyId, sendTimeStart,
					sendEnd, pageable);
		}

		return new PageImpl<>(BeanMapper.map(page.getContent(), MessageRecordDto.class), pageable,
				page.getTotalElements());
	}

	@RequestMapping("/delChatRecord")
	@ResponseBody
	public Map<String, Object> delMessageRecord(@RequestParam String username, @RequestParam Long userId,
			@RequestParam Integer companyId) {
		Map<String, Object> map = new HashMap<>();
		int sum = messageRecordService.delMessageRecord(username, userId, companyId);
		if (sum > 0) {
			map.put("msg", "清除成功！");
			map.put("code", true);
			return map;
		}
		map.put("msg", "无记录可清！");
		map.put("code", true);
		return map;
	}
	/**
	 * 创建是唯一
	 * @param nickName
	 * @return
	 */
	@RequestMapping("/nick/exist")
	@ResponseBody
	public boolean nickNameExist(@RequestParam String nickName) {
		return userService.findByNickName(nickName) == null;
	}
	@RequestMapping("/account/exist")
	@ResponseBody
	public boolean accountExist(@RequestParam String account) {
		return userService.findByAccount(account) == null;
	}
	/**
	 * 修改时唯一
	 * @param nickName
	 * @param userId
	 * @return
	 */
	@RequestMapping("/nick/exist/who")
	@ResponseBody
	public boolean nickNameExist(@RequestParam String nickName,@RequestParam Long userId) {
		if(userId == -1l){
			Subject sub = SecurityUtils.getSubject();
			ShiroUser u = (ShiroUser) sub.getPrincipal();
			userId = u.id;
		}
		return userService.findByNickNameAndNotUserId(nickName, userId) == null;
	}

	/**
	 * 离线消息数目
	 * 
	 * @return
	 */
	@RequestMapping("/leavemsgs")
	@ResponseBody
	public Map<String, Object> getLeaveMsgs() {
		Map<String, Object> map = Maps.newHashMap();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		User user = userService.find(u.id);
		Layer lay = user.getLayer();
		if (lay != null) {
			Map<String, List<LeaveMsgDTO>> usermaps = Global.LEAVE_MSGS.get(lay.getId());
			List<LeaveMsgNum> res = new ArrayList<>();
			if (null == usermaps) {
				map.put("nums", 0);
				return map;
			}
			for (String username : usermaps.keySet()) {
				LeaveMsgNum dto = new LeaveMsgNum();
				dto.setUserName(username);
				dto.setSize(usermaps.get(username).size());
				res.add(dto);
			}
			map.put("nums", res);
			return map;
		}
		map.put("nums", 0);
		return map;
	}

	/**
	 * 读取离线消息
	 * 
	 * @return
	 */
	@RequestMapping("/leavemsgs/read")
	@ResponseBody
	public Map<String, Object> readLeaveMsg(@RequestParam String userName) {
		Map<String, Object> map = Maps.newHashMap();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		User user = userService.find(u.id);
		Layer lay = user.getLayer();
		if (lay != null) {
			for (Integer layer : Global.LEAVE_MSGS.keySet()) {
				System.err.println("###" + layer + " : content:" + Global.LEAVE_MSGS.get(layer));
			}
			Map<String, List<LeaveMsgDTO>> usermaps = Global.LEAVE_MSGS.get(lay.getId());
			if (null == usermaps) {
				map.put("code", false);
				map.put("msg", "当前没有任何留言！");
				return map;
			}
			List<LeaveMsgDTO> messages = usermaps.get(userName);
			if (null == messages) {
				map.put("code", false);
				map.put("msg", "该用户没有留言！");
				return map;
			}
			// 保存到数据库为已读消息
			try {
				messageRecordService.saveLeaveMsg(u.companyId, u.id, userName, messages);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("###保存离线消息到数据库失败！userName:{},messages:{}", userName, messages);
			}
			map.put("code", true);
			map.put("msg", messages);
			usermaps.remove(userName);
			Global.LEAVE_MSGS.remove(lay.getId());// 读取完清除缓存
			if (null != usermaps && usermaps.size() > 0)
				Global.LEAVE_MSGS.put(lay.getId(), usermaps);
			for (Integer layer : Global.LEAVE_MSGS.keySet()) {
				System.err.println("###" + layer + " : content:" + Global.LEAVE_MSGS.get(layer));
			}
		}
		return map;
	}

}
