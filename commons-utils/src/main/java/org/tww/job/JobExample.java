package org.tww.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

public class JobExample implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		try {
			String globalName = (String) context.getScheduler().getContext()
					.get("name");
			System.out.println(globalName);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		String localName = (String) context.getJobDetail().getJobDataMap()
				.get("name");
		System.out.println(localName);

	}
}
