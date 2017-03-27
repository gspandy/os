package n.web.controller.chat;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 聊天共享设置
 * @author jt_wangshuiping @date 2017年2月20日
 *
 */
@Controller
public class ChatSettingController {

	@RequiresPermissions(value="chat-charshare/share")
	@RequestMapping("/chat/charshare/index")
	public String index(){
		return "center/share/index";
	}
}
