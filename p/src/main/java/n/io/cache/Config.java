package n.io.cache;

public class Config {

	public static Integer PROVIDE_MAX_LIMIT = 20;

	public static String U_HOST_INFO = "U_HOST_INFO";

	public static String V_HOST_INFO = "V_HOST_INFO"; // 游客host信息

	public static String SERV_STATE_KEY = "CS_"; // 客服信息列表CS_[公司ID] key:serv_id

	// value:protocol
	public static String RELATION_US = "U_S_"; // 用户与客服对应关系LIST
	public static String RELATION_SU = "S_U_"; // 客服与用户对应关系LIST

	public static String OLD_RELATION = "OLD_U_S_";
	public static String LAYER_SER_U_RELATION = "L_S_U_R";// 保存分组类型客服关系

	public static String CRU_LN = "LN_CUR_"; // 当前期
	public static String LAST_LN = "LN_LAST_";

	// ------------------------------------------分割线
	public static String LEAVE_MSGS = "LEAVE_MSGS";
	
}
