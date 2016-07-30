package cn.ncut.autotask.push;

import lombok.extern.slf4j.Slf4j;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author wzq
 *
 *version 1.0 2016-4-19 上午11:31:22
 */
@Slf4j
public class AutoPushByLocationJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		log.info("根据地理位置定时推送任务开始");
		
		Example.mytask();
		
		log.info("根据地理位置定时推送任务j结束");
		
		
	}

}
