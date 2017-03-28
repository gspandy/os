package com.hitler.core.jutils.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import com.alibaba.dubbo.container.Main;

public class IPUtils {
	/**
	 * 获取本机内网IP
	 * @return
	 */
	public static String getLocalHostIP() {
		return getLocalHost().getHostAddress();
	}

	/**
	 * 获取本机当前用户名
	 * @return
	 */
	public static String getLocalHostName() {
		return getLocalHost().getHostName();
	}

	public static InetAddress getLocalHost() {
		InetAddress netAddress = null;
		try {
			netAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return netAddress;
	}

	/**
	 * 获取外网IP  
	 * @return
	 */
	public static String getOuterNetIP(String checkURL) {
		/*String checkURL = "http://iframe.ip138.com/ic.asp";*/
		HttpURLConnection conn = null;
		String outerIPStr = "";
		try {
			URL url = new URL(checkURL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((outerIPStr = reader.readLine()) != null) {
				if (outerIPStr.indexOf("[") > 0) {
					outerIPStr = outerIPStr.substring(outerIPStr.indexOf("[") + 1, outerIPStr.indexOf("]"));
					break;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outerIPStr;
	}
	
}
