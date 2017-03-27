package n.service.play;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.domain.Page;

import n.core.jutils.bean.BeanMapper;
import n.entity.play.Game;
import n.entity.play.LN;
import n.service.play.IGameService;
import n.service.play.ILNService;
import n.test.BaseTest;

public class LNServiceTest extends BaseTest {

	@Resource
	private IGameService gameService;

	@Resource
	private ILNService lnService;

	@Test
	public void findCruTest() {
		LN ln = lnService.findLastLottery(1);
		// lnService.findCurrent(1);
		System.out.println(BeanMapper.objToJson(ln));
	}

	@Test
	public void findPageTest() {
		Page<LN> page = lnService.findHistory(1, 0, 10);
		System.out.println(BeanMapper.objToJson(page.getContent()));
	}

	@Test
	public void findTest() {
		Game game = gameService.find(1);
		List<LN> result = lnService.findByScheduledTime(game, new Date());
		log.info("###size:{}" + result.size());
	}

	@Test
	public void countTest() {
		Game game = gameService.find(1);
		int result = lnService.countDaliy(game, new Date());
		System.out.println(result);
	}

	@Test
	public void findRecord() {

	}

}
