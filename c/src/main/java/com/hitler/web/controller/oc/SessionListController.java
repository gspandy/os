package com.hitler.web.controller.oc;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hitler.core.entity.security.ShiroUser;
import com.hitler.core.web.controller.GenericController;
import com.hitler.service.authc.IUserService;
import com.hitler.table.dto.authc.UserDTO;

/**
 * 会话列表控制器
 * @author jt_wangshuiping @date 2017年1月17日
 *
 */
@Controller
@RequestMapping("oc/session")
public class SessionListController extends GenericController {
	
	@Resource
	private IUserService userService;
	
	/**
	 * 可用的客服列表
	 * @param id 分组id（问题分类id）
	 * @return
	 */
	@RequestMapping(value = "/usable/serv/{id}")
	@ResponseBody
	public List<UserDTO> findServ(@PathVariable Integer id){
		Subject sub = SecurityUtils.getSubject();
		ShiroUser u = (ShiroUser) sub.getPrincipal();
		return userService.findUserByLayerIdAndCompanyIdetc(id, u.companyId, u.id);
	}

}
