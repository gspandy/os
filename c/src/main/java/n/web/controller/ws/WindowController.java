package n.web.controller.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;

import n.core.jutils.base.RegexUtils;
import n.core.jutils.base.ValidateHelper;
import n.core.jutils.bean.BeanMapper;
import n.core.jutils.date.DateUtils;
import n.core.jutils.encrypt.DesEncryptUtils;
import n.core.web.controller.GenericController;
import n.entity.authc.Company;
import n.entity.authc.Layer;
import n.entity.info.MessageRecord;
import n.service.authc.ICompanyService;
import n.service.authc.ILayerService;
import n.service.info.IMessageRecordService;
import n.table.dto.authc.MessageRecordDto;
import n.table.dto.info.LeaveMsgDTO;
import n.web.Global;

/**
 * 聊天视图控制器
 * 
 * @author onsoul@qq.com
 * @time 2016年11月7日 上午10:22:09
 * @version 1.0
 */

@Controller
public class WindowController extends GenericController {
	private Logger log = LoggerFactory.getLogger(WindowController.class);

	@Resource
	private ICompanyService companyService;

	@Resource
	private ILayerService layerService;
 
	@Autowired
	private IMessageRecordService messageRecordService;

	/**
	 * 网页客户端1.0版
	 * 
	 * @param request
	 * @param company
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/c/{company}")
	public ModelAndView c2(HttpServletRequest request, @PathVariable String company) throws Exception {
		String sgin_uname = signType(request);
		ModelAndView mav = new ModelAndView();

		if (RegexUtils.isInteger(company)) {

			String sid = request.getSession().getId();
			Integer companyID = Integer.valueOf(company);
			Company c$enity = companyService.find(companyID);
			List<Layer> layers = new ArrayList<Layer>();
			if (c$enity != null) {
				// 生成用户上线协议
				/*Protocol protocol = MakeProtocol.userOnline(c$enity.getId(), sid);*/

				layers = layerService.findAllLayerByCID(companyID);
				if (ValidateHelper.isNotEmptyString(sgin_uname)) {
					String userName = "C" + c$enity.getId() + "_" + sgin_uname;// 生成不同平台下的用户名，避免冲突
					Set<String> visitors = (Set<String>) request.getSession().getAttribute(Global.VISITOR_NAME);
					if (ValidateHelper.isNotEmptyCollection(visitors)) {
						Iterator<String> iterator = visitors.iterator();
						while (iterator.hasNext()) {
							String visitor_name = iterator.next();
							if (visitor_name.equals(userName)) {
							/*	protocol.setUsername(visitor_name); // 如果只是刷新页面
								protocol.setState(State.RECONNECT);*/
								break;
							} else {
							/*	protocol.setUsername(userName);*/
								visitors.add(userName);
								break;
							}
						}

					} else {
					/*	protocol.setUsername(userName);*/
						visitors = new HashSet<>();
						visitors.add(userName);
					}

					/*protocol.setuType(UType.USER);*/
					request.getSession().setAttribute(Global.VISITOR_NAME, visitors);

				} else {
					String ip = request.getRemoteHost();
					String vhost = ip + ":" + c$enity.getId();
					log.info("ip:{}, vhost:{}", ip, vhost);
					/*Protocol protoc = relation.getVisitorHost(vhost);
					if (ValidateHelper.isEmpty(protoc)) {
						protocol.setUsername(protocol.getUsername());
						protocol.setUhost(vhost);
						relation.saveVisitorHost(protocol);
					} else {
						protocol.setUsername(protoc.getUsername());
						;
					}*/
				}

		/*		log.info("### URL:{} 有用户访问,开始生成ID:{}", c$enity.getWebSite(), protocol.getUsername());
				String protocol_json = BeanMapper.objToJson(protocol);*/
				mav.addObject("layers", layers);
			/*	mav.addObject("protocol", protocol_json);*/
				mav.addObject("company", c$enity);
			} else {
				throw new Exception("访问的地址无效！");
			}
		}
		mav.setViewName("ws/c2");
		return mav; // 客户端视图

	}

	private String signType(HttpServletRequest request) {
		String sign = request.getParameter("sign");
		String username = "";
		if (ValidateHelper.isNotEmptyString(sign)) {
			try {
				byte[] decryptFrom = DesEncryptUtils.parseHexStr2Byte(sign);
				username = new String(DesEncryptUtils.decrypt(decryptFrom, DesEncryptUtils.MY_KEY), "UTF-8");
				log.info("解密:{},{}", decryptFrom, username);
			} catch (Exception e) {
				log.error("##跳转失败:解密失败,转换用户为游客模式!");
				username = "";
			}
		}
		return username;
	}

	// 查看聊天记录
	@RequestMapping("/c/chat/record")
	@ResponseBody
	public Page<MessageRecordDto> queryMessageRecord(@RequestParam String sendTime,@RequestParam String sendTimeEnd, @RequestParam String username,
			@RequestParam Integer companyId, Pageable pageable) {
		Date sendTimeStart = DateUtils.str2Date(sendTime + " 00:00:00");
		Date sendTend = DateUtils.str2Date(sendTimeEnd + " 23:59:59");
		Page<MessageRecord> page = messageRecordService.queryMessageRecordAll(username, companyId, sendTimeStart,
				sendTend, pageable);

		return new PageImpl<>(BeanMapper.map(page.getContent(), MessageRecordDto.class), pageable,
				page.getTotalElements());
	}
	/**
	 * 离线留言
	 * @param layId
	 * @param userName
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "/c/chat/leave", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> leaveMsg(@RequestParam Integer layId,@RequestParam String userName,@RequestParam String content){
		Map<String, Object> map = Maps.newHashMap();
		LeaveMsgDTO dto = new LeaveMsgDTO();
		try {
			Map<String, List<LeaveMsgDTO>> oldmsgs = Global.LEAVE_MSGS.get(layId);
			if(ValidateHelper.isEmptyMap(oldmsgs)){
				oldmsgs = new HashMap<>();
				List<LeaveMsgDTO> messages = new ArrayList<>();
				dto.setContent(content);
				dto.setSendTime(new Date());
				messages.add(dto);
				oldmsgs.put(userName, messages);
			} else {
				List<LeaveMsgDTO> messages = oldmsgs.get(userName);
				if(ValidateHelper.isEmptyCollection(messages)){
					messages = new ArrayList<>();
					oldmsgs.put(userName, messages);
				}
				dto.setContent(content);
				dto.setSendTime(new Date());
				messages.add(dto);
				oldmsgs.put(userName, messages);
			}
			Global.LEAVE_MSGS.put(layId, oldmsgs);
			for (Integer lay : Global.LEAVE_MSGS.keySet()) {
				System.err.println("###"+lay+" : content:"+Global.LEAVE_MSGS.get(lay));
			}
			map.put("code", true);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("####{}，用户名：{}，离线留言失败：message:{}",layId, userName, content);
			map.put("code", false);
		}
		return map;
	}

}
