package com.hitler.web.controller.back;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletRequest;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hitler.core.repository.DynamicSpecifications;
import com.hitler.core.repository.OP;
import com.hitler.core.repository.SearchFilter;
import com.hitler.core.service.support.IGenericService;
import com.hitler.entity.authc.Permission;
import com.hitler.entity.authc.Permission_;
import com.hitler.service.authc.IPermissionService;
import com.hitler.table.authc.PermissionTable;
import com.hitler.table.dto.authc.PermissionCreateDTO;
import com.hitler.table.dto.authc.PermissionDTO;
import com.hitler.table.dto.authc.PermissionTreeDTO;
import com.hitler.table.dto.authc.PermissionUpdateDTO;
import com.hitler.web.controller.support.CRUDController;

/**
 * 权限控制器
 * 
 * @author jt_wangshuiping @date 2016年11月3日
 *
 */
@Controller
@RequestMapping(value = "back/" + PermissionController.PATH)
public class PermissionController extends
		CRUDController<Permission, Integer, PermissionDTO, PermissionCreateDTO, PermissionUpdateDTO, PermissionTable<PermissionDTO>> {

	public static final String PATH = "admin/permission";

	public PermissionController() {
		super(PATH);
	}

	@Resource
	private IPermissionService permissionService;

	@Override
	protected IGenericService<Permission, Integer> getService() {
		return permissionService;
	}

	@Override
	protected void preCreate(Model model, PermissionCreateDTO createDTO, ServletRequest request) throws Exception {
		String parentPermissionId = request.getParameter("parentPermissionId");
		String floor = request.getParameter("floor");
		createDTO.setParentPermissionId(Integer.parseInt(parentPermissionId));
		createDTO.setFloor(Integer.parseInt(floor));
	}

	@Override
	@RequestMapping(value = { "/create" }, method = RequestMethod.GET)
	public String create(Model model, ServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		return super.create(model, request);
	}

	@Override
	// @RequiresPermissions(value="permission/update")
	@RequestMapping(value = { "/update/{id}" }, method = RequestMethod.GET)
	public String update(Model model, @PathVariable Integer id) throws Exception {
		return super.update(model, id);
	}

	/**
	 * 删除权限
	 * 
	 * @param id
	 *            权限id
	 */
	// @RequiresPermissions(value="permission/deleten")
	@RequestMapping(value = "/del/{id}")
	@ResponseBody
	public Map<String, Object> deletePer(@PathVariable Integer id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Permission> list = permissionService.findBySpec(id);
			
			if (list == null || list.size() == 0) {
				permissionService.delete(id);
				map.put("success", true);
			} else {
				map.put("success", false);
				map.put("message", "此权限存在子权限，不允许删除！");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "此节点存在关联，不允许删除！");
		}

		return map;
	}

	/**
	 * 判断权限名是否存在
	 */
	@RequestMapping("/code/exists")
	@ResponseBody
	public boolean ajaxCodeNotExists(String code) {
		return permissionService.findByCode(code) == null;
	}

	/**
	 * 获取permission列表json给jstree（后台）
	 */
	@RequestMapping(value = "/json")
	@ResponseBody
	public List<PermissionTreeDTO> json() {
		List<PermissionTreeDTO> list = permissionService.permissionTree();
		return list;
	}

}
