package com.nriet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

public class EncodingTransfer {

	/**
	 * 转换指定目录里的文件名符合给定正则表达式的文件编码
	 * 
	 * @param oldEncoding
	 * @param newEncoding
	 * @param dir
	 * @param regex
	 */
	public static void transfer(String oldEncoding, String newEncoding, File dir, final String regex) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if (dir.isDirectory() || name.matches(regex))
						return true;
					return false;
				}
			});
			for (File file : files) {
				if (file.isFile()) {
					transfer(oldEncoding, newEncoding, file);
				} else {
					transfer(oldEncoding, newEncoding, file, regex);
				}
			}
		}
	}

	/**
	 * 修改文件的编码
	 * 
	 * @param oldEncoding
	 *            之前的编码
	 * @param newEncoding
	 *            新编码
	 * @param file
	 *            要修改的文件
	 */
	public static void transfer(String oldEncoding, String newEncoding, File file) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(file);
			byte[] content = new byte[fis.available()];
			fis.read(content);
			String temp = new String(content, oldEncoding);
			fos = new FileOutputStream(file);
			fos.write(temp.getBytes(newEncoding));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (fos != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		File dir = new File("D:\\workspace\\sts3.6\\UtilSet\\src\\main\\java\\org\\tww\\convert\\DataBaseUtil.java");
		// transfer("GBK", "UTF-8", dir);
		transfer("UTF-8", "GBK", dir);

	}
}
