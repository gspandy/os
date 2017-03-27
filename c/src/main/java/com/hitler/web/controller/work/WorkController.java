package com.hitler.web.controller.work;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hitler.core.web.controller.GenericController;

@Controller
@RequestMapping("/work")
public class WorkController extends GenericController {

	/**
	 * 主页会话窗口
	 * 
	 * @return
	 */
	@RequestMapping("/chat")
	public String chatIndex(Model model) {
		// 可以添加主题属性
		/*Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		Protocol $proto = MakeProtocol.servPrepare(u.getCompanyId(), u.getId(), u.getAccount(),
				u.nickname,session.getId().toString());
		String protocol = BeanMapper.objToJson($proto);
		model.addAttribute("protocol", protocol);*/
		return "chat/console";
	}

}
