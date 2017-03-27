package n.play.worker;

import java.util.Date;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import n.entity.play.Game;
import n.entity.play.LN;
import n.play.event.Event;
import n.play.lottery.LNFactory;

public class LTTimer {

	private static final Logger log = LoggerFactory.getLogger(LTTimer.class);
	final static Timer timer = new Timer();
	private Queue<LN> lnQueue = null;

	private Event event;

	public LTTimer(Queue<LN> lnQueue, Event event) {
		log.info("[timer定时:]开启期号调度.");
		this.lnQueue = lnQueue;
		this.event = event;
		startup();
	}

	private void startup() {
		LN ln = this.getCrawlerLN();
		String gname = ln.getGame().getName().toString();
		String issue = ln.getIssue();

		Date curDate = new Date();
		// 当前时间大于开盘时间 且当前时间小于封盘时间
		if (curDate.after(ln.getOpenTime()) && curDate.before(ln.getCloseTime())
				&& ln.getStatus().equals(LN.Status.OPEN)) {
			log.info("[timer定时:{}]期号:{},错过开盘时间,且未封盘并且是已经开盘的期号,先封盘再开下一期.", gname, issue);
			closeSchedule(ln);
			openSchedule(getCrawlerLN());
		}
		// 错过封盘时间
		else if (curDate.after(ln.getCloseTime()) && !ln.getStatus().equals(LN.Status.CLOSED)) {
			log.info("[timer定时:{}]期号:{},错过封盘时间,且状态不是已封盘的期号,先封盘再开下一期盘.", gname, issue);
			// 封盘
			closeSchedule(ln);
			openSchedule(getCrawlerLN());
		}
		// 错过开盘时间
		else if (curDate.after(ln.getOpenTime()) && ln.getStatus().equals(LN.Status.NOT_OPEN)) {
			log.info("[timer定时:{}]期号:{},错过开盘时间,且状态是未开盘的期号,直接开盘.", gname, issue);
			// 开盘
			openSchedule(ln);
		} else if (ln.getStatus().equals(LN.Status.NOT_OPEN)) {
			log.info("[timer定时:{}]期号:{},已按预定开盘,状态是未开盘的期号直接开盘.", gname, issue);
			// 按预定开盘
			openSchedule(ln);
		}
	}

	/**
	 * 开盘定时器
	 */
	private void openSchedule(final LN ln) {
		String gname = ln.getGame().getName().toString();
		String issue = ln.getIssue();
		if (ln != null) {
			timer.schedule(new TimerTask() {
				public void run() {
					openEvent();
					closeSchedule(ln);
					openSchedule(getCrawlerLN());
				}
				private void openEvent() {
					log.info("[开盘:{},{}]触发开盘.", gname, issue);
					ln.setStatus(LN.Status.OPEN);
					try {
						event.open(ln);
					} catch (Exception e) {
						log.info("[开盘:{},{}]开盘事件异常", gname, issue, e);
						e.printStackTrace();
					}
				}
			}, ln.getOpenTime());
			log.info("[预定开盘:{},{}]", gname, issue);
		}
	}

	/**
	 * 封盘定时器
	 */
	private void closeSchedule(final LN ln) {
		String gname = ln.getGame().getName().toString();
		String issue = ln.getIssue();
		timer.schedule(new TimerTask() {
			public void run() {
				closeEvent();
				crawlSchedule(ln);
			}
			private void closeEvent() {
				log.info("[封盘:{},{}]触发封盘.", gname, issue);
				ln.setStatus(LN.Status.CLOSED);
				try {
					event.closed(ln); 
				} catch (Exception e) {
					log.info("[封盘:{},{}]封盘事件异常.", gname, issue, e);
					e.printStackTrace();
				}
			}
		}, ln.getCloseTime());
		log.info("[封盘:{},{}]预定封盘.", gname, issue);
	}

	private void crawlSchedule(final LN ln) {
		String gname = ln.getGame().getName().toString();
		String issue = ln.getIssue();
		timer.schedule(new TimerTask() {
			public void run() {
				startCrawl();
			}
			private void startCrawl() {
				Game game = ln.getGame();
				game.getType();

				switch (game.getType()) {
				case OP: // 自开
					lotteryOP(ln, gname, issue, game);
				case CR: // 采集
					break;

				default:
					break;
				}
			}
			private void lotteryOP(final LN ln, String gname, String issue, Game game) {
				log.info("[采集事件:{},{}]开奖时间触发生成开奖号码事件", gname, issue);
				String num=LNFactory.getInstance().makeNum(game.getGmGroup());
				ln.setLotteryNumber(num);
				try {
					event.lottery(ln);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, ln.getScheduledTime());
		log.info("[预定开奖:{},{}]预定封盘.", gname, issue);
	}

	private LN getCrawlerLN() {
		return this.lnQueue.poll();
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
