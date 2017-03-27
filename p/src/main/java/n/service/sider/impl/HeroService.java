package n.service.sider.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import n.core.repository.support.GenericRepository;
import n.core.service.support.GenericService;
import n.entity.sider.Hero;
import n.play.worker.JobType;
import n.play.worker.LTWorker;
import n.play.worker.MakeLNWorker;
import n.repository.sider.IHeroRepository;
import n.service.sider.IHeroService;

/**
 * 此类用来检视系统是否正常
 * 
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
@Service
public class HeroService extends GenericService<Hero, Integer> implements IHeroService {

	public HeroService() {
		super(Hero.class);
	}

	@Resource
	private IHeroRepository repository;

	@Resource
	private MakeLNWorker makeLNWorker;

	@Resource
	private LTWorker ltWorker;

	@Override
	protected GenericRepository<Hero, Integer> getRepository() {
		return repository;
	}

	@Override
	public void comm(JobType jobType) throws Exception {
		switch (jobType) {
		case MAKE_LN:
			makeLNWorker.makeLN4Comm();
			break;
		case LT:
			ltWorker.LT4Comm();
			break;
		case INIT_DATA:
			break;
		case PRIZE:
			break;
		default:
			break;
		}
	}

}
