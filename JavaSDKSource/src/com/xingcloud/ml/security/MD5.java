package com.xingcloud.ml.security;

import java.security.MessageDigest;

/**
 * 
 * MD5的工具类
 * 
 */
public class MD5 {
	public static final String ALGORITHM = "MD5";

	/**
	 * 对传入的字节数组进行MD5编码
	 * 
	 * @param bytes
	 *            传入数组
	 * @return 编码后字符串
	 * @throws Exception
	 */
	public static String encode(byte[] bytes) throws Exception {
		MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
		digest.update(bytes);
		byte[] encodedBytes = digest.digest();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < encodedBytes.length; i++) {
			stringBuffer.append(Integer.toString(
					(encodedBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}

	/**
	 * 
	 * 对传入的内容按utf-8的格式进行MD5编码
	 * 
	 * @param source
	 *            传入source
	 * @return 编码后字符串
	 * @throws Exception
	 */
	public static String encode(String source) throws Exception {
		return MD5.encode(source.getBytes("UTF-8"));
	}

	/**
	 * 对传入的内容按指定格式进行MD5编码
	 * 
	 * @param source
	 *            传入source
	 * @param charset
	 *            格式
	 * @return 编码后的字符串
	 * @throws Exception
	 */
	public static String encode(String source, String charset) throws Exception {
		return MD5.encode(source.getBytes(charset));
	}
}