package n.wizard.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

/**
 * JOB计划
 * @author  jtwise
 * @date 2016年7月20日 上午10:29:54
 * @verion 1.0
 */
public class JobScheduler {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception{
	 
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();     //声明标准JOB调度器
		
		TriggerKey triggerkey=new TriggerKey("MAKE_LN", "SYS");
		
		Class<Job> clas=(Class<Job>) Class.forName("com.hitler.wizard.job.MakeLNJob");
		// 具体任务
		JobDetail job = JobBuilder.newJob(clas).withIdentity("make_ln", "SYS").build();
		
		// 触发时间点
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ? *");
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerkey)
		        .withSchedule(cronScheduleBuilder).build();

		// 交由Scheduler安排触发
		scheduler.scheduleJob(job, trigger);
	
	}

}
