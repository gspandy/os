package n.wizard.config;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * yml配置加载至全局
 * 
 * @author JTwise
 * @date 2016年5月13日 下午5:29:43
 */
public class YmlPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private Map<String, Object> ymlMap;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		try {
			props.putAll(ymlMap);
			System.out.println(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setYmlMap(Map<String, Object> ymlMap) {
		this.ymlMap = ymlMap;
	}

}
