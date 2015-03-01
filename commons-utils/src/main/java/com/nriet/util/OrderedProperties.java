package com.nriet.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：修改属性后按原顺序就行存储,保留注释
 * 
 */
public class OrderedProperties {

	private String endSuffix = "\r\n";

	private List<String> fileContent = new ArrayList<String>();

	/**
	 * 取得endSuffix方法
	 * 
	 * @return endSuffix
	 */
	public String getEndSuffix() {
		return endSuffix;
	}

	/**
	 * 设置endSuffix方法
	 * 
	 * @param endSuffix
	 *            设置到endSuffix
	 */
	public void setEndSuffix(String endSuffix) {
		this.endSuffix = endSuffix;
	}

	public void load(String filePath) {
		try {
			this.load(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void load(InputStream is) {
		this.load(new InputStreamReader(is));
	}

	public void load(Reader reader) {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(reader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				fileContent.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setProperty(String key, String value) {
		for (int i = 0; i < fileContent.size(); i++) {
			if (fileContent.get(i).startsWith(key + "=")) {
				fileContent.set(i, key + "=" + value);
			}
		}
	}

	public void setComment(String key, String comment) {
		for (int i = 0; i < fileContent.size(); i++) {
			if (fileContent.get(i).startsWith(key + "=")) {
				fileContent.add(i, "#" + comment);
			}
		}
	}

	public String getComment(String key) {
		for (int i = 0; i < fileContent.size(); i++) {
			if (fileContent.get(i).startsWith(key + "=")) {
				if (i >= 1 && fileContent.get(i - 1).startsWith("#")) {
					return fileContent.get(i - 1).substring(1);
				}
			}
		}
		return null;
	}

	public String getProperty(String key) {
		for (int i = 0; i < fileContent.size(); i++) {
			if (fileContent.get(i).startsWith(key + "=")) {
				String line = fileContent.get(i);
				return line.substring(line.indexOf("=") + 1);
			}
		}
		return null;
	}

	public void store(String filePath) {
		try {
			store(new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void store(OutputStream os) {
		store(new OutputStreamWriter(os));
	}

	public void store(Writer writer) {
		PrintWriter printWriter = null;
		printWriter = new PrintWriter(writer);
		for (String s : fileContent) {
			printWriter.print(s + this.getEndSuffix());
		}
		printWriter.flush();
		printWriter.close();
	}
}
