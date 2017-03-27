package n.service.sider;

import n.core.service.support.IGenericService;
import n.entity.sider.Hero;
import n.play.worker.JobType;

/**
 * 此类用来检视系统是否正常
 * 
 * @author jt_wangshuiping
 * @date 2016-10-21
 * @version 3.0
 */
public interface IHeroService extends IGenericService<Hero, Integer> {
	public void comm(JobType jobType) throws Exception;
}
