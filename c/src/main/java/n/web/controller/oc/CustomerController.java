package n.web.controller.oc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

import n.core.entity.security.ShiroUser;
import n.core.jutils.bean.BeanMapper;
import n.core.validation.Token;
import n.core.web.controller.GenericController;
import n.core.web.view.MappingJsonView;
import n.entity.authc.Layer;
import n.entity.authc.Role;
import n.entity.authc.User;
import n.entity.authc.User.AccountState;
import n.entity.authc.User.AccountType;
import n.service.authc.ICompanyService;
import n.service.authc.ILayerService;
import n.service.authc.IPermissionService;
import n.service.authc.IRoleService;
import n.service.authc.IUserChatShareService;
import n.service.authc.IUserService;
import n.table.dto.authc.CustomerCreateDTO;
import n.table.dto.authc.CustomerDTO;
import n.table.dto.authc.CustomerUpdateDTO;
import n.table.dto.authc.RoleDTO;
import n.web.Global;
import n.web.Global.RoleNames;
import n.web.realm.SaltUtils;


/**
 * 客服管理控制器
 * @author jt_wangshuiping @date 2016年11月17日
 *
 */
@RequestMapping("oc/ser")
@Controller
public class CustomerController extends GenericController{
	
	@Resource
	private IUserService userService;
	@Resource
	private IRoleService roleService;
	@Resource
	private IPermissionService permissionService;
	@Resource
	private ICompanyService companyService;
	@Resource
	private ILayerService layerService;	
	@Autowired
	private IUserChatShareService userChatShareService;
	
	@RequestMapping("/index")
	public String index(){
		return "center/customer/index";
	}
	/**
	 * table
	 * @param pageable
	 * @return
	 */
	@RequestMapping("/list/")
	@RequiresRoles("companyAdmin")
	@ResponseBody
	public Map<String, Object> listForTable(Pageable pageable){
		Map<String, Object> map = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		Page<User> p = userService.findByCompanyId(u.companyId, u.id, pageable);
		Page<CustomerDTO> custList = new PageImpl<>(BeanMapper.map(p.getContent(), CustomerDTO.class), pageable, p.getTotalElements());
		map.put("data", custList.getContent());
		map.put("recordsTotal", custList.getTotalElements());
		return map;
	}
	
	@RequestMapping("/list")
	@RequiresRoles("companyAdmin")
	@ResponseBody
	public Page<CustomerDTO> list(Pageable pageable){
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		Page<User> p = userService.findByCompanyId(u.companyId, u.id, pageable);
		return new PageImpl<>(BeanMapper.map(p.getContent(), CustomerDTO.class), pageable, p.getTotalElements());
	}

	/**
	 * 新增页
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	@ResponseBody
	public List<Layer> toadd(){
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		if(null == u.companyId){
			return null;
		}else{
			return userService.findLayerByCompanyId(u.companyId);
		}
	}
	/**
	 * 新增客服
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
//	@RequiresRoles("companyAdmin")
	@ResponseBody
	public Map<String, Object> add(@Valid CustomerCreateDTO dto, BindingResult br){
		/**
		 * 1.insert User: choose default Role set Permissions
		 */
		Map<String, Object> map = Maps.newHashMap();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		if(br.hasErrors()){
			return MappingJsonView.bindMsg(br);
		}
		if(null == u.companyId){
			map.put("msg", "没有公司信息不能操作,请以客服管理员登陆！");
			map.put("code", false);
			return map;
		}
		dto.setCompany(companyService.find(u.companyId));
		if(null != userService.findByAccount(dto.getAccount())){
			map.put("msg", "该客服账号已存在！");
			map.put("code", false);
			return map;
		}
		Integer layerid=dto.getLayer().getId();
		Layer layer = layerService.find(layerid);
		dto.setLayer(layer);
		
		if(null != dto.getPerms()){//自定义权限
			String[] ps = dto.getPerms().split(",");
			List<String> permiss = Arrays.asList(ps);
			List<Role> roles = roleService.findByRoleNameIn(permiss);
			
			roles.add(roleService.findByRoleName(RoleNames.services.name()));
			dto.setRoles(roles);
		}else{
			List<Role> roles = new ArrayList<>();
			roles.add(roleService.findByRoleName(RoleNames.services.name()));
			dto.setRoles(roles);
		}
		
		
		dto.setAccountType(AccountType.services);
		dto.setPasswordSalt(SaltUtils.getSalt(dto.getAccount(), Global.S_DEFAULT_PWD));
		dto.setPassword(SaltUtils.encodeMd5Hash(Global.S_DEFAULT_PWD, dto.getPasswordSalt()));
		
		try {
			userService.save(dto);
		} catch (Exception e) {
			log.info("#添加客服信息失败");
			e.printStackTrace();
			map.put("msg", "添加失败！");
			map.put("code", false);
			return map;
		}
		map.put("msg", "添加成功");
		map.put("code", true);
		return map;
	}
	/**
	 * 修改-页面
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	@RequiresRoles("companyAdmin")
	@ResponseBody
	public Map<String, Object> find(@PathVariable Long id) {
		Map<String, Object> map = Maps.newHashMap();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		if(null == u.companyId){
			map.put("layer", null);
		}else{
			map.put("layer", layerService.findByCompany(u.companyId, null).getContent());
		}
		List<RoleDTO> roles = null;
		try {
			roles = BeanMapper.map(roleService.findByUserId(id), RoleDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null==roles || roles.size()==0)
			map.put("roles", null);
		else
			map.put("roles", roles);
		CustomerDTO customer = BeanMapper.map(userService.find(id), CustomerDTO.class);
		map.put("customer", customer);
		return map;
	}
	/**
	 * 修改
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresRoles("companyAdmin")
	@ResponseBody
	public Map<String, Object> edit(@Valid CustomerUpdateDTO dto, BindingResult br){
		Map<String, Object> map = Maps.newHashMap();
		Subject subject = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) subject.getPrincipal();
		if(br.hasErrors()){
			return MappingJsonView.bindMsg(br);
		}
		if(null == u.companyId){
			map.put("msg", "没有公司信息不能操作,请以客服管理员登陆！");
			map.put("code", false);
			return map;
		}
		//清除原来共享设置
		User user=userService.find(dto.getId());
		try {
			List<User> list=userService.findByLayerIdAndCompanyId(user.getLayer().getId(),user.getCompany().getId());
			if(list.size() > 0 && list != null){
				for(User us:list){
					if(!us.getId().equals(user.getId())){
						userChatShareService.deleteUserShare(user.getId(), us.getId());
					}
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Integer layerid=dto.getLayer().getId();
		
//		try {
//			if(null != dto.getPerms()){//自定义权限
//				String[] ps = dto.getPerms().split(",");
//				List<String> permiss = Arrays.asList(ps);
//				List<Role> roles = roleService.findByRoleNameIn(permiss);
//				
//				roles.add(roleService.findByRoleName(RoleNames.services.name()));
//				dto.setRoles(roles);
//				roleService.deleteUserRole(dto.getId(), null);
//			} else{
//				List<Role> roles = new ArrayList<>();
//				roles.add(roleService.findByRoleName(RoleNames.services.name()));
//				dto.setRoles(roles);
//				roleService.deleteUserRole(dto.getId(), null);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.info("#修改客服    移除角色关系失败！");
//			map.put("msg", "修改失败！");
//			map.put("code", false);
//			return map;
//		}
//			CustomerUpdateDTO dtos=new CustomerUpdateDTO();
//			BeanUtils.copyProperties(dto, dtos);
//			dto.setRoles(null);
			try {
				userService.updateLayer(null, dto.getId());
			} catch (Exception e) {
				e.printStackTrace();
				log.info("#修改客服    移除角色关系后保存客服1失败！");
			}
			Layer layer=layerService.find(layerid);
			dto.setLayer(layer);
			try {
				userService.update(dto);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("#修改客服    移除角色关系后保存客服2失败！");
				map.put("msg", "修改失败！");
				map.put("code", false);
				return map;
			}
		map.put("msg", "修改成功");
		map.put("code", true);
		return map;
	}
	
	/**
	 * 启用/禁用
	 */
	@RequestMapping("/lock:{state}/{id}")
	@ResponseBody
	public boolean locked(@PathVariable Long id, @PathVariable boolean state) {
		AccountState t = AccountState.normal;
		if(state){
			t = AccountState.lock;
		}
		boolean b = userService.locked(id, t);
		return b;
	}
	/**
	 * 自动分配？
	 */
	@RequestMapping("/allot:{state}/{id}")
	@ResponseBody
	public boolean autoAllot(@PathVariable Long id, @PathVariable boolean state) {
		try {
			userService.allot(id, state);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("#客服{} 设置自动分配失败",id);
			return false;
		}
		return true;
	}
	/**
	 * 删除
	 */
	@RequestMapping("/del/{id}")
	@ResponseBody
	public boolean del(@PathVariable Long id) {
		boolean b = false;
		AccountState t = AccountState.del;
		b= userService.locked(id, t);
		return b;
	}
}
