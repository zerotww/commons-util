package com.nriet.util;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * 
 * @ClassName: FileUtil
 * @Description: 文件的相关读取类
 * @author liqingbo
 * @date 2014年9月22日 上午10:58:46
 * 
 */
public class FileUtil {
	protected FileUtil() {
	}

	/**
	 * 以字节形式读取二进制文件或文本文件
	 * 
	 * @param path
	 *            文件绝对路径
	 * @return
	 * @throws IOException
	 */
	public static byte[] readAsByte(String path) throws IOException {
		byte[] buf;
		buf = localReadAsByte(path);
		return buf;
	}

	/**
	 * 以指定字符编码读取文本文件
	 * 
	 * @param path
	 *            文件绝对路径
	 * @param encoding
	 *            字符编码
	 * @return
	 * @throws IOException
	 */
	public static String read(String path, String encoding) throws IOException {
		String content = "";
		content = localRead(path, encoding);
		return content;
	}

	/**
	 * 读取文本文件
	 * 
	 * @param path
	 *            文件绝对路径
	 * @return
	 * @throws IOException
	 */
	public static String read(String path) throws IOException {
		String content = "";
		String encoding = EncodingDetectUtil.getJavaEncode(path);
		content = read(path, encoding);
		return content;
	}

	/**
	 * 以指定字符编码读取文本文件
	 * 
	 * @param file
	 *            文件
	 * @param encoding
	 *            字符编码
	 * @return
	 * @throws IOException
	 */
	public static String read(File file, String encoding) throws IOException {
		String content = "";
		content = localRead(file, encoding);
		return content;
	}

	/**
	 * 读取本地文本文件
	 * 
	 * @param file
	 *            文件
	 * @return
	 * @throws IOException
	 */
	public static String read(File file) throws IOException {
		String content = "";
		String encoding = EncodingDetectUtil.getJavaEncode(file.getAbsolutePath());
		content = localRead(file, encoding);
		return content;
	}

	/**
	 * 读取本地文件内容(字节形式)
	 * 
	 * @param path
	 *            文件路径
	 * @return 文件内容
	 * @throws IOException
	 */
	private static byte[] localReadAsByte(String path) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
		int bufferSize = bis.available();
		byte[] buf = new byte[bufferSize];
		bis.read(buf);
		bis.close();
		return buf;
	}

	/**
	 * 读取本地文件内容(字节形式)
	 * 
	 * @param file
	 *            文件路径
	 * @return 文件内容
	 * @throws IOException
	 */
	private static byte[] localReadAsByte(File file) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		int bufferSize = bis.available();
		byte[] buf = new byte[bufferSize];
		bis.read(buf);
		bis.close();
		return buf;
	}

	/**
	 * 以指定字符编码读取本地文本文件
	 * 
	 * @param path
	 *            文件路径
	 * @param encoding
	 *            字符编码
	 * @return 文件内容
	 * @throws IOException
	 */
	public static String localRead(String path, String encoding) throws IOException {
		byte[] temp = localReadAsByte(path);
		String str = new String(temp, encoding);
		return str;
		// return new String(localReadAsByte(path),encoding);
	}

	/**
	 * 读取本地文件内容
	 * 
	 * @param file
	 *            文件
	 * @param encoding
	 *            字符编码
	 * @return 文件内容
	 * @throws IOException
	 */
	public static String localRead(File file, String encoding) throws IOException {
		byte[] temp = localReadAsByte(file);
		String str = new String(temp, encoding);
		return str;
	}

	/**
	 * 按照little-endian 字节顺序写入浮点数
	 * 
	 * @param dos
	 * @param value
	 */
	public static void writeFloatByLE(DataOutputStream dos, float value) throws IOException {
		ByteBuffer buff = ByteBuffer.allocate(4);
		buff.order(ByteOrder.LITTLE_ENDIAN);
		buff.putFloat(value);
		dos.write(buff.array());
	}

	/**
	 * 按照little-endian 字节顺序写入短整数
	 * 
	 * @param dos
	 * @param value
	 */
	public static void writeShortByLE(DataOutputStream dos, short value) throws IOException {
		ByteBuffer buff = ByteBuffer.allocate(2);
		buff.order(ByteOrder.LITTLE_ENDIAN);
		buff.putShort(value);
		dos.write(buff.array());
	}

	/**
	 * 按照little-endian 字节顺序写入无符号短整数
	 * 
	 * @param dos
	 * @param value
	 */
	public static void writeUnsignedShortByLE(DataOutputStream dos, int value) throws IOException {
		ByteBuffer buff = ByteBuffer.allocate(4);
		buff.order(ByteOrder.LITTLE_ENDIAN);
		buff.putInt(value);
		dos.write(Arrays.copyOfRange(buff.array(), 0, 2));
	}

	/**
	 * 按照little-endian 字节顺序写入整数
	 * 
	 * @param dos
	 * @param value
	 */
	public static void writeIntByLE(DataOutputStream dos, int value) throws IOException {
		ByteBuffer buff = ByteBuffer.allocate(4);
		buff.order(ByteOrder.LITTLE_ENDIAN);
		buff.putInt(value);
		dos.write(buff.array());
	}

	/**
	 * 剪切文件,若目标文件存在则不做任何处理
	 * 
	 * @param fromPath
	 * @param toPath
	 */
	public static void move(String fromPath, String toPath) {
		move(new File(fromPath), new File(toPath));
	}

	/**
	 * 剪切文件,若目标文件存在则删掉目标文件后再剪切
	 * 
	 * @param fromFile
	 * @param toFile
	 */
	public static boolean move(File fromFile, File toFile) {
		if (!fromFile.exists())
			return false;
		if (!toFile.getParentFile().exists())
			toFile.getParentFile().mkdirs();
		if (toFile.exists())
			toFile.delete();
		fromFile.renameTo(toFile);
		if (toFile.exists() && toFile.length() > 0) {
			fromFile.delete();
			return true;
		}
		return false;
	}

	/**
	 * 剪切文件
	 * 
	 * @param fromPath
	 * @param toPath
	 */
	public static void moveFile(String fromPath, String toPath) {
		File fromFile = new File(fromPath);
		File toFile = new File(toPath);
		File topathDir = new File(toFile.getParent());
		if (!topathDir.exists()) {
			topathDir.mkdirs();
		}
		// 存在同名文件
		if (toFile.exists()) {
			// 截断路径
			int last = toPath.lastIndexOf(File.separator) > toPath.lastIndexOf("\\") ? toPath
					.lastIndexOf(File.separator) : toPath.lastIndexOf("\\");
			String path = toPath.substring(0, last);
			// 备份文件名
			File fileBak = new File(path + File.separator + toFile.getName() + ".bak");
			// 备份文件同名
			if (!toFile.renameTo(fileBak)) {
				fileBak.delete();// 删除同名备份文件
			}
			// 备份文件
			toFile.renameTo(fileBak);
		}
		// 剪切
		fromFile.renameTo(toFile);
	}
}
