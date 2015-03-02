package org.tww.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.nriet.util.DateTimeUtil;

public class JobExample implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println(Thread.currentThread().getName() + "===" + DateTimeUtil.toString(new Date(), "yyyy MM dd HH mm ss SSS"));
	}

}
