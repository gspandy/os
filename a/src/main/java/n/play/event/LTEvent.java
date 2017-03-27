package n.play.event;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import n.core.jutils.bean.BeanMapper;
import n.entity.play.LN;
import n.entity.play.LN.Status;
import n.io.protocol.GmInfo;
import n.io.protocol.Info;
import n.io.protocol.GmInfo.Type;
import n.io.topic.Topic;
import n.play.worker.PrizeWorker;
import n.service.play.ILNService;

@Component("ltEvent")
public class LTEvent implements Event {

	private static final Logger log = LoggerFactory.getLogger(LTEvent.class);

	@Resource
	private ILNService lnService;
	@Resource
	private Topic topic;

	@Resource
	private PrizeWorker prizeWorker;

	@Override
	public void open(LN ln) throws Exception {
		log.info("###开盘事件触发!!!");
		lnService.update(ln);
		Info info = mkInfo(ln);
		topic.production(info); // 生产消息
	}

	@Override
	public void closed(LN ln) throws Exception {
		log.info("###封盘事件触发!!!");
		lnService.update(ln);
		Info info = mkInfo(ln);
		topic.production(info); // 生产消息
	}

	@Override
	public void lottery(LN ln) throws Exception {
		Info info = mkInfo(ln);
		topic.production(info); // 生产消息
		ln.setStatus(Status.LT_ED);
		lnService.update(ln);
		prize(ln); // 计算派奖
	}

	@Override
	public void prize(LN ln) throws Exception {
		ln.setStatus(Status.PRIZE);
		prizeWorker.doPrize(ln);
	}

	@Override
	public void crawler(LN ln) {
	}

	private GmInfo mkInfo(LN ln) {
		GmInfo info=new GmInfo();
		info.setType(Type.lt);
		info.setBody(BeanMapper.objToJson(ln));
		return info;
	}

}
