package com.hitler.web.realm;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
/**
 * 加盐工具类
 * @author jt_wangshuiping @date 2016年11月2日
 *
 */
public class SaltUtils {
	public static String getSalt(String username, String password) {
		StringBuffer sb = null;
		sb = new StringBuffer();
		sb.append(username).append(password);
		return new Md5Hash(sb.toString(), RandomStringUtils.randomNumeric(6)).toHex();
	}
	
	public static String encodeMd5Hash(String pwd, String salt) {
		return new SimpleHash("md5", pwd, salt, 2).toHex();
	}
}
