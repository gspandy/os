package n.web.controller.gm;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import n.core.web.controller.GenericController;
import n.entity.play.LN;
import n.service.play.ILNService;

@Controller
@RequestMapping("/ln")
public class LNController extends GenericController {

	@Resource
	private ILNService lnService;
	
	@RequestMapping("/cur/{gmid}")
	@ResponseBody
	public LN findCurrent(@PathVariable Integer gmid) {
		return lnService.findCurrent(gmid);
	}

	@RequestMapping("/last/{gmid}")
	@ResponseBody
	public LN findLastLottery(@PathVariable Integer gmid) {
		return lnService.findLastLottery(gmid);
	}

	@RequestMapping("/history/{gmid}")
	@ResponseBody
	public List<LN> findHistory(@PathVariable Integer gmid, Integer index, Integer pageSize) {
		if (null != index && null != pageSize) {
			return lnService.findHistory(gmid, index, pageSize).getContent();
		} else {
			return lnService.findHistory(gmid, 0, 10).getContent();
		}
	}

}
