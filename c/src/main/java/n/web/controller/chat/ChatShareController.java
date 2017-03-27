package n.web.controller.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import n.core.entity.security.ShiroUser;
import n.core.jutils.bean.BeanMapper;
import n.entity.authc.Layer;
import n.entity.authc.User;
import n.entity.authc.UserChatShare;
import n.entity.authc.User.OnlineState;
import n.entity.info.MessageRecord;
import n.service.authc.IUserChatShareService;
import n.service.authc.IUserService;
import n.service.info.IMessageRecordService;
import n.table.dto.authc.MessageRecordDto;
import n.table.dto.authc.UserDTO;

@Controller
public class ChatShareController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IUserChatShareService userChatShareService;
	
	@Autowired
	private IMessageRecordService messageRecordService;

	/**
	 * table
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(value="chat-charshare/share")
	@RequestMapping(value = "/chat-charshare/share/", method = RequestMethod.GET)
	public Map<String, Object> charShareSetTable(Model model) throws Exception {
		Map<String, Object> map = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		User user=userService.find(u.getId());
		Layer lay = user.getLayer();
		List<UserDTO> dtoList=new ArrayList<UserDTO>();
		if(lay == null){
			map.put("data", dtoList);
			map.put("recordsTotal", 0);
			return map;
		}
		System.out.println("分组:"+user.getLayer().getId()+",公司:"+user.getCompany().getId());
		List<User> list=userService.findByLayerIdAndCompanyId(user.getLayer().getId(),user.getCompany().getId());
		List<UserChatShare> shareList=userService.getUserChatShare(user.getId());
		for(User us:list){
			if(!us.getId().equals(user.getId())){
				UserDTO ud=new UserDTO();
				ud.setId(us.getId());
				ud.setNickName(us.getNickName());
				ud.setAccountType(us.getAccountType());
				ud.setAccount(us.getAccount());
				ud.setUserName(us.getUserName());
				if(shareList!=null&&!shareList.isEmpty()){
					for(UserChatShare ucs:shareList){
						if(ucs.getShareUserId().equals(us.getId())){
							ud.setIsShare("1");
							break;
						}
					}
				}
				dtoList.add(ud);
			}
		}
		map.put("data", dtoList);
		map.put("recordsTotal", dtoList.size());
		return map;
	}
	@RequiresPermissions(value="chat-charshare/lookShare")
	@RequestMapping("/chat-share/queryMsgRecord/")
	public Map<String, Object> queryShareMessageRecordTable(){
		Map<String, Object> map = Maps.newHashMap();
		List<MessageRecordDto> list=new ArrayList<MessageRecordDto>();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		List<UserChatShare> ucsList=userService.getUserChatShareMsgRecord(u.getId());
		for(UserChatShare ucs:ucsList){
			List<MessageRecord> mrList=messageRecordService.queryShareMsgRecord(ucs.getUserId());
			for(MessageRecord mr:mrList){
				MessageRecordDto mrd=new MessageRecordDto();
				User user=userService.find(mr.getUserId());
				mrd.setCompanyId(mr.getCompanyId());
				mrd.setUserName(user.getAccount());
				mrd.setFromUser(mr.getFromNickName());
				mrd.setUserId(mr.getUserId());
				list.add(mrd);
			}
		}
		
		map.put("data", list);
		map.put("recordsTotal", list.size());
		return map;
	}
	
	
	@RequiresPermissions(value="chat-charshare/share")
	@RequestMapping(value = "/chat-charshare/share", method = RequestMethod.GET)
	public List<UserDTO> charShareSet(Model model) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		User user=userService.find(u.getId());
		Layer lay = user.getLayer();
		if(lay == null)
			return null;
		System.out.println("分组:"+user.getLayer().getId()+",公司:"+user.getCompany().getId());
		List<User> list=userService.findByLayerIdAndCompanyId(user.getLayer().getId(),user.getCompany().getId());
		List<UserChatShare> shareList=userService.getUserChatShare(user.getId());
		List<UserDTO> dtoList=new ArrayList<UserDTO>();
		for(User us:list){
			if(!us.getId().equals(user.getId())){
				UserDTO ud=new UserDTO();
				ud.setId(us.getId());
				ud.setNickName(us.getNickName());
				ud.setAccount(us.getAccount());
				ud.setUserName(us.getUserName());
				if(shareList!=null&&!shareList.isEmpty()){
					for(UserChatShare ucs:shareList){
						if(ucs.getShareUserId().equals(us.getId())){
							ud.setIsShare("1");
							break;
						}
					}
				}
				dtoList.add(ud);
			}
		}
		return dtoList;
	}
	/**
	 * 查找可求助的同组下的客服
	 * @return
	 */
	@RequiresPermissions(value="chat-charshare/share")
	@RequestMapping(value = "/chat/charshare/select/share/id", method = RequestMethod.GET)
	@ResponseBody
	public List<UserDTO> selectShareId(){
		List<UserDTO> users = Lists.newArrayList();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		List<UserChatShare> userChatShare = userService.getUserChatShare(u.id);
		if(null != userChatShare && userChatShare.size() > 0){
			for(UserChatShare uc : userChatShare){
				UserDTO ud = new UserDTO();
				User find = userService.find(uc.getShareUserId());
				if(find.getOnlineState().equals(OnlineState.online)){
					ud.setId(find.getId());
					ud.setNickName(find.getNickName());
					ud.setLayer(find.getLayer());
					users.add(ud);
				}
			}
		}
		return users;
	}
	
	@RequiresPermissions(value="chat-charshare/share")
	@RequestMapping(value = "/chatset/setting", method = RequestMethod.GET)
	@ResponseBody
	public String setting(Model model,@RequestParam Long shareUserId,@RequestParam String type) throws Exception {
		try {
			Subject subject = SecurityUtils.getSubject();
			ShiroUser u = (ShiroUser) subject.getPrincipal();
			if ("1".equals(type)) {
				UserChatShare ucs = new UserChatShare();
				ucs.setUserId(u.getId());
				ucs.setShareUserId(shareUserId);
				userChatShareService.save(ucs);
				return "1";
			} else if ("0".equals(type)) {
				userChatShareService.deleteUserShare(u.getId(), shareUserId);
				return "1";
			}else if("3".equals(type)){
				User user=userService.find(u.getId());
				List<User> list=userService.findByLayerIdAndCompanyId(user.getLayer().getId(),user.getCompany().getId());
				for(User us:list){
					if(!us.getId().equals(user.getId())){
						UserChatShare ucs = new UserChatShare();
						ucs.setUserId(u.getId());
						ucs.setShareUserId(us.getId());
						userChatShareService.save(ucs);
					}
				}
				return "1";
			}else if("4".equals(type)){
				User user=userService.find(u.getId());
				List<User> list=userService.findByLayerIdAndCompanyId(user.getLayer().getId(),user.getCompany().getId());
				for(User us:list){
					if(!us.getId().equals(user.getId())){
						userChatShareService.deleteUserShare(user.getId(), us.getId());
					}
				}
				return "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		return "0";
	}
	
	@RequiresPermissions(value="chat-charshare/lookShare")
	@RequestMapping("/chat-share/queryMsgRecord")
	public List<MessageRecordDto> queryShareMessageRecord(){
		List<MessageRecordDto> list=new ArrayList<MessageRecordDto>();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		List<UserChatShare> ucsList=userService.getUserChatShareMsgRecord(u.getId());
		for(UserChatShare ucs:ucsList){
			List<MessageRecord> mrList=messageRecordService.queryShareMsgRecord(ucs.getUserId());
			for(MessageRecord mr:mrList){
				MessageRecordDto mrd=new MessageRecordDto();
				User user=userService.find(mr.getUserId());
				mrd.setCompanyId(mr.getCompanyId());
				mrd.setUserName(user.getUserName());
				mrd.setFromUser(mr.getFromNickName());
				mrd.setUserId(mr.getUserId());
				list.add(mrd);
			}
		}
		
		return list;
	}
	@RequiresPermissions(value="chat-charshare/lookShare")
	@RequestMapping("/chat-share/chatRecord")
	@ResponseBody
	public Page<MessageRecordDto> queryMessageRecord(@RequestParam String username,@RequestParam Long userId,@RequestParam Integer companyId,Pageable pageable){
		Page<MessageRecord> page=messageRecordService.findShareMessageRecord(username,userId, companyId, pageable);
		return new PageImpl<>(BeanMapper.map(page.getContent(), MessageRecordDto.class), pageable, page.getTotalElements());
	}
	
}
