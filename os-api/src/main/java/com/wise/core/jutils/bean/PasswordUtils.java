package com.wise.core.jutils.bean;

import org.apache.commons.lang.RandomStringUtils;

import com.wise.core.jutils.encrypt.MD5Utils;

/**
 * 密码加密
 * @author onsou
 *
 */
public class PasswordUtils {

	public static String encode(String pwd, String salt) {
		return MD5Utils.encode(pwd + salt);
	}

	public static String generateSalt() {
		return RandomStringUtils.randomNumeric(6);
	}

}
