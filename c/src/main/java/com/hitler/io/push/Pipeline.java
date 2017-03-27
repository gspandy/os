package com.hitler.io.push;

import java.io.IOException;
import java.util.Queue;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hitler.core.jutils.bean.BeanMapper;
import com.hitler.io.protocol.Info;

/**
 * 消息推送管道 第一部分此类挂载了一个与本机关联的消息消费者组件，如果从Topic消费到此
 * 
 * @author onsoul
 *
 */
@Component
public class Pipeline {

	private static final Logger log = LoggerFactory.getLogger(Pipeline.class);

	/**
	 * 单个目标 发送消息
	 * 
	 * @param target
	 * @param protocol
	 */
	public void emitOnce(Session target, Info info) {
		String json_pro = BeanMapper.objToJson(info);
		try {
			if (null != target) {
				target.getBasicRemote().sendText(json_pro);
			} else {
				log.error("##session is null.msg:{}" + json_pro);
			}
		} catch (IOException e) {
			log.error("##SID:{} send msg :{}", target.getId(), json_pro);
		}
	}

	/**
	 * 多个目标发送消息
	 * 
	 * @param targets
	 * @param protocol
	 * @throws Exception
	 */
	public void emit(Queue<Session> targets, Info info) throws Exception {
		if (targets.isEmpty())
			return;
		targets.forEach(target -> {
			emitOnce(target, info);
		});
	}

}
