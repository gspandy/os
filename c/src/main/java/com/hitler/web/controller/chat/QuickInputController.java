package com.hitler.web.controller.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("oc/quickset")
public class QuickInputController {
	
	@RequestMapping("/index")
	public String index(){
		return "center/quickset/index";
	}

}
