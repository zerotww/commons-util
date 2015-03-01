package org.tww.encryption;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * DES加密工具,DES属于对称加密
 * 
 * @author Administrator
 * 
 */
public class DESUtil {
	/**
	 * DES加密解密所用到的密钥
	 */
	private static Key key;

	private static String KEY_STR = "myKey";

	static {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("DES");
			generator.init(new SecureRandom(KEY_STR.getBytes()));
			key = generator.generateKey();
			generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取加密后的字符
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncryptString(String str) {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		try {
			byte[] strBytes = str.getBytes("UTF-8");
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptStrBytes = cipher.doFinal(strBytes);
			return base64Encoder.encode(encryptStrBytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取解密后的字符
	 * 
	 * @param str
	 * @return
	 */
	public static String getDecryptString(String str) {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		try {
			byte[] strBytes = base64Decoder.decodeBuffer(str);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decryptStrBytes = cipher.doFinal(strBytes);
			return new String(decryptStrBytes, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		System.out.println("请输入要加密的字符");
		Scanner scanner = new Scanner(System.in);
		System.out.println(getEncryptString(scanner.next()));
		System.out.println("请输入要解密的字符串");
		System.out.println(getDecryptString(scanner.next()));
		scanner.close();
	}
}
