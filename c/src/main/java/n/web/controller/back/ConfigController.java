package n.web.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("back/config")
public class ConfigController {

	@RequestMapping(value={"/","/index"})
	public String configIndex(){
		return "admin/config/index";
	}
	
}
