package org.tww.ftp;

import java.util.Comparator;

public class FtpClientComparator implements Comparator<FtpClient> {

	@Override
	public int compare(FtpClient arg0, FtpClient arg1) {
		return arg0.order - arg1.order;
	}

}
