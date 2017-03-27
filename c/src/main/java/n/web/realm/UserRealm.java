package n.web.realm;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import n.core.entity.security.ShiroUser;
import n.core.jutils.bean.PasswordUtils;
import n.entity.authc.User;
import n.entity.authc.User.AccountState;
import n.entity.authc.User.OnlineState;
import n.service.authc.IPermissionService;
import n.service.authc.IRoleService;
import n.service.authc.IUserService;


/**
 * 权限域,用于处理用户认证与授权 认证:判定用户账号密码 授权:读取用户的角色与权限
 * 认证与授权产生的对应信息会交给Shiro管理,这两份信息实质会交给WEB Session. 以供前端视图与控制器做相应的判定处理
 * @author JTWise 2016年7月25日 上午10:18:22
 */
public class UserRealm extends AuthorizingRealm {
	private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

	@Resource
	private IUserService userService;
	
	@Resource
	private IRoleService roleService;

	@Resource
	private IPermissionService permissionService;

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
		Object obj = principals.getPrimaryPrincipal();
		
		if (obj instanceof ShiroUser) {
			ShiroUser userinfo = (ShiroUser) obj;
			Long userId = userinfo.getId();
			
			Set<String> roleSet = roleService.findUserRolesByID(userId);
			Set<String> permissionSet = permissionService.findUserPermissionByUID(userId);
			
			// 用户授权
			auth.setRoles(roleSet);
			auth.addStringPermissions(permissionSet);
		}

		return auth;
	}
	
	/**
	 * 认证回调函数， 登陆时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		UserToken token = (UserToken) authcToken;
		String token_uname = token.getUsername();

		logger.info("### {} 进入认证...", token_uname);

		User user = userService.findByAccount(token_uname);

		// 用户是否存在
		if (null == user || user.getAccountState()==AccountState.del) {
			throw new UnknownAccountException("帐号不存在！");
		}
		// 账号状态
		boolean status = user.getAccountState() == AccountState.lock ;
		if (status) {
			throw new LockedAccountException("账号异常！");
		}
		
		boolean effectivePwd = false;

		if (effectivePwd == false) {
			// 密码是否正确
			String paswordMd5 = PasswordUtils.encode(token.getLoginPassword(), user.getPasswordSalt());
			effectivePwd = user.getPassword().equals(paswordMd5);
		}
		if(effectivePwd = false){
			//密码错误次数处理，可以锁定账户等操作(提示密码错误)
			throw new IncorrectCredentialsException("密码错误");
		}
		//更新在线状态，用户状态...等信息
		user.setLastLoginTime(user.getLoginTime());
		user.setLastLoginIp(user.getLoginIp());
		user.setLoginIp(token.getLoginIp());
		user.setLoginTime(new DateTime());
		user.setAccountState(AccountState.normal);  //
		
		String pwd = user.getPassword();
		Integer companyId = null == user.getCompany() ? null:user.getCompany().getId();
		String companyName = null == user.getCompany() ? null:user.getCompany().getCompanyName();
		
		ShiroUser SU = new ShiroUser(user.getId(), user.getNickName(), user.getAccount(), pwd, user.getPasswordSalt(),user.getAccountType(),companyId, user.getUserIcon(), user.getAccountState(), user.getEmail(), user.getMobile(), user.getLoginIp(), user.getLoginTime(), user.getLastLoginIp(), user.getLastLoginTime(), companyName);
		
		ByteSource salt_pwd = ByteSource.Util.bytes(user.getPasswordSalt());
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(SU, pwd, salt_pwd, getName());
		
		
		try {
			user = userService.online(user,OnlineState.online);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("### {} (登录成功更改状态) {},{}", user.getUserName(),user.getOnlineState(),user.getLoginTime());
//		doGetAuthorizationInfo(authenticationInfo.getPrincipals());
		return authenticationInfo;
	}

	protected void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	protected void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	
	

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public void setPermissionService(IPermissionService permissionService) {
		this.permissionService = permissionService;
	}

}
