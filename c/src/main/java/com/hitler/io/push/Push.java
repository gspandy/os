package com.hitler.io.push;

import java.util.Queue;

import javax.annotation.Resource;
import javax.websocket.Session;

import org.springframework.stereotype.Component;

import com.hitler.io.protocol.Info;
import com.hitler.io.topic.IPush;
import com.hitler.io.topic.IPushStrategy;

/**
 * 消息推送实际操作者
 * 
 * @author onsoul
 *
 */
@Component("push")
public class Push implements IPush {

	@Resource
	private Pipeline pipeline; // 此通道是直接发送给用户

	@Override
	public void doPush(Info info, IPushStrategy<Info> strategy) {
		pullLocal(info, strategy);
	}

	private void pullLocal(Info info, IPushStrategy<Info> strategy) {
		Queue<Session> targets = (Queue<Session>) strategy.target(info);
		try {
			if (targets.isEmpty())
				return;
			pipeline.emit(targets, info); // 交由线程池完成
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
