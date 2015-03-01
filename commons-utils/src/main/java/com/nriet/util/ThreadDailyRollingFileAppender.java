package com.nriet.util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 用于一个线程写一个日志，通常一个appender对应一个日志文件，<br />
 * 这里将每个线程的DailyRollingFileAppender封装到一个ThreadDailyRollingFileAppender里了,
 * 所以每个线程的文件名必须属于同一个文件名模板。<br />
 * 配置同DailyRollingFileAppender一致，只是在文件名属性里可以使用%t代表线程名称，<br />
 * 如果不使用%t则就没有使用这个类的意义了
 * 
 * 
 * 
 * @author tangweiwei
 *
 */
public class ThreadDailyRollingFileAppender extends FileAppender {

	private Map<String, DailyRollingFileAppender> appenders = new ConcurrentHashMap<String, DailyRollingFileAppender>();

	private String datePattern = "'.'yyyy-MM-dd";

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	@Override
	public synchronized void setFile(String fileName, boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		// TODO Auto-generated method stub
		// super.setFile(fileName, append, bufferedIO, bufferSize);
	}

	@Override
	public void doAppend(LoggingEvent event) {

		System.out.println(appenders.size());
		String threadName = Thread.currentThread().getName();
		DailyRollingFileAppender appender = null;
		if (!appenders.containsKey(threadName)) {
			try {
				appender = new DailyRollingFileAppender(layout, getFile().replaceAll("%t", threadName), datePattern);
				appenders.put(threadName, appender);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			appender = appenders.get(threadName);
		appender.doAppend(event);
	}
}
