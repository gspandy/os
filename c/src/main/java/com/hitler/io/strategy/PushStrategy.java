package com.hitler.io.strategy;

import java.util.Queue;

import org.springframework.stereotype.Component;

import com.hitler.core.jutils.base.IPUtils;
import com.hitler.io.cache.Side;
import com.hitler.io.protocol.GmInfo;
import com.hitler.io.topic.IPushStrategy;

@Component
public class PushStrategy implements IPushStrategy<GmInfo> {

	@Override
	public String topic() {
		return IPUtils.getLocalHostName();
	}

	@Override
	public Queue<?> target(GmInfo info) {
		return Side.CLI;
	}
}
