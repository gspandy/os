package com.hitler.web.controller.chat;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 共享记录
 * @author jt_wangshuiping @date 2017年2月20日
 *
 */
@Controller
public class ShareRecordController {
	
	@RequiresPermissions(value="chat-charshare/lookShare")
	@RequestMapping("/chat/lookshare/index")
	public String index(){
		return "center/share/record";
	}

}
