package cn.ncut.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.rmi.runtime.Log;

public class AESUtil {

	
	/** 
     * 生成密钥 
     * 自动生成AES128位密钥 
     * 传入保存密钥文件路径 
     * filePath 表示文件存储路径加文件名；例如d:\aes.txt 
	 * @return 
     * @throws NoSuchAlgorithmException  
     * @throws IOException  
     */  
    public static String getAutoCreateAESKey(){  
        KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance("AES");
			 kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256  
		        SecretKey sk = kg.generateKey();
		        return parseByte2HexStr(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}  
    }
    
    
    
    //将二进制数据转换为16进制
    public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    }
    
    
    
    /** 
     * 将byte[]转为各种进制的字符串 
     * @param bytes byte[] 
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
     * @return 转换后的字符串 
     */  
    public static String binary(byte[] bytes, int radix){  
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
    }  
      
    
    
    /** 
     * base 64 encode 
     * @param bytes 待编码的byte[] 
     * @return 编码后的base 64 code 
     */  
    public static String base64Encode(byte[] bytes){  
        return new BASE64Encoder().encode(bytes);  
    }  
      
    
    
    /** 
     * base 64 decode 
     * @param base64Code 待解码的base 64 code 
     * @return 解码后的byte[] 
     * @throws Exception 
     */  
    public static byte[] base64Decode(String base64Code) throws Exception{
    	if(null!=base64Code){
    		return new BASE64Decoder().decodeBuffer(base64Code);
    	}
        return null;  
    }  
      
    /** 
     * 获取byte[]的md5值 
     * @param bytes byte[] 
     * @return md5 
     * @throws Exception 
     */  
    public static byte[] md5(byte[] bytes) throws Exception {  
        MessageDigest md = MessageDigest.getInstance("MD5");  
        md.update(bytes);  
          
        return md.digest();  
    }  
      
    /** 
     * 获取字符串md5值 
     * @param msg  
     * @return md5 
     * @throws Exception 
     */  
    public static byte[] md5(String msg) throws Exception {
    	if(null!=msg){
    		return md5(msg.getBytes());
    	}
        return null;  
    }  
      
    /** 
     * 结合base64实现md5加密 
     * @param msg 待加密字符串 
     * @return 获取md5后转为base64 
     * @throws Exception 
     */  
    public static String md5Encrypt(String msg) throws Exception{  
        return base64Encode(md5(msg));  
    }  
    
    
      
    /** 
     * AES加密 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的byte[] 
     * @throws Exception 
     */  
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(encryptKey.getBytes()));  
     
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
          
        return cipher.doFinal(content.getBytes("utf-8"));  
    }  
      
    /** 
     * AES加密为base 64 code 
     * @param content 待加密的内容 
     * @param encryptKey 加密密钥 
     * @return 加密后的base 64 code 
     * @throws Exception 
     */  
    public static String aesEncrypt(String content, String encryptKey) throws Exception {  
        return base64Encode(aesEncryptToBytes(content, encryptKey));  
    }  
      
    /** 
     * AES解密 
     * @param encryptBytes 待解密的byte[] 
     * @param decryptKey 解密密钥 
     * @return 解密后的String 
     * @throws Exception 
     */  
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(decryptKey.getBytes()));  
    
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
          
        return new String(decryptBytes);  
    }  
      
    /** 
     * 将base 64 code AES解密 
     * @param encryptStr 待解密的base 64 code 
     * @param decryptKey 解密密钥 
     * @return 解密后的string 
     * @throws Exception 
     */  
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {  
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);  
    }  
    
    public static void main(String[] args) throws Exception {
		String a = getAutoCreateAESKey();
		System.out.println(a);
		 String content = "15618874263";  
	        System.out.println("加密前：" + content);  
	  
	        String encrypt = aesEncrypt(content, "8388C6BD21BAD51A200C2ED29D1D4FA3");  
	        System.out.println("加密后：" + encrypt);  
	          
	        String decrypt = aesDecrypt(encrypt, "8388C6BD21BAD51A200C2ED29D1D4FA3");  
	        System.out.println("解密后：" + decrypt); 
    	
	}
}
