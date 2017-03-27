package com.hitler.io.push;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hitler.core.jutils.base.IPUtils;
import com.hitler.io.protocol.Info;
import com.hitler.io.topic.IPush;
import com.hitler.io.topic.IPushStrategy;
import com.hitler.io.topic.Topic;


/**
 * 此类用于实现服务之间的消息传递
 * @author onsoul@qq.com
 * 2017年3月20日 上午10:18:17
 * 
 */
@Component
public class PushCluster {

	private static Logger log = LoggerFactory.getLogger(PushCluster.class);

	@Resource
	private Topic topic;   
	
	@Resource
	private IPush push;
	
	@Resource
	private IPushStrategy<Info> pushStrategy;    //基础消息通道的策略
	
	/**
	 * 如果到此类与
	 */
	@PostConstruct 
	private void register(){
		log.info("#开始注册服务器到集群,并挂载此机为消费者...");
		topic.registerCluster(IPUtils.getLocalHostName());
		mount();
		log.info("#开始注册服务器到集群,并挂载此机为消费者...");
	}
	
	
	@PreDestroy
	private void leave(){
		topic.leaveCluster(IPUtils.getLocalHostName());
		log.info("#销毁服务器集群于缓存的信息完成.");
	}
	
	/**
	 * 挂载此机为消费者
	 */
	private void mount(){
		topic.comsumer(push, pushStrategy);
	}
	
	/**
	 * 如果消息目标是其它服务器.用此生产者进行传递
	 * @param info
	 * @return
	 */
	public boolean emitS(Info info) {
		String host = info.getServerHost();
		if (topic.contains(host)) {
			return topic.production(info, info.getServerHost());
		} else {
			log.error("###信息推送至服务器:{},但是目标服务器未注册到集群列表中.", host);
		}
		return false;
	}
}
