package n.play.worker;

import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import n.entity.play.Game;
import n.entity.play.LN;
import n.play.event.Event;
import n.service.play.IGameService;
import n.service.play.ILNService;

@Component("ltWorker")
public class LTWorker {

	final static Timer timer = new Timer();

	@Resource
	private IGameService gameService;
	@Resource
	private ILNService lnService;
	@Resource
	private Event ltEvent;

	@Scheduled(cron = "0 30 4 * * ?")
	@PostConstruct
	private void LTScheduler() {
		List<Game> games = gameService.findAll();
		for (Game game : games) {
			Date crd = new Date();
			List<LN> no_open_lns = lnService.findByScheduledTime(game, crd);
			if (!no_open_lns.isEmpty()) {
				Queue<LN> queue = new LinkedBlockingQueue<LN>();
				queue.addAll(no_open_lns);
				new LTTimer(queue, ltEvent);
			}
		}

	}

	public void LT4Comm() {
		LTScheduler(); //执行任务
	}

}
