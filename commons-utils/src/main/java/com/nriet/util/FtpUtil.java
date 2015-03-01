package com.nriet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

/**
 * 
 * @文件名: FtpUtil.java
 * @作者:tangweiwei
 * @日期:2014-9-29
 * @版本信息:
 * @版权:
 * @描述:FTP工具类
 */
public class FtpUtil {

	/**
	 * 上传单个文件
	 * 
	 * @param ip
	 * @param username
	 * @param pwd
	 * @param localPathname
	 *            本地文件完整路径
	 * @param remoteDir
	 *            服务器存储目录
	 * @param remoteFilename
	 *            服务器存储文件名
	 */
	public static void upload(String ip, String username, String pwd, String localPathname, String remoteDir,
			String remoteFilename) {
		FTPClient ftpClient = new FTPClient();
		FileInputStream fis = null;

		try {
			ftpClient.connect(ip);
			ftpClient.login(username, pwd);

			File srcFile = new File(localPathname);
			fis = new FileInputStream(srcFile);
			// 设置上传目录
			ftpClient.changeWorkingDirectory(remoteDir);
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding("GBK");
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.storeFile(remoteFilename, fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			IOUtils.closeQuietly(fis);
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	/**
	 * 列出指定目录下的所有文件名称,没有文件或者异常则返回空数组,进行此操作并不会改变当前工作目录
	 * 
	 * @param ip
	 * @param username
	 * @param pwd
	 * @param remoteDir
	 * @return
	 * @throws IOException
	 * @throws SocketException
	 */
	public static String[] list(String ip, String username, String pwd, String remoteDir) throws SocketException,
			IOException {
		FTPClient ftpClient = getFTPClient(ip, username, pwd);
		return ftpClient.listNames(remoteDir);
	}

	/**
	 * FTP下载单个文件
	 */
	public static void download(String ip, String username, String pwd, String remoteFileName, String localFileName) {
		FTPClient ftpClient = null;
		FileOutputStream fos = null;

		try {
			ftpClient = getFTPClient(ip, username, pwd);
			fos = new FileOutputStream(localFileName);
			ftpClient.setBufferSize(1024);
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.retrieveFile(remoteFileName, fos);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			IOUtils.closeQuietly(fos);
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	/**
	 * 下载远程目录下的所有文件
	 * 
	 * @param ip
	 * @param username
	 * @param pwd
	 * @param remoteDir
	 * @param localDir
	 * @return
	 */
	public static String[] downloadDir(String ip, String username, String pwd, String remoteDir, String localDir) {
		return downloadDir(ip, username, pwd, remoteDir, localDir, 0);
	}

	/**
	 * 下载远程目录下的指定数目的文件,若是文件已下载则不进行重复下载
	 * 
	 * @param ip
	 * @param username
	 * @param pwd
	 * @param remoteDir
	 * @param localDir
	 * @param num
	 *            要下载的文件数量
	 * @return
	 */
	public static String[] downloadDir(String ip, String username, String pwd, String remoteDir, String localDir,
			int num) {
		String[] filenames = null;
		FTPClient ftpClient = null;
		FileOutputStream fos = null;
		try {
			ftpClient = getFTPClient(ip, username, pwd);
			ftpClient.cwd(remoteDir);
			filenames = ftpClient.listNames();
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			int hasDownloaded = 0;
			for (String filename : filenames) {
				File localFile = new File(localDir, filename);
				if (localFile.exists())
					continue;
				fos = new FileOutputStream(localFile);
				if (ftpClient.retrieveFile(filename, fos))
					hasDownloaded++;
				if (num > 0 && hasDownloaded >= num)
					break;
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
				if (ftpClient != null)
					ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return filenames;
	}

	public static FTPClient getFTPClient(String ip, String username, String pwd) throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(ip);
		ftpClient.login(username, pwd);
		return ftpClient;
	}

	public static void main(String[] args) {
		String ip = "127.0.0.1";
		String pwd = "nriet123";
		String username = "root";

		String remoteDir = "D:/commandline";
		// FtpUtil.download(ip, username, pwd, "d:/commandline/jad.exe",
		// "d:/test/jad.exe");

		FtpUtil.downloadDir(ip, username, pwd, remoteDir, "d:\\test");

	}
}