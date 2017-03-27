package n.core.web.listener;

import java.util.HashMap;
import java.util.Map;

public class SessionCache {

	private static Map<Integer, String> sessionIdMap = new HashMap<Integer, String>();
	
	public static String getCacheSessionId(Integer userId) {
		return sessionIdMap.get(userId);
	}
	
	public static void setCacheSessionId(Integer userId, String sessionId) {
		sessionIdMap.put(userId, sessionId);
	}
}
