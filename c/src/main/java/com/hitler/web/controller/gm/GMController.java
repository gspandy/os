package com.hitler.web.controller.gm;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hitler.core.web.controller.GenericController;
import com.hitler.entity.play.Game;
import com.hitler.entity.play.Game.Name;
import com.hitler.entity.play.Game_;
import com.hitler.service.play.IGameService;

/**
 * 独立获取游戏信息时
 * @author onsou
 *
 */
@Controller
@RequestMapping("/gm")
public class GMController extends GenericController {

	@Resource
	private IGameService gameService;

	@RequestMapping("/home")
	public String gmIndex() {
		return "gm/index";
	}
	
	@RequestMapping("/hero")
	public String gmHero(){
		return "gm/hero";
	}

	@RequestMapping("/all")
	@ResponseBody
	public List<Game> findAll() {
		return gameService.findAll(new Sort(Sort.Direction.DESC, Game_.deep.getName())); // 根据ID
	}

	@RequestMapping("/group/{gid}")
	@ResponseBody
	public List<Game> findByGroup(@PathVariable Integer gid) {
		return gameService.findByGroupID(gid); // 根据ID
	}

	@RequestMapping("/id/{gid}")
	@ResponseBody
	public Game findByID(@PathVariable Integer gid) {
		return gameService.find(gid); // 根据ID
	}

	@RequestMapping("/name")
	@ResponseBody
	public Game findByName(String gname) {
		Name[] values = Name.values();
		for (Name value : values) {
			String eq = value.name();
			if (gname.equals(eq)) {
				return gameService.findByName(value); // 根据ID
			}
		}
		return null;

	}

}
