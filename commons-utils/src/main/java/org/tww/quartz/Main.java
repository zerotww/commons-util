package org.tww.quartz;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 手动生成任务调度
 * 
 * @author Administrator
 *
 */
public class Main {

	public static void main(String[] args) {
		method2();
	}

	/**
	 * 通过默认的scheduler创建任务调度
	 */
	public static void method1() {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			JobDetail jobDetail = new JobDetail("JobDetailName", JobExample.class);
			CronTrigger cronTrigger = new CronTrigger("GronTriggerName", "GroupName", "0 0,3-5,8-10,13-15,18-20,23-25,28-30,33-35,38-40,43-45,48-50,53-55,58,59 * * * ?");
			scheduler.scheduleJob(jobDetail, cronTrigger);
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建两个scheduler,两个scheduler的名称不一样所以不能将{@link Main#method1()}
	 * 中的方法调用两次,可以通过构造Properties赋予不同的实例名传递给工厂类，或者配置properties属性文件
	 */
	public static void method2() {
		try {
			SchedulerFactory factory = new StdSchedulerFactory("org/tww/quartz/quartz1.properties");
			SchedulerFactory factory2 = new StdSchedulerFactory("org/tww/quartz/quartz2.properties");
			Scheduler scheduler = factory.getScheduler();
			Scheduler scheduler2 = factory2.getScheduler();
			System.out.println(scheduler);
			System.out.println(scheduler2);
			JobDetail jobDetail = new JobDetail("JobDetailName", JobExample.class);
			CronTrigger cronTrigger = new CronTrigger("GronTriggerName", "GroupName", "0/10 * * * * ?");
			scheduler.scheduleJob(jobDetail, cronTrigger);
			scheduler2.scheduleJob(jobDetail, cronTrigger);
			scheduler.start();
			scheduler2.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
