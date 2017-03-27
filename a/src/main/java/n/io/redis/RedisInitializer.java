package n.io.redis;

import java.io.IOException;
import java.io.InputStream;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redis 读写组件redidsson的初始化 加载配置文件创建客户端,通过 & Configuration &Bean 注入到Spring
 * context 如果上下文扫包加入此类,就可以在上下文其它位置使用Redis进行读写操作 连接池与线程控制由配置文件声明. for Sample:
 * <hr/>
 * &Resource RedissonClient redissonClient
 * 
 * @author onsoul@qq.com
 * @time 2016年11月30日 上午10:07:31
 * @version 1.0
 */
@Configuration
public class RedisInitializer {

	private static Logger log = LoggerFactory.getLogger(RedisInitializer.class);

	@Bean()
	public RedissonClient init() {
		RedissonClient client = Redisson.create(cellConfig());
		return client;
	}

	private Config cellConfig() {
		Config config;
		try {
			String conf_file = "/multi/redis-conf.json";
			InputStream conf = RedisInitializer.class.getResourceAsStream(conf_file);
			log.info("##INIT REIDS CONFIG:{}", conf_file);
			config = Config.fromJSON(conf);
			return config;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
