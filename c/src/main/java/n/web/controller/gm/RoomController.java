package n.web.controller.gm;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import n.core.web.controller.GenericController;
import n.service.play.IGameService;
import n.service.play.ILNService;
import n.service.play.IPlayTypeService;

@Controller
@RequestMapping("/room")
public class RoomController extends GenericController {

	@Resource
	private IGameService gameService;
	@Resource
	private IPlayTypeService playTypeService;

	@Resource
	private ILNService lnService;

	@RequestMapping("/sys/{gmid}")
	public ModelAndView roomGM(@PathVariable String gmid) {
		ModelAndView mav = new ModelAndView();
		// log.info("RID:{}", tid);
		if (null != gmid) {
			Integer gm_id = Integer.valueOf(gmid);
			mav.addObject("game", gameService.find(gm_id)); // 游戏类型
			mav.addObject("ptGroup", playTypeService.findAllGroup()); //
			mav.addObject("cln", lnService.findCurrent(gm_id)); // 当前期
			mav.addObject("lastLN",lnService.findLastLottery(gm_id));
			mav.setViewName("gm/sys");
		}
		return mav;
	}

}
