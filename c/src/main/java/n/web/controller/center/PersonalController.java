package n.web.controller.center;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("oc/personal")
public class PersonalController {

	@RequestMapping("/index")
	public String settingIndex() {
		return "center/personal/index";
	}
}
