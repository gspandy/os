package n.wizard.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;

public class ZKConfig implements Config {
	
	private ZKOption zkOption;
	
	@Override
	public byte[] getConfig(String path) throws Exception {
		CuratorFramework client = zkOption.get(); //注入客户端
		if (!exists(client, path)) {
			System.out.println("###zk config Path " + path + " does not exists. frist crating...");
			client.create().forPath(path);
		}
		return client.getData().forPath(path);
	}

	private boolean exists(CuratorFramework client, String path) throws Exception {
		Stat stat = client.checkExists().forPath(path);
		return !(stat == null);
	}

	public void setZkOption(ZKOption zkOption) {
		this.zkOption = zkOption;
	}
}
