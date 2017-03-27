package n.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import n.table.dto.info.LeaveMsgDTO;

public class Global {

	public static final String USER_MENU = "user_menu";
	
	public static final String VISITOR_NAME="VISITOR_NAME";
	
	public static final String GUEST_INFO="GUEST_INFO";
	
	public static final String S_DEFAULT_PWD = "123456";//客服、公司管理员初始化密码
	
	public static final String TOKEN="token";
	
	public static final String USERID="userId";
	/**
	 * 默认角色名枚举
	 * @author jt_wangshuiping @date 2016年11月22日
	 *
	 */
	public enum RoleNames{
		administrator,
		companyAdmin,
		services,
		normal
	}
	
	public static Map<Long, String> sessionMap = new HashMap<Long, String>();
	
	public static Map<Integer, Map<String, List<LeaveMsgDTO>>> LEAVE_MSGS = Maps.newConcurrentMap();
}
