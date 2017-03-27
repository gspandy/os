package com.hitler.web.controller.oc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * 
 * 这是首页控制
 * @author onsou
 *
 */
@Controller
public class HomeController {
	
	/**
	 * 首页跳转
	 * @return
	 */
	@RequestMapping(value={"/","/index"})
	public String home(Model model){
		return "home/index";
	}
	
	/**
	 * 登录页面跳转
	 * @return
	 */
	@RequestMapping(value={"/login"})
	public String login(){
		return "login";
	}

}
