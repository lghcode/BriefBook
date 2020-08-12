package com.lghcode.briefbook.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author lgh
 */
public class MD5Utils {

	/**
	 * MD5加密算法
	 *
	 * @Author lghcode
	 * @param  strValue 要加密的字符串
	 * @return  String
	 * @Date 2020/8/10 10:30
	 */
	public static String getMD5Str(String strValue){
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Base64.encodeBase64String(md5 != null ? md5.digest(strValue.getBytes()) : new byte[0]);
	}

}
