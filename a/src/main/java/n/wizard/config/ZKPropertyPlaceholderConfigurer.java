package n.wizard.config;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 全局配置交由Zookeeper管理
 * @author JTwise
 * @date 2016年5月13日 下午5:29:43
 */
public class ZKPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	public static final String PATH = "zk.root.path";

	private Config zkConfig;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		try {
			fillCustomProperties(props);
			System.out.println(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillCustomProperties(Properties props) throws Exception {
		byte[] data = getData(props);
		fillProperties(props, data);
	}

	private void fillProperties(Properties props, byte[] data) throws UnsupportedEncodingException {
		String cfg = new String(data, "UTF-8");
		if (StringUtils.isNotBlank(cfg)) {
			// 完整的应该还需要处理：多条配置、value中包含=、忽略#号开头
			String[] cfgItem = StringUtils.split(cfg, "=");
			props.put(cfgItem[0], cfgItem[1]);
		}
	}

	private byte[] getData(Properties props) throws Exception {
		String path = props.getProperty(PATH);
		return zkConfig.getConfig(path);
	}

	public void setZkConfig(Config zkConfig) {
		this.zkConfig = zkConfig;
	}

}
