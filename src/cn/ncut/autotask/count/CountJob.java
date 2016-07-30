package cn.ncut.autotask.count;
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
		 
		  log.info("定时统计任务开始工作！");
		  try {
			  
	//		SlideCountTask.docounttask();
			
	//		CountScoreTask.is_scoreoutlife();
	//		 
	//	    CountScoreTask.is_codeoutlife();
			 
	//		CountScoreTask.count_nums();
			
			CountPicPrefer.countPicPrefer();//统计图片
			  
			/*ThemeTask.ExecuteThemeTask();*/
			CountAppPrefer.insertUsersApp();
			
			CountUseHabit.updateHabits();//统计每天每个时段的状况
			CountUseDayHabit.updateCount();
			
			  
			
		} catch (Exception e) {
		   log.error("定时统计任务发送异常!"+e);
		}
		  
		  log.info("定时统计任务结束工作！");
		  
		  
	}

}
