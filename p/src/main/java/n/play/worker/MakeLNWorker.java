package n.play.worker;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import n.entity.play.Game;
import n.entity.play.LN;
import n.entity.play.Game.Name;
import n.play.make.CreateLN;
import n.service.play.IGameService;
import n.service.play.ILNService;

@Component("makeLNWorker")
public class MakeLNWorker {

	private Logger log = LoggerFactory.getLogger(MakeLNWorker.class);

	@Resource
	private IGameService gameService;

	@Resource
	private ILNService lnService;

	// 0/30 * * * * ?
	@Scheduled(cron = "0 15 4 * * ?") // 三十秒执行一次 (corn 表达式)
	private void makeDaily() throws Exception {
		log.info("###before make ln >");
		List<Game> games = gameService.findAll();
		for (Game game : games) {
			Name name = game.getName();
			log.info("###before make ln > {}", name.toString());
			List<LN> issues = make(game);
			int done = lnService.save(issues);
			log.info("GAME " + name.toString() + " [size :{}] Make done:{}", issues.size(), done);
		}
	}

	private List<LN> make(Game game) {
		List<LN> issues = new ArrayList<LN>();
		switch (game.getName()) {
		case PCOO:
			issues = CreateLN.makePCOO(game);
			break;
		default:
			break;
		}
		return issues;
	}

	public void makeLN4Comm() throws Exception {
		makeDaily();
	}

}
