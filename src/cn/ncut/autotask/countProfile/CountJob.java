package cn.ncut.autotask.countProfile;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author wzq
 *
 *version 1.0 2015-1-18 下午5:54:23
 */
@Slf4j
public class CountJob implements Job{
  @Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		 
		  log.info("定时统计任务开始工作2！");
			  
			CountPicPrefer.countPicPrefer();//统计图片
//			  
			CountAppPrefer.insertUsersApp();
			
			CountUseHabit.updateHabits();//统计每天每个时段的状况
			CountUseDayHabit.updateCount();
			CountPicClassiy.countPicClassiy();
	//		CountLocPrefer.countMap();
		  
		  log.info("定时统计任务结束工作！");
		  
		  
	}

}
