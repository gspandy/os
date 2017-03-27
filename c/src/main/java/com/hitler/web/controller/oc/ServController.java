package com.hitler.web.controller.oc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.hitler.core.entity.security.ShiroUser;
import com.hitler.core.validation.Token;
import com.hitler.core.web.controller.GenericController;
import com.hitler.entity.authc.Permission;
import com.hitler.service.authc.IPermissionService;
import com.hitler.service.authc.IUserService;
import com.hitler.table.dto.authc.MenuTreeDTO;
import com.hitler.web.Global;

@Controller
public class ServController extends GenericController {

	@Resource
	private IPermissionService permissionService;
	@Resource
	private IUserService userService;
	
	@RequestMapping("/oc/serv")
	public String serv(Model model) throws Exception {

		// 可以添加主题属性
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		ShiroUser u = (ShiroUser) subject.getPrincipal();

		@SuppressWarnings("unchecked")
		List<MenuTreeDTO> o = (List<MenuTreeDTO>) session.getAttribute(Global.USER_MENU);
		List<MenuTreeDTO> menuList = new ArrayList<MenuTreeDTO>();
		
		if (o == null || o.size() == 0) {
			List<Permission> plist = permissionService.findByUserId(u.id);
			menuList = menuTree(plist);
			session.setAttribute(Global.USER_MENU, menuList);
		} else {
			menuList = o;
		}
		/*Protocol $proto=MakeProtocol.servPrepare(u.getCompanyId(),u.getId(), u.getAccount(),u.nickname,session.getId().toString());
		String protocol = BeanMapper.objToJson($proto);
		model.addAttribute("memuList", menuList);
		model.addAttribute("protocol", protocol);
		model.addAttribute("curUser", u.account);
		model.addAttribute("curImg", userService.findByID(u.id).getUserIcon());*/
		return "serv/index";
	}
	
	/**
	 * ajax生成token
	 * @return
	 */
	@RequestMapping(value = "/token")
	@Token(save = true)
	@ResponseBody
	public Map<String, Object> token(HttpServletRequest request){
		Map<String, Object> map = Maps.newHashMap();
		map.put("token", request.getSession().getAttribute(Global.TOKEN));
		return map;
	}

	/**
	 * 菜单树
	 */
	private List<MenuTreeDTO> menuTree(List<Permission> list) {
		List<MenuTreeDTO> menuTreeList = new ArrayList<MenuTreeDTO>();
		menuTreeList = generateMenuTree(list, menuTreeList, null, 1);
		return menuTreeList;
	}

	private List<MenuTreeDTO> generateMenuTree(List<Permission> allList, List<MenuTreeDTO> dtoList,
			Integer parentPermissionId, int floor) {
		int _pmType = 2;
		
		if (allList != null && allList.size() > 0) {
			for (Permission p : allList) {
				if (_pmType == p.getPermissionType()) {
					if (floor == p.getFloor()) {
						MenuTreeDTO pt = new MenuTreeDTO();
						pt.setId(p.getId());
						pt.setName(p.getPermissionName());
						pt.setDeep(p.getDeep());
						pt.setPath(p.getPath());
						pt.setIcon(p.getIcon());
						pt.setIsDisplay(p.getIsDisplay());

						// 递归查找子权限
						List<MenuTreeDTO> subTreeList = generateMenuTree(allList, new ArrayList<MenuTreeDTO>(),
								p.getId(), -1);
						
						pt.setSubList(subTreeList);
						dtoList.add(pt);
					} else if (parentPermissionId == p.getParentPermissionId()) {
						MenuTreeDTO pt = new MenuTreeDTO();
						pt.setId(p.getId());
						pt.setName(p.getPermissionName());
						pt.setDeep(p.getDeep());
						pt.setPath(p.getPath());
						pt.setIsDisplay(p.getIsDisplay());

						// 递归查找子权限
						List<MenuTreeDTO> subTreeList = generateMenuTree(allList, new ArrayList<MenuTreeDTO>(),
								p.getId(), -1);
						pt.setSubList(subTreeList);
						dtoList.add(pt);
					}
				}
			}
		}
		return dtoList;
	}

	public void setPermissionService(IPermissionService permissionService) {
		this.permissionService = permissionService;
	}

	
}
