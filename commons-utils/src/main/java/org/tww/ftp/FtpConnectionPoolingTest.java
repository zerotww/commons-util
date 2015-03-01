package org.tww.ftp;

import java.io.IOException;

public class FtpConnectionPoolingTest extends Thread {

	private static int n = 0;

	private static int m = 1;

	public void run() {
		try {
			/******************** 业务代码调用样例 *********************/
			System.out.println(m++);
			FtpClientProxy ftpClientProxy = new FtpClientProxy();
			String t = "连接" + ++n;
			System.out.println(ftpClientProxy.getOrderString());
			//System.out.println(t + "连接成功,端口号：" + ftpClientProxy.getLocalPort());
			sleep(1000);
			//System.out.println(t + "释放连接");
			ftpClientProxy.release();// 释放连接
			/***************************************************/
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		/************ 需要在服务器启动时进行加载 **************/
		FtpClientInfo ftpClientInfo = new FtpClientInfo();
		ftpClientInfo.setFtpIp("10.124.31.99");
		ftpClientInfo.setFtpPassword("jsmonitor");
		ftpClientInfo.setFtpPort(21);
		ftpClientInfo.setFtpUserName("jsmonitor");
		ftpClientInfo.setMaxConnects(20);
		FtpConnectionPooling.init(ftpClientInfo);
		/*******************************************/

		/*************************************************************/
		try {
			FtpClientProxy ftpClientProxy1 = new FtpClientProxy();
			System.out.println("本地端口" + ftpClientProxy1.getLocalPort());
			ftpClientProxy1.release();
			ftpClientProxy1.release();
			FtpClientProxy ftpClientProxy2 = new FtpClientProxy();
			System.out.println("本地端口" + ftpClientProxy2.getLocalPort());
			ftpClientProxy2.release();
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/************************* 并发测试 *******************************/
		for (int i = 1; i <= 40; i++) {
			FtpConnectionPoolingTest test = new FtpConnectionPoolingTest();
			test.start();
		}
	}
}
