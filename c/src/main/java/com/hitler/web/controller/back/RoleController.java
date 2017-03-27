package com.hitler.web.controller.back;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.hitler.core.exception.BusinessException;
import com.hitler.core.jutils.bean.BeanMapper;
import com.hitler.core.service.support.IGenericService;
import com.hitler.entity.authc.Permission;
import com.hitler.entity.authc.Role;
import com.hitler.service.authc.IPermissionService;
import com.hitler.service.authc.IRoleService;
import com.hitler.table.authc.RoleTable;
import com.hitler.table.dto.authc.RoleCreateDTO;
import com.hitler.table.dto.authc.RoleDTO;
import com.hitler.table.dto.authc.RoleUpdateDTO;
import com.hitler.web.controller.support.CRUDController;

/**
 * 角色控制器
 * @author jt_wangshuiping @date 2016年10月26日
 *
 */
@Controller
@RequestMapping(value = "back/"+RoleController.PATH)
public class RoleController extends
		CRUDController<Role, Integer, RoleDTO, RoleCreateDTO, RoleUpdateDTO, RoleTable<RoleDTO>> {

	public static final String PATH = "admin/role";
	
	public RoleController() {
		super(PATH);
	}
	
	@Resource
	private IRoleService roleService;
	@Resource
	private IPermissionService permissionService;

	@Override
	protected IGenericService<Role, Integer> getService() {
		return roleService;
	}
	@Override
	protected void preCreate(Model model, RoleCreateDTO createDTO, ServletRequest request) throws Exception {
		model.addAttribute("roles", BeanMapper.map(roleService.findAll(),RoleDTO.class));
	}
	/**
	 * 进入权限分配页面
	 */
	@RequestMapping(value = { "/distribution/permission/{id}" }, method = RequestMethod.GET)
	public String distributionPermission(Model model, @PathVariable Integer id) 
			throws Exception {
		Role role = roleService.find(id);
		String permissionIds = "";
		for(Permission p : permissionService.findByRoleId(id)) {
			permissionIds += p.getId() + ",";
		}
		permissionIds = permissionIds.equals("") ? "" : permissionIds.substring(0, permissionIds.length()-1);
		
		model.addAttribute("role", BeanMapper.map(role, RoleDTO.class));
		model.addAttribute("permissionIds", permissionIds);
		return PATH + "/dpermission";
	}
	/**
	 * 进入权限分配页面
	 */
	@RequestMapping(value = { "/distribution/permission1/{id}" }, method = RequestMethod.GET)
	public String distributionPermission1(Model model, @PathVariable Integer id) 
			throws Exception {
		Role role = roleService.find(id);
		String permissionIds = "";
		for(Permission p : permissionService.findByRoleId(id)) {
			permissionIds += p.getId() + ",";
		}
		permissionIds = permissionIds.equals("") ? "" : permissionIds.substring(0, permissionIds.length()-1);
		
		model.addAttribute("role", BeanMapper.map(role, RoleDTO.class));
		model.addAttribute("permissionIds", permissionIds);
		return PATH + "/dpermission_serv";
	}

	@RequestMapping(value = "/permission/save", method = RequestMethod.POST)
	public String permissionSave(Integer roleId, String permissions) throws Exception{
		Role role = roleService.find(roleId);
		if(null == roleId || null ==role){
			throw new BusinessException("角色不存在");
		}
		/**
		 * 1.删除原关系
		 * 2.保存关系
		 */
		if(null != permissions && !"".equals(permissions)){
			permissionService.permissionSave(roleId, permissions);
		}
		return "redirect:/back/"+PATH;
	}
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public Map<String, Object> del(@PathVariable Integer id) throws Exception{
		Map<String, Object> map = Maps.newHashMap();
		Role role = roleService.find(id);
		if(null == id || null ==role){
			map.put("msg", "角色不存在");
			map.put("code", false);
			return map;
		}
		if(role.getParentId() != null){
			map.put("msg", "该角色存在父角色不允许删除！");
			map.put("code", false);
			return map;
		}
		roleService.delete(role);
		map.put("msg", "删除成功！");
		map.put("code", true);
		return map;
	}
}
