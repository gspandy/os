package n.service.play;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import n.entity.play.BetRecord;
import n.entity.play.LN;
import n.entity.play.PlayType;
import n.service.play.IBetRecordService;
import n.service.play.ILNService;
import n.service.play.IPlayTypeService;
import n.table.dto.play.BetRecordDTO;
import n.test.BaseTest;

public class BetRecordServiceTest extends BaseTest {

	@Resource
	private IBetRecordService betRecordService;

	@Resource
	private ILNService lnService;

	@Resource
	private IPlayTypeService playTypeService;

	@Test
	public void findTest() {
		LN ln = lnService.find(6148l);
		List<BetRecord> result = betRecordService.findAllByLN(ln);
		log.info("###Result size {}", result.size());
	}

	@Test
	public void betTest() throws Exception {
		LN ln = lnService.find(6184l);
		PlayType pType = playTypeService.find(1);
		
		BetRecordDTO bet=new BetRecordDTO();
		
		bet.setPlayType(pType);
		bet.setLn(ln);
		bet.setBetAmount(2.0);
		bet.setLotteryNumber("27");
		bet.setBetNumber("3232");
		bet.setBetCount(3);
		bet.setBetMultiple(3);
		bet.setBetTotal(234d);
		
		betRecordService.save(bet);
	}

}
