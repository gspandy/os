package com.wise.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wise.entity.sider.Knight;
import com.wise.service.sider.IKnightService;

@RestController
@RequestMapping("hero")
public class HeroController {
	
	@Resource
	private IKnightService knightService;

	@RequestMapping("/say")
	public String say(String something) {
		return "hero:" + something;
	}
	
	@RequestMapping("/find")
	@ResponseBody
	public Knight findOneKnight(Integer id){
		Knight k=knightService.find(id);
		return k;
	}
	

}
