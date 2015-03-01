package org.tww.job;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 动态添加Job
 * 
 * @author Administrator
 * 
 */
public class Main {

	public static void main(String[] args) {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			// 全局参数,所有的Job共用的
			scheduler.getContext().put("name", "Tom");
			JobDetail detail = new JobDetail("myJobDetail", JobExample.class);
			// 局部参数,特定的Job使用的
			detail.getJobDataMap().put("name", "Mike");
			CronTrigger trigger = new CronTrigger("myCronTrigger", "myGroup",
					"0 0 0 * * * ?");
			scheduler.scheduleJob(detail, trigger);
			scheduler.start();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
