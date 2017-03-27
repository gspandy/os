package com.hitler.web.controller.center;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("oc/mymanage")
public class MyManageController {
	
	@RequestMapping("/index")
	public String index(){
		return "center/mymanage/index";
	}

}
