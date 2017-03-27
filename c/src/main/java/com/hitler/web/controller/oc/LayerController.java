package com.hitler.web.controller.oc;

import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.hitler.core.entity.security.ShiroUser;
import com.hitler.core.web.controller.GenericController;
import com.hitler.core.web.view.MappingJsonView;
import com.hitler.entity.authc.Layer;
import com.hitler.entity.authc.Layer.LayerState;
import com.hitler.service.authc.ICompanyService;
import com.hitler.service.authc.ILayerService;
import com.hitler.service.authc.IUserService;
import com.hitler.table.dto.authc.LayerCreateDTO;
import com.hitler.table.dto.authc.LayerUpdateDTO;

/**
 * 分组控制器
 * @author jt_wangshuiping @date 2016年12月28日
 *
 */
@Controller
@RequestMapping("oc/layer")
public class LayerController extends GenericController {
	
	@Resource
	private ILayerService layerService;
	@Resource
	private IUserService userService;
	@Resource
	private ICompanyService companyService;
	
	@RequestMapping("/index")
	public String index(){
		return "center/layer/index";
	}
	/**
	 * table
	 * @param pageable
	 * @return
	 */
	@RequestMapping("/list/")
	@ResponseBody
	public Map<String, Object> listForTable(Pageable pageable){
		Map<String, Object> map = Maps.newHashMap();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		Page<Layer> layerList = layerService.findByCompany(u.companyId, pageable);
		map.put("data", layerList.getContent());
		map.put("recordsTotal", layerList.getTotalElements());
		return map;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Page<Layer> list(Pageable pageable){
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		return layerService.findByCompany(u.companyId, pageable);
	}
	
	/**
	 * 添加分组
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(@Valid LayerCreateDTO dto, BindingResult br){
		Map<String, Object> map = Maps.newHashMap();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		if(br.hasErrors()){
			return MappingJsonView.bindMsg(br);
		}
		if(null == u.companyId){
			map.put("msg", "请登录公司管理员账户添加客服！");
			map.put("code", false);
			return map;
		}
		if(null != layerService.findByCode(dto.getCode())){
			map.put("msg", "该分组代码已存在！");
			map.put("code", false);
			return map;
		}
		try {
			dto.setCompany(companyService.find(u.companyId));
			layerService.save(dto);
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
	@RequestMapping(value = "/find/{id}")
	@ResponseBody
	public Layer find(@PathVariable Integer id){
		return layerService.find(id);
	}
	/**
	 * 修改分组
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(@Valid LayerUpdateDTO dto, BindingResult br){
		Map<String, Object> map = Maps.newHashMap();
		if(br.hasErrors()){
			return MappingJsonView.bindMsg(br);
		}
		if(null == dto.getId() || null == layerService.findById(dto.getId())){
			map.put("msg", "该分组不存在！");
			map.put("code", false);
			return map;
		}
		try {
			layerService.update(dto);
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
	 * 启用/禁用分组
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/del:{state}/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(@PathVariable Integer id, @PathVariable boolean state){
		Map<String, Object> map = Maps.newHashMap();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		if(null == id || null == layerService.findById(id)){
			map.put("msg", "该分组不存在！");
			map.put("code", false);
			return map;
		}
		if(null != userService.findByLayerIdAndCompanyId(id, u.companyId)&&userService.findByLayerIdAndCompanyId(id, u.companyId).size() > 0){
			map.put("msg", "该分组存在客服不允许禁用！");
			map.put("code", false);
			return map;
		}
		
		LayerState layerState = LayerState.normal;
		try {
			if(state){
				layerState = LayerState.forbid;
			}
			layerService.forbid(id, layerState);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "操作失败！");
			map.put("code", false);
			return map;
		}
		map.put("msg", "操作成功！");
		map.put("code", true);
		return map;
	}
	
	@RequestMapping("/name/exist")
	@ResponseBody
	public boolean layerNameExist(@RequestParam String layerName){
		return layerService.findByLayerName(layerName) == null;
	}
	@RequestMapping("/name/exist/who")
	@ResponseBody
	public boolean layerNameExist(@RequestParam String layerName, @RequestParam Integer id){
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		return layerService.findByLayerName(layerName, id, u.companyId) == null;
	}

}
