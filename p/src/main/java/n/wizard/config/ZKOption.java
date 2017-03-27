package n.wizard.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.alibaba.druid.util.StringUtils;

/**
 * 利用zookeeper做为整个上下文全局properties的管理端
 * @author JTwise
 * @date 下午3:59:11
 * 
 */
public class ZKOption {

	public static final String DEF_CONNECT_STRING = "127.0.0.1:2181";

	public static final int MAX_RETRIES = 3;

	public static final int BASE_SLEEP_TIMEMS = 3000;

	public static final String NAME_SPACE = "ctx";

	private String zkurl;
	private String rootSpace;

	public CuratorFramework get() {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIMEMS, MAX_RETRIES);
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString(zkUrl()).retryPolicy(retryPolicy)
				.namespace(NAME_SPACE).build();
		client.start();
		return client;
	}

	private String zkUrl() {
		if (!StringUtils.isEmpty(zkurl)) {
			return zkurl;
		}
		System.err.println("WARNING:系统采用了zookeeper做为配置中心,但是zookeeper url没有配置.");
		return DEF_CONNECT_STRING;
	}

	private String zkSpace() {
		if (!StringUtils.isEmpty(rootSpace)) {
			return rootSpace;
		}
		System.err.println("WARNING:系统采用了zookeeper做为配置中心,但是zookeeper NAME SPACE没有配置.采用默认:" + NAME_SPACE);
		return NAME_SPACE;
	}

	public void setZkurl(String zkurl) {
		this.zkurl = zkurl;
	}

	public void setRootSpace(String rootSpace) {
		this.rootSpace = rootSpace;
	}

}
