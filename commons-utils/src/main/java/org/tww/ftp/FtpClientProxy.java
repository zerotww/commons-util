package org.tww.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

/**
 * <br>
 * Title:FTPClient代理类 <br>
 * Description:负责FTPClient功能的代理 <br>
 * Author:张智研(zhangzhiyan@neusoft.com) <br>
 * Date:2013-7-4
 */
public class FtpClientProxy {
	private FtpClient ftpClient;

	public FtpClientProxy() throws InterruptedException, SocketException, IOException {
		ftpClient = FtpConnectionPooling.poll();
		if (!ftpClient.isConnected()) {
			FtpClientInfo info = FtpConnectionPooling.getFtpClientInfo();// 获取ftpClient信息
			ftpClient.connect(info.getFtpIp(), info.getFtpPort());// 连接
			ftpClient.login(info.getFtpUserName(), info.getFtpPassword());// 登陆
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置为二进制
		}
	}

	public String getOrderString() {
		return "第" + ftpClient.order + "个FTPClient";
	}

	/**
	 * <br>
	 * Description:释放ftpClient <br>
	 * Author:张智研 <br>
	 * Date:2013-7-4
	 * 
	 * @return
	 */
	public boolean release() {
		if (ftpClient == null)
			return true;
		boolean b = FtpConnectionPooling.add(ftpClient);
		if (b)
			ftpClient = null;
		return b;
	}

	/**
	 * <br>
	 * Description:下载文件 <br>
	 * Author:张智研(zhangzhiyan@neusoft.com) <br>
	 * Date:2013-7-4
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public InputStream retrieveFileStream(String remote) throws IOException {
		return ftpClient.retrieveFileStream(remote);
	}

	/**
	 * <br>
	 * Description:上传文件 <br>
	 * Author:张智研(zhangzhiyan@neusoft.com) <br>
	 * Date:2013-7-4
	 * 
	 * @param remote
	 * @param local
	 * @return
	 * @throws IOException
	 */
	public boolean storeFile(String remote, InputStream local) throws IOException {
		return ftpClient.storeFile(remote, local);
	}

	/**
	 * <br>
	 * Description:获取本地端口 <br>
	 * Author:张智研(zhangzhiyan@neusoft.com) <br>
	 * Date:2013-7-4
	 * 
	 * @return
	 */
	public int getLocalPort() {
		return ftpClient.getLocalPort();
	}
}
