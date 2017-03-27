package n.wizard.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成期号JOB
 * @author  jtwise
 * @date 2016年7月20日 上午10:37:02
 * @verion 1.0
 */
public class MakeLNJob implements Job{
	
	private Logger log=LoggerFactory.getLogger(MakeLNJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("###开始执行期号生成:"+context.getTrigger().getStartTime());
	}
	
}
