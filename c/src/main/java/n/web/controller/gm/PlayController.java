package n.web.controller.gm;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import n.core.web.controller.GenericController;
import n.entity.play.Game;
import n.entity.play.PlayType;
import n.entity.play.PlayTypeGroup;
import n.service.play.IGameService;
import n.service.play.IPlayTypeService;

@Controller
@RequestMapping("/play")
public class PlayController extends GenericController {

	@Resource
	private IPlayTypeService playTypeService;

	@Resource
	private IGameService gameService;

	@RequestMapping("/group/all")    //获取玩法分组信息
	@ResponseBody
	public List<PlayTypeGroup> findAllGroup() {
		return playTypeService.findAllGroup();
	}

	/**
	 * @param gmid
	 * @param pid
	 * @return
	 */
	@RequestMapping("/gm/{gmid}")  //获取游戏对应的玩法分组信息
	@ResponseBody
	public List<PlayType> findByGMAndPTGroup(@PathVariable Integer gmid, Integer pgid) {
		if (null != gmid) {
			Game game = gameService.find(gmid);
			if (null != game) {
				Integer ggroup = game.getGmGroup().getId();
				return playTypeService.findByGroup(ggroup, pgid);
			}
		}  
		return null;
	}

}
