package n.web.controller.center;

import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

import n.core.entity.security.ShiroUser;
import n.core.web.controller.GenericController;
import n.core.web.view.MappingJsonView;
import n.service.authc.IUserService;
import n.table.dto.authc.UserBaseUpdateDTO;

@Controller
@RequestMapping("/oc/setting")
public class SettingController extends GenericController{
	
	@Resource
	private IUserService userService;
	
	@RequestMapping("/index")
	public String settingIndex(Model model) {
		Subject subject = SecurityUtils.getSubject();
		ShiroUser su = (ShiroUser) subject.getPrincipal();
		model.addAttribute("curIcon", su.userIcon);
		model.addAttribute("curNickName", su.nickname);
		model.addAttribute("accountState", su.accountState);
		model.addAttribute("email", su.email);
		model.addAttribute("mobile", su.mobile);
		model.addAttribute("companyName", su.companyName);
		return "center/setting/index";
	}
	@RequestMapping("/repwd")
	public String settingPwd(){
		return "center/setting/repwd";
	}
	
	/**
	 * 修改资料页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/info")
	public String settingInfo(Model model) throws Exception{
		Subject subject = SecurityUtils.getSubject();
		ShiroUser su = (ShiroUser) subject.getPrincipal();
		model.addAttribute("account", su.account);
		model.addAttribute("nickName", su.nickname);
		model.addAttribute("email", su.email);
		model.addAttribute("mobile", su.mobile);
		return "center/setting/update";
	}
	/**
	 * 更新个人资料
	 * @return
	 */
	@RequestMapping(value = "/info/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(@Valid UserBaseUpdateDTO dto, BindingResult br){
		Map<String, Object> map = Maps.newHashMap();
		if(br.hasErrors()){
			return MappingJsonView.bindMsg(br);
		}
		Subject subject = SecurityUtils.getSubject();
		ShiroUser su = (ShiroUser) subject.getPrincipal();
		dto.setId(su.id);
		try {
			userService.update(dto);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "更新失败！");
			map.put("code", false);
			return map;
		}
		map.put("msg", "更新成功！请重新登录");
		map.put("code", true);
		return map;
	}

}
