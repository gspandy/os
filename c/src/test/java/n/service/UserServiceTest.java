package n.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import n.core.jutils.bean.BeanMapper;
import n.core.repository.DynamicSpecifications;
import n.core.repository.OP;
import n.core.repository.SearchFilter;
import n.entity.authc.Layer;
import n.entity.authc.Role;
import n.entity.authc.User;
import n.entity.authc.User_;
import n.entity.authc.User.AccountState;
import n.entity.authc.User.AccountType;
import n.entity.authc.User.OnlineState;
import n.service.authc.IRoleService;
import n.service.authc.IUserService;
import n.table.dto.authc.UserDTO;
import n.web.realm.SaltUtils;
import n.web.test.BaseTest;

public class UserServiceTest extends BaseTest {

	@Resource
	private IUserService userService;
	@Resource
	private IRoleService roleService;

	/**
	 * 初始化admin
	 * 
	 * @throws Exception
	 */
	@Test
	public void save() throws Exception {
		// if(userService.findByAccount("admin")==null){
		System.err.println("create admin begin!");
		userService.save(cell());
		System.err.println("create admin end!");
		// }
		// userService.update(cell());
	}

	@Test
	public void tesPage() {
		Page<User> p = null;
		try {
			p = userService.findByCompanyId(1, 1l, new PageRequest(0, 10));
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (User dto : p) {
			System.err.println(dto.getAccount());
		}
	}

	@Test
	public void tes() throws Exception {
		String s = "admin,permission";
		String[] ps = s.split(",");
		List<String> plist = Arrays.asList(ps);
		for (String str : plist) {
			System.err.println(str);
		}
	}

	@Test
	public void findLayer() {
		List<Layer> u = userService.findLayerByCompanyId(1);
		for (Layer l : u) {
			System.err.println(l.getLayerName());
		}
	}

	@Test
	public void add() throws Exception {
		User user = new User();
		user.setAccount("testaaa");
		user.setUserName("管理员");
		user.setEmail("dddd@qq.com");
		user.setCompany(null);
		user.setPasswordSalt(SaltUtils.getSalt(user.getAccount(), "123456"));
		user.setPassword(SaltUtils.encodeMd5Hash("123456", user.getPasswordSalt()));
		user.setVersion(1);
		// user.setLastModifiedDate(new DateTime());
		user.setAccountState(AccountState.normal);
		user.setAccountType(AccountType.companyAdmin);
		List<Role> roles = new ArrayList<>();
		Role r = roleService.findByRoleName("companyAdmin");
		Role r1 = roleService.findByRoleName("services");
		roles.add(r);
		roles.add(r1);
		user.setRoles(roles);
		user = userService.save(user);
		System.err.println(user.getId());
	}

	@Test
	public void findTest() {
		User u1 = userService.find(1L);
		User user = userService.authc("admin", SaltUtils.encodeMd5Hash("admin", u1.getPasswordSalt()));
		log.info("###user info:{}", user.getCreatedDate());
		// UserDTO user = userService.findByID(1L);
		UserDTO u = BeanMapper.map(user, UserDTO.class);
		System.err.println(u.getAccount());
	}

	@Test
	public void findRolesTest() {
		User user = userService.find(1l);
		System.err.println("Roles:" + user.getRoles().get(0).getRoleName());
	}

	private User cell() {
		User admin = new User();
		// User admin = userService.find(1L);
		admin.setEmail("redleaf@foxmail.com");
		// admin.setId(1l);
		admin.setAccount("test");
		admin.setPasswordSalt(SaltUtils.getSalt(admin.getAccount(), "test"));
		admin.setPassword(SaltUtils.encodeMd5Hash("test", admin.getPasswordSalt()));
		admin.setUserName("客服");
		admin.setVersion(1);
		admin.setAccountState(AccountState.normal);
		admin.setAccountType(AccountType.administrator);// 0-访客，1-客服，2-管理员，3-超级管理员
		return admin;
	}

	private User visitor() {
		User user = new User();
		user.setAccount("v20161025");
		user.setEmail("123456@163.com");
		user.setLoginIp("192.168.0.36");
		user.setLastLoginTime(new DateTime());
		user.setMobile("18888888888");
		user.setUserName("visitor");
		user.setNickName("visitor1");
		user.setAutoAllot(false);
		user.setOnlineState(OnlineState.off);
		user.setPassword("123456");
		return user;
	}

	/**
	 * 根据用户类型和账户状态，区分出客服、访客、管理员列表
	 */
	@Test
	public void findUserTest() {
		// User user = userService.findByAccount("admin");
		// UserDTO u =BeanMapper.map(user, UserDTO.class);
		// System.err.println(u.getAccount());
		Specification<User> spec = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// query.where(cb.equal(root.get(User_.accountType),3),cb.equal(root.get(User_.accountState),
				// 0));
				// return cb.equal(root.get(User_.accountType),3);
				List<Predicate> predList = new ArrayList<>();
				predList.add(cb.equal(root.get(User_.accountType), 3));
				predList.add(cb.equal(root.get(User_.accountState), 0));
				query.where(predList.toArray(new Predicate[predList.size()]));
				return null;
			}
		};
		
		Collection<SearchFilter> filters = Arrays.asList(new SearchFilter(User_.accountType.getName(), OP.EQ, 3),
				new SearchFilter(User_.accountState.getName(), OP.EQ, 0));
		Specification<User> f = DynamicSpecifications.bySearchFilter(filters);
		List<User> user = userService.findAll(f);
		for (User u : user) {
			System.err.println(u.getAccount());
		}
		Page<User> pUser = userService.findAll(spec,
				new PageRequest(0, 10, new Sort(Direction.ASC, User_.account.getName())));
		System.err.println("记录数:" + pUser.getTotalElements());
		for (User pu : pUser.getContent()) {
			System.err.println(pu.getAccount() + "分页后的条件");
		}
	}
}
