package com.nriet.util;

import java.util.ArrayList;
import java.util.List;

import com.nriet.util.SSH2Util;

import ch.ethz.ssh2.Connection;

public class MountManage {

	private static String[] commands = new String[] { "mount -t cifs -o uid=500 -o username=jsqx,passwd='jsqxjsqx' //10.124.33.201/benj/NAFP/ECMF /gpfs1/Incoming/mnt/datasource/NAFP/ECMF",
			"mount -t cifs -o uid=500 -o username=jsqx,passwd='jsqxjsqx' //10.124.32.201/benj/NAFP/T639 /gpfs1/Incoming/mnt/datasource/NAFP/T639",
			"mount -t cifs -o uid=500 -o username=jsqx,passwd='jsqxjsqx' //10.124.32.201/benj/NAFP/RJTD /gpfs1/Incoming/mnt/datasource/NAFP/RJTD",
			"mount -t cifs -o uid=500 -o username=jsqx,passwd='jsqxjsqx' //10.124.32.201/benj/RADA /gpfs1/Incoming/mnt/datasource/RADA",
			"mount -t cifs -o uid=500 -o username=jsqx,passwd='jsqxjsqx' //10.124.32.55/swanshare/LOCAL  /gpfs1/Incoming/mnt/swan",
			"mount -t cifs -o uid=500 -o username=rad,passwd='rad' //10.124.32.80/raining /gpfs1/Incoming/mnt/qpegs_3",
			"mount -t cifs -o uid=500 -o username=rad,passwd='rad' //10.124.32.62/product/data/综合/预警结果数据 /gpfs1/Incoming/mnt/flash",
			"mount -t cifs -o uid=500 -o username=WRF14,passwd='WRF14' //10.124.41.235/wrf/PWAFS-GRIB /gpfs1/Incoming/mnt/PWAFS",
			"mount -t cifs -o uid=500 -o username=jsqx,passwd='jsqxjsqx' //10.124.32.193/gfs /gpfs1/Incoming/mnt/datasource/NAFP/GFS",
			"mount -t cifs -o uid=500 -o username=jsyth,passwd='jsyth'  //10.124.31.200/jsyth/incoming/NAFP/ECMWF/  /gpfs1/Incoming/mnt/NAFP/ECMWF",
			"mount -t cifs -o uid=500 -o username=jsyth,passwd='jsyth'  //10.124.31.200/jsyth/incoming/NAFP/NCEP/  /gpfs1/Incoming/mnt/NAFP/NCEP",
			"mount -t cifs -o uid=500 -o username=owner,passwd='owner' //10.124.127.199/operational /gpfs1/Incoming/mnt/radiometer/ABNJ",
			"mount -t cifs -o uid=500 -o username=jsqx,passwd='jsqxjsqx' //10.124.32.55/swanshare/scpf  /gpfs1/Incoming/mnt/scpf",
			"mount -t cifs -o uid=500 -o username=WRF14,passwd='WRF14' //10.124.41.235/wrf/GRAPES-GRIB /gpfs1/Incoming/mnt/GRAPES",
			"mount -t cifs -o uid=500 -o username=st_swan,pass=st_swan-st_swan //10.124.31.98/swan/Anc/QPFblended  /gpfs1/Incoming/mnt/BJANC" };

	private static Connection connection = SSH2Util.getConnection("10.124.31.208", "root", "nriet123");

	public static List<String> getMountDevice() {

		String result = SSH2Util.execCommand(connection, "mount -t cifs");
		String[] arr = result.split("\r\n|\n");
		int orignLength = arr.length;
		List<String> list = new ArrayList<String>();
		for (String string : arr) {
			int index1 = string.indexOf("/gpfs1");
			if (index1 < 0)
				index1 = string.indexOf("/gpfs");
			int index2 = string.indexOf("type cifs (rw)");
			if (index1 > 0 && index2 > 0)
				list.add(string.substring(index1, index2).trim());
			else {
				System.out.println(string);
			}
		}
		int length = list.size();
		if (length != orignLength) {
			System.out.println("some line parse error!");
		}
		return list;
	}

	public static String check(String device) {
		String result = SSH2Util.execCommand(connection, "cd " + device, "ls -l");
		return result;
	}

	public static void main(String[] args) {
		List<String> mountDevice = getMountDevice();
		System.out.println("total mount devices: " + mountDevice.size());
		int num = 1;
		for (String string : mountDevice) {
			String result = check(string);
			if (result.trim().equals("total 0")) {
				boolean findCommand = false;
				// SSH2Util.execCommand(connection, "umount " + string);
				/*
				 * for (String command : commands) { if
				 * (command.trim().endsWith(string)) { String execResult =
				 * SSH2Util.execCommand(connection, command);
				 * System.out.println(execResult); findCommand = true; break; }
				 * }
				 */
				if (!findCommand) {
					System.out.println("cannot find mount command for : " + string);
				}
			} else {
				System.out.println(num++ + "、device " + string + " normal");
			}
		}
	}
}
