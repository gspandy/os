package n.play.worker;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import n.entity.play.BetRecord;
import n.entity.play.LN;
import n.play.calc.Atlis;
import n.service.play.IBetRecordService;

@Component
public class PrizeWorker {

	private static Logger log = LoggerFactory.getLogger(PrizeWorker.class);

	@Resource
	private IBetRecordService betRecordService;

	public boolean doPrize(LN ln) {
		List<BetRecord> results = betRecordService.findAllByLN(ln);
		log.info("## {}, 单量:{} 开始进行派奖!!!", ln.getIssue(), results.size());
		if (!results.isEmpty()) {
			String arrow = Atlis.spilt(ln.getLotteryNumber());
			for (BetRecord record : results) {
				BetRecord calced = Atlis.calc(record, arrow);
			}
			return false;
		} else {
			return false;
		}

	}

}
