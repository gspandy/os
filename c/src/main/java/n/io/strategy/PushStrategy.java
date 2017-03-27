package n.io.strategy;

import java.util.Queue;

import org.springframework.stereotype.Component;

import n.core.jutils.base.IPUtils;
import n.io.cache.Side;
import n.io.protocol.GmInfo;
import n.io.topic.IPushStrategy;

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
