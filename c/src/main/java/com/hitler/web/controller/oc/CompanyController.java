package com.hitler.web.controller.oc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hitler.core.validation.Token;
import com.hitler.core.web.controller.GenericController;
import com.hitler.core.web.view.MappingJsonView;
import com.hitler.entity.authc.Company;
import com.hitler.entity.authc.Role;
import com.hitler.entity.authc.User;
import com.hitler.entity.authc.User.AccountState;
import com.hitler.entity.authc.User.AccountType;
import com.hitler.service.authc.ICompanyService;
import com.hitler.service.authc.IRoleService;
import com.hitler.service.authc.IUserService;
import com.hitler.table.dto.authc.CompanyDTO;
import com.hitler.table.dto.authc.CompanyUpdateDTO;
import com.hitler.web.Global;
import com.hitler.web.Global.RoleNames;
import com.hitler.web.realm.SaltUtils;

/**
 * 
 * @author jt_wangshuiping @date 2016年11月14日
 *
 */
@Controller
@RequestMapping("oc/company")
public class CompanyController extends GenericController {

	@Resource
	private ICompanyService companyService;

	@Resource
	private IUserService userService;

	@Resource
	private IRoleService roleService;

	@RequestMapping("/index")
	public String index(){
		return "admin/company/index";
	}
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	@RequiresRoles("administrator")
	@Token(save = true)
	public String edit(Model model,@PathVariable Integer id){
		model.addAttribute("company",companyService.find(id));
		return "admin/company/update";
	}
	
	/**
	 * 公司列表
	 * 
	 * @return
	 */
	@RequestMapping("/list/")
	@ResponseBody
	public Map<String, Object> listForTable(CompanyDTO dto, Pageable page) {
		Map<String, Object> map = new HashMap<>();
		Page<Company> companys = companyService.findNotDel(dto, page);
		map.put("data", companys.getContent());
		map.put("recordsTotal", companys.getTotalElements());
		return map;
	}
	/**
	 * 公司列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Page<Company> list(CompanyDTO dto, Pageable page) {
		return companyService.findNotDel(dto, page);
	}

	/**
	 * 新增
	 * 
	 * @param companyDTO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresRoles("administrator")
	@ResponseBody
	public Map<String, Object> add(@Valid Company companyDTO, BindingResult br, HttpServletRequest request) throws Exception {
		/**
		 * 1.新增公司信息 2.增加默认公司管理员 3.增加默认角色
		 */
		Map<String, Object> map = Maps.newHashMap();
		if(br.hasErrors()){
			return MappingJsonView.bindMsg(br);
		}
		if (companyService.exists(companyDTO.getCompanyName())) {
			map.put("msg", "公司已存在！");
			map.put("code", false);
			return map;
		}
		companyDTO.setCode(companyDTO.getCompanyAccount());
		Company company = companyService.save(companyDTO);
		String url = request.getRequestURL().substring(0, request.getRequestURL().indexOf(request.getContextPath()))
				+ request.getContextPath() + "/c/" + company.getId();//生成链接
		company.setUrl(url);
		company = companyService.update(company);
		User user = userService.save(cellCompanyAdmin(company));
		if (null == user || user.getId() == 0) {
			map.put("msg", "添加失败！");
			map.put("code", false);
			return map;
		}
		map.put("msg", "添加成功");
		map.put("code", true);
		return map;
	}

	private User cellCompanyAdmin(Company company) {
		User user = new User();
		user.setAccount(company.getCompanyAccount());
		user.setUserName(company.getCompanyName() + "管理员");
		user.setNickName(company.getCompanyName() + "管理员");
		user.setEmail(company.getEmail());
		user.setCompany(company);
		user.setPasswordSalt(SaltUtils.getSalt(user.getAccount(), Global.S_DEFAULT_PWD));
		user.setPassword(SaltUtils.encodeMd5Hash(Global.S_DEFAULT_PWD, user.getPasswordSalt()));
		user.setVersion(1);
		user.setLastModifiedBy("admin");
		user.setLastModifiedDate(new DateTime());
		user.setAccountState(AccountState.normal);
		user.setAccountType(AccountType.companyAdmin);
		List<Role> roles = new ArrayList<>();
		Role r = roleService.findByRoleName(RoleNames.companyAdmin.name());
		Role r1 = roleService.findByRoleName(RoleNames.services.name());
		roles.add(r);
		roles.add(r1);
		user.setRoles(roles);
		return user;
	}
	

	/**
	 * 修改公司信息
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresRoles("administrator")
	@Token(remove = true)
	@ResponseBody
	public Map<String, Object> edit(@Valid CompanyUpdateDTO dto, BindingResult br) {
		Map<String, Object> map = Maps.newHashMap();
		try {
			if(br.hasErrors()){
				return MappingJsonView.bindMsg(br);
			}
			companyService.update(dto);
		} catch (Exception e) {
			log.info("#修改公司信息失败");
			map.put("msg", "修改失败！");
			map.put("code", false);
			return map;
		}
		map.put("msg", "修改成功！");
		map.put("code", true);
		return map;
	}

	/**
	 * 删除公司信息
	 * 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del/{id}")
	@ResponseBody
	@RequiresRoles("administrator")
	public boolean del(@PathVariable Integer id) throws Exception {
		List<User> userList = userService.findByCompanyId(id);
		if (userList.size() > 0 && null != userList) {
			return false;
		}
		try {
			companyService.delCompany(id);
		} catch (Exception e) {
			log.info("#删除公司信息失败");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 验证公司名称是否存在
	 * 
	 * @param companyName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/name/exists/{companyName}")
	@ResponseBody
	@RequiresRoles("administrator")
	public boolean exists(@PathVariable String companyName) throws Exception {
		boolean b = companyService.exists(companyName);
		return b;
	}

	/**
	 * 启用禁用
	 * 
	 * @param ids
	 * @param enable
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/isenable:{enable}/{ids}")
	@ResponseBody
	@RequiresRoles("administrator")
	public boolean isenable(@PathVariable String ids, @PathVariable Boolean enable) throws Exception {
		List<Integer> cids = Lists.newArrayList();
		String[] idArray = ids.split(",");
		if (idArray.length > 1) {
			Integer[] intIds = new Integer[idArray.length];
			for (int i = 0; i < idArray.length; i++) {
				intIds[i] = Integer.parseInt(idArray[i]);
			}
			cids = Arrays.asList(intIds);
		} else {
			cids.add(Integer.parseInt(ids));
		}
		boolean b = companyService.isEnable(enable, cids);
		return b;
	}

}
