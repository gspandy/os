package n.web.controller.oc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

import n.core.entity.security.ShiroUser;
import n.core.web.controller.GenericController;
import n.core.web.view.MappingJsonView;
import n.entity.sider.Preference;
import n.entity.sider.PromptConfig;
import n.entity.sider.PromptConfig.PromptType;
import n.service.sider.IPreferenceService;
import n.service.sider.IPromptConfigService;
import n.table.dto.sider.PromptConfigCreateDTO;
import n.table.dto.sider.PromptQuickCreateDTO;
import n.table.dto.sider.PromptQuickUpdateDTO;
/**
 * 聊天提示配置控制器
 * @author jt_wangshuiping @date 2016年12月13日
 *
 */
@Controller
@RequestMapping("/oc/prompt")
public class PromptConfigController extends GenericController {
	@Resource
	private IPromptConfigService configService;
	@Resource
	private IPreferenceService preferenceService;
	
	/**
	 * 列表
	 * @return
	 */
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String, Object> list(){
		Map<String, Object> map = Maps.newHashMap();
		map.put("prompts", configService.findAll());
		map.put("timeouts", configService.findListByType(PromptType.timeout));
		return map;
	}
	/**
	 * 快速输入内容列表
	 * @return
	 */
	@RequestMapping(value = "/quick/list")
	@ResponseBody
	public List quickList(){
		return configService.findListByType(PromptType.quick);
	}
	/**
	 * table
	 * @return
	 */
	@RequestMapping(value = "/quick/list/")
	@ResponseBody
	public Map<String, Object> quickListTable(){
		Map<String, Object> map = Maps.newHashMap();
		List<PromptConfig> list = configService.findListByType(PromptType.quick);
		map.put("data", list);
		map.put("recordsTotal", list.size());
		return map;
	}
	/**
	 * 快速输入内容（添加）
	 * @param dto
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/quick/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addQuick(@Valid PromptQuickCreateDTO  dto, BindingResult br){
		Map<String, Object> map = Maps.newHashMap();
		if(br.hasErrors()){
			return MappingJsonView.bindMsg(br);
		}
		Subject sub = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) sub.getPrincipal();
		dto.setUserAccount(u.account);
		dto.setUserId(u.id);
		try {
			configService.save(dto);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "添加失败！");
			map.put("code", false);
			return map;
		}
		map.put("msg", "添加成功！");
		map.put("code", true);
		return map;
	}
	@RequestMapping(value = "/quick/update/{id}", method = RequestMethod.GET)
	public Map<String, Object> update(@PathVariable Integer id){
		Map<String, Object> map = Maps.newHashMap();
		PromptConfig prompt = configService.find(id);
		map.put("prompt", prompt);
		return map;
	}
	/**
	 * 快速输入内容（修改保存）
	 * @param dto
	 * @param br
	 * @return
	 */
	@RequestMapping(value = "/quick/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(@Valid PromptQuickUpdateDTO dto, BindingResult br){
		Map<String, Object> map = Maps.newHashMap();
		if(br.hasErrors()){
			return MappingJsonView.bindMsg(br);
		}
		try {
			configService.update(dto);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "修改失败！");
			map.put("code", false);
			return map;
		}
		map.put("msg", "修改成功！");
		map.put("code", true);
		return map;
	}
	
	/**
	 * 添加
	 * @param dtos
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(@RequestBody List<PromptConfigCreateDTO> dtos){
		Map<String, Object> map = Maps.newHashMap();
		if(null == dtos || dtos.size() <= 0){
			map.put("msg", "参数错误！");
			map.put("code", false);
			return map;
		}
		for (PromptConfigCreateDTO dto : dtos) {
			try {
				configService.add(dto);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("msg", "添加失败！");
				map.put("code", false);
				return map;
			}
		}
		map.put("msg", "添加成功！");
		map.put("code", true);
		
		return map;
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> del(@PathVariable Integer id) {
		Map<String, Object> map = Maps.newHashMap();
		try {
			configService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "删除失败！");
			map.put("code", false);
			return map;
		}
		map.put("msg", "删除成功！");
		map.put("code", true);
		return map;
	}
	/**
	 * 超时提醒开关
	 * @param enable
	 * @return
	 */
	@RequestMapping(value = "/timeout/switch/{enable}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> timeoutSwitch(@PathVariable boolean enable){
		Map<String, Object> map = Maps.newHashMap();
		Preference p = preferenceService.findByCode("timeOutSwitch");
		if(null == p){
			map.put("msg", "数据不可用！");
			map.put("code", false);
			return map;
		}
		if(enable){
			p.setValue("1");
		} else {
			p.setValue("0");
		}
		try {
			preferenceService.save(p);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "设置失败！");
			map.put("code", false);
			return map;
		}
		
		map.put("msg", "设置成功！");
		map.put("code", true);
		return map;
	}
	/**
	 * 访客超时提醒状态
	 * @return
	 */
	@RequestMapping(value = "/timeout", method = RequestMethod.GET)
	@ResponseBody
	public String timeoutVal(){
		Preference p = preferenceService.findByCode("timeOutSwitch");
		if(null == p)
			return null;
		return p.getValue();
	}

}
