package n.web.controller.back;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import n.core.exception.BusinessException;
import n.core.jutils.bean.BeanMapper;
import n.core.service.support.IGenericService;
import n.entity.authc.Company;
import n.entity.authc.Role;
import n.entity.authc.User;
import n.service.authc.ICompanyService;
import n.service.authc.IRoleService;
import n.service.authc.IUserService;
import n.table.authc.UserTable;
import n.table.dto.authc.RoleDTO;
import n.table.dto.authc.UserCreateDTO;
import n.table.dto.authc.UserDTO;
import n.table.dto.authc.UserNickUpdateDTO;
import n.web.controller.support.CRUDController;
import n.web.realm.SaltUtils;

/**
 * 后端用户控制
 * @author onsoul
 *
 */

@Controller
@RequestMapping(value = "back/"+UserController.PATH)
public class UserController extends CRUDController<User, Long, UserDTO, UserCreateDTO, UserNickUpdateDTO, UserTable<UserDTO>> {
	
	public static final String PATH = "admin/user";
	
	public UserController() {
		super(PATH);
	}

	@Autowired
	private IUserService userService;
	@Resource
	private ICompanyService companyService;
	@Resource
	private IRoleService roleService;
	
	@Override
	protected IGenericService<User, Long> getService() {
		return userService;
	}

	@Override
	protected void preCreate(Model model, UserCreateDTO createDTO, ServletRequest request) throws Exception {
		//model.addAttribute("userLayerList", userLayerService.findAll(UserLayerDTO.class));
	}
	
	@Override
	protected void postCreate(Model model, UserCreateDTO createDTO, BindingResult br) throws Exception {
		Company c = createDTO.getCompany();
		c.setCompanyAccount(createDTO.getAccount());
		if(companyService.findByCompanyName(c.getCompanyName()) !=null){
			throw new BusinessException("公司已经存在");
		}
		Company company = companyService.save(c);
		String salt = SaltUtils.getSalt(createDTO.getAccount(), createDTO.getPassword());
		createDTO.setPasswordSalt(salt);
		createDTO.setPassword(SaltUtils.encodeMd5Hash(createDTO.getPassword(), salt));
		createDTO.setCompany(company);
	}
	/**
	 * 角色分配页面
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/allot/role/{id}", method = RequestMethod.GET)
	public String allotRole(Model model, @PathVariable Integer id) throws Exception {
//	public String allotRole(HttpServletRequest request, @PathVariable Integer id) throws Exception {
		UserDTO u = BeanMapper.map(userService.find((long)id), UserDTO.class);
		List<Role> rs = roleService.findAll(); 
		List<RoleDTO> roles = BeanMapper.map(rs, RoleDTO.class);
//		request.setAttribute("roles",roles);
//		request.setAttribute("user", u);
		model.addAttribute("roles",roles);
		model.addAttribute("user", u);
		return PATH + "/allotrole";
	}
	@RequestMapping(value = "/allot/role/save", method = RequestMethod.POST)
	public String allotSave(Integer userId, Integer roleId) throws Exception{
		User user = userService.find((long)userId);
		if(null == user){
			throw new BusinessException("用户不存在");
		}
		roleService.deleteUserRole((long)userId, roleId);
		if(null != roleId){
			roleService.insertUserRole((long)userId, roleId);
		}
		return "redirect:/back/" + PATH;
	}

}
