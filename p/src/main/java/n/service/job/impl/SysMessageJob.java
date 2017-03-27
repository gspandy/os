package n.service.job.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import n.service.sider.IHeroService;

@Component
public class SysMessageJob {

	@Resource
	private IHeroService heroService;

	private Logger log = LoggerFactory.getLogger(SysMessageJob.class);

	@Scheduled(cron = "0/30 * * * * ?") // 三十秒执行一次 (corn 表达式)
	public void exec() {
		//log.info("我是三十秒执行一次的任务触发器.");
		//Hero hero = heroService.find(1);
		//if (hero != null)
		//	log.info("### 这是自动任务 " + hero.getName());
	}

}
