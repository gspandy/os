package n.io.topic;

import javax.annotation.Resource;

import org.redisson.api.RList;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import n.io.protocol.Info;

@Component("topic")
public class Topic {

	private static final String CLUSTER_KEY = "CLUSTER_CONFIG";

	private static Logger log = LoggerFactory.getLogger(Topic.class);

	public enum TopicName {
		LT_, USER_STATUS_
	}

	@Resource
	private RedissonClient redissonClient;

	/**
	 * 消费订阅
	 * @param push
	 * @param strategy
	 */
	public void comsumer(IPush push, IPushStrategy<Info> strategy) {
		String topic_name = strategy.topic(); // 从策略处获取topic name
		RTopic<Info> topic = redissonClient.getTopic(topic_name);
		log.info("### INIT TOPIC: {} ", topic_name);
		topic.addListener(new MessageListener<Info>() {
			@Override
			public void onMessage(String channel, Info info) {
				push.doPush(info, strategy);
			}
		});
	}

	/**
	 * 生产订阅消息
	 * 
	 * @param protocol
	 * @param topic_name
	 * @return
	 */
	public boolean production(Info info, String topic_name) {
		log.info("### TOPIC [{}] PRODUCTION AN MSG:{}", topic_name, info.body());
		// 在其他线程或JVM节点
		RTopic<Info> topic = redissonClient.getTopic(topic_name);
		long clientsReceivedMessage = topic.publish(info);
		return clientsReceivedMessage > 0;
	}

	/**
	 * 生产订阅消息
	 * 
	 * @param protocol
	 * @param topic_name
	 * @return
	 */
	public boolean production(Info info, TopicName emtopic) {
		String topic_name = emtopic.toString();
		log.info("### TOPIC [{}] PRODUCTION AN MSG:{}", topic_name, info.body());
		// 在其他线程或JVM节点
		RTopic<Info> topic = redissonClient.getTopic(topic_name);
		long clientsReceivedMessage = topic.publish(info);
		return clientsReceivedMessage > 0;
	}

	/**
	 * 我的目标是要做集群消息推送的，怎么可以和上面那条咸鱼比.
	 * 
	 * @param info
	 *            消息
	 * @param topics
	 *            推送的目标Topic 列表
	 * @return
	 */
	private boolean production(Info info, RList<String> serv_hosts) {
		long queue_size = serv_hosts.size();
		long clientsReceivedMessage = 0;
		for (String topic_name : serv_hosts) {
			log.info("### TOPIC [{}] PRODUCTION AN MSG:{}", topic_name, info.body());
			// 在其他线程或JVM节点
			RTopic<Info> topic = redissonClient.getTopic(topic_name);
			clientsReceivedMessage += topic.publish(info);
		}
		return queue_size == clientsReceivedMessage;
	}

	/**
	 * 消息推送给集群
	 * @param info
	 * @return
	 */
	public boolean production(Info info) {
		RList<String> cluster = redissonClient.getList(CLUSTER_KEY);
		return production(info, cluster);
	}

	/**
	 * 服务器注入集群
	 * 
	 * @param host
	 * @return
	 */
	public boolean registerCluster(String host) {
		RList<String> cluster = redissonClient.getList(CLUSTER_KEY);
		return cluster.add(host);
	}
	
	public boolean contains(String host){
		RList<String> cluster = redissonClient.getList(CLUSTER_KEY);
		return cluster.contains(host);
	}

	/**
	 * 服务器离开集群
	 * 
	 * @param host
	 * @return
	 */
	public boolean leaveCluster(String host) {
		RList<String> cluster = redissonClient.getList(CLUSTER_KEY);
		return cluster.remove(host);
	}

}
