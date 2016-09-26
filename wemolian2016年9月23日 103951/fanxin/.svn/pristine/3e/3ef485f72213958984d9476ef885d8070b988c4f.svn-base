package com.wemolian.app.utils;

import it.sauronsoftware.base64.Base64;

import java.util.Random;




/**
 * base64加密算法工具类
 * @author zhangyun
 *
 */
public class Base64Util {

	/**
	 * 16位获取随机数
	 */
	public static String getRan(){
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		Random ran = new Random(16);
		StringBuffer sb = new StringBuffer();  
	    for (int i = 0; i < 16; i++) {  
	        int number = ran.nextInt(str.length());  
	        sb.append(str.charAt(number));  
	    }  
	    System.out.println("随机字符为:" + sb.toString());
	    return sb.toString();
	}
	
	/**
	 * 将传入的字符串加密
	 * @param str 需要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String base64Encode(String str){
		String btStr =Base64.encode(str);
//		Base64.Encoder en = Base64.getEncoder();
//		byte[] btStr = en.encode(str.getBytes());
		String key = getRan();
		return (key + btStr);
	}
	
	/**
	 * 将传入的字符串解密
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static String base64Decode(String str){
//		Base64.Decoder de = Base64.getDecoder();
//		if(str == null){
//			return null;
//		}
//		String deStr = str.substring(15, str.length());
//		byte[] btStr = de.decode(deStr);
//		if(btStr == null ){
//			return null;
//		}
//		return new String(btStr);
		return null;
	}
}
