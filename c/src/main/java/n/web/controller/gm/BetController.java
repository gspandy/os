package n.web.controller.gm;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import n.core.jutils.bean.BeanMapper;
import n.entity.play.LN;
import n.entity.play.PlayType;
import n.io.topic.Topic;
import n.service.play.IBetRecordService;
import n.table.dto.play.BetRecordDTO;

@Controller
@RequestMapping("/bet")
public class BetController {

	@Resource
	private IBetRecordService betRecordService;

	@RequestMapping("/record")
	@ResponseBody
	@RequiresAuthentication
	public List<BetRecordDTO> findRecord() throws Exception {
		return BeanMapper.map(betRecordService.findAll(), BetRecordDTO.class);
	}

	@RequestMapping("/up")
	@ResponseBody
	@RequiresAuthentication
	public boolean bet(BetRecordDTO dto) {
		try {
			betRecordService.bet(dto);
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
