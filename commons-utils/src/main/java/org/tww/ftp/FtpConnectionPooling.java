package org.tww.ftp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * <br>
 * Title:FTP连接池 <br>
 * Description:FTP连接池及FTP连接池相应的操作 <br>
 * Date:2013-7-4
 */
public abstract class FtpConnectionPooling {
	private static BlockingQueue<FtpClient> fqueue;

	private static FtpClientInfo ftpClientInfo;

	/**
	 * <br>
	 * Description:初始化连接池 <br>
	 * Author:张智研 <br>
	 * Date:2013-7-4
	 * 
	 * @param ftpClientInfo
	 */
	public static void init(FtpClientInfo info) {
		ftpClientInfo = info;
		fqueue = new PriorityBlockingQueue<FtpClient>(ftpClientInfo.getMaxConnects(), new FtpClientComparator());// 初始化队列容量
		FtpClient ftpClient;
		for (int i = 0; i < ftpClientInfo.getMaxConnects(); i++) {
			ftpClient = new FtpClient();
			ftpClient.order = i;
			fqueue.add(ftpClient);
		}
	}

	public static FtpClientInfo getFtpClientInfo() {
		return ftpClientInfo;
	}

	/**
	 * <br>
	 * Description:向线程池中添加FTPClient <br>
	 * Author:张智研 <br>
	 * Date:2013-7-4
	 * 
	 * @param ftpClient
	 */
	public static boolean add(FtpClient ftpClient) {
		boolean b = fqueue.contains(ftpClient);
		if (!b)
			return fqueue.add(ftpClient);
		return true;
	}

	/**
	 * <br>
	 * Description:获取FTPClient，如果线程池为空，则等待到FtpClientInfo中所设置的超时时间 <br>
	 * Author:张智研 <br>
	 * Date:2013-7-4
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public static FtpClient poll() throws InterruptedException {
		return fqueue.poll(ftpClientInfo.getTimeout(), ftpClientInfo.getTimeUnit());
	}

	/**
	 * <br>
	 * Description:获取FTPClient，如果线程池为空，则一直等待。 <br>
	 * Author:张智研 <br>
	 * Date:2013-7-4
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public static FtpClient take() throws InterruptedException {
		return fqueue.take();
	}
}
