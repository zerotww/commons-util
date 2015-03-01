package org.tww.quartz;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class Main {

	public static void main(String[] args) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			JobDetail jobDetail = new JobDetail("JobDetailName",
					JobExample.class);
			CronTrigger cronTrigger = new CronTrigger(
					"GronTriggerName",
					"GroupName",
					"0 0,3-5,8-10,13-15,18-20,23-25,28-30,33-35,38-40,43-45,48-50,53-55,58,59 * * * ?");
			scheduler.scheduleJob(jobDetail, cronTrigger);
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
