package cn.ncut.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Base64;

/**
 *@author chenbin
 *@time  2016年4月29日下午5:02:15
 *@version  1.0.0 
 */
public class TestUtil {

	public static final String VIPARA = "0102030405060708";  
	public static final String bm = "UTF-8";  
	
	
	
	
	
	public static String encrypt(String dataPassword, String cleartext)  
	        throws Exception {  
	    IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());  
	    SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");//省去了在ECC_SHA中的getRawKey(byte[] seed)方法，直接第一个参数使用dataPassword.getBytes()  
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	    cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);  
	    byte[] encryptedData = cipher.doFinal(cleartext.getBytes(bm));  
	  
	    return encryptBASE64(encryptedData);  
	}  
	  
	public static String decrypt(String dataPassword, String encrypted)  
	        throws Exception {  
	    byte[] byteMi = Base64.decode(encrypted);  
	    IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());  
	    SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");  
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	    cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);  
	    byte[] decryptedData = cipher.doFinal(byteMi);  
	  
	    return new String(decryptedData,bm);  
	}  
	
	
	/** 
	 * BASE64解密 ,
	 *  
	 * @param key 
	 * @return 
	 * @throws Exception 
	 */  
	public static byte[] decryptBASE64(String key) throws Exception {  
		//base64String - String containing Base64 data
	    return Base64.decode(key); // 将包含Base64数据的字符串解密并返回byte[]类型数据
		
	}  
	  
	/** 
	 * BASE64加密 
	 *  
	 * @param key 
	 * @return 
	 * @throws Exception 
	 */  
	public static String encryptBASE64(byte[] key) throws Exception {  
	    return new String (Base64.encode(key));  //将byte[]类型数据使用Base64编码并转换为字符串
		
	} 
	
	
	
}


