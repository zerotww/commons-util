package org.tww.daemon;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

public class DaemonTemplate implements Daemon {

	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
		System.out.println("init.............");
	}

	@Override
	public void start() throws Exception {
		System.out.println("start");
	}

	@Override
	public void stop() throws Exception {
		System.out.print("stop");
	}

	@Override
	public void destroy() {
		System.out.println("destroy.....");
	}

}
