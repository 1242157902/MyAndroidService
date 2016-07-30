package cn.ncut.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import cn.ncut.devicedao.PhoneDao;

@Slf4j
public class ECC_SHA {
	
	public static final String ALGORITHM = "EC"; 
	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
    private static final String PUBLIC_KEY = "ECCPublicKey";  
    private static final String PRIVATE_KEY = "ECCPrivateKey";
    public static final String SERVER_PRIVATE_KEY="MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCD3OdXW5BN/Lh8f3Uk7VczyVlVaBuclaIqGQC5DMK/VDQ==";//长度200,使用了base64处理后的结果
    public static  String SERVER_PUBLIC_KEY="MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEQr6OMvnz/0mHgjfQuxfOmfV9o1ZcpghSRDt/f6vF9LvxZem3QZLQN+KngBpf1W0Vsba/3ZjUC7KrFfzynv+VfQ==";
    
    //Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式。
    //常见于邮件、http加密，截取http信息，你就会发现登录操作的用户名、密码字段通过BASE64加密的。
    
    /** 
     * 解密<br> 
     * 用私钥解密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decrypt(byte[] data, String privateKey) throws Exception { 
    	
    	Security.addProvider(new BouncyCastleProvider());
    	
    	
        //对密钥解密  
        byte[] keyBytes = decryptBASE64(privateKey);  
    	
        //取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM) ;  
  
        ECPrivateKey priKey = (ECPrivateKey) keyFactory  
                .generatePrivate(pkcs8KeySpec);  
  
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(priKey.getS(),  
                priKey.getParams());  
  
        // 对数据解密  
        //Cipher cipher = Cipher.getInstance("EC/CBC/NoPadding","SunEC");
        //Cipher cipher = Cipher.getInstance("ECIESwithDESede/NONE/PKCS7Padding", "BC");//算法--feedback mode--padding scheme
        Cipher cipher = Cipher.getInstance("ECIES/NONE/NoPadding", "BC");
       
        //cipher.init(Cipher.DECRYPT_MODE, priKey, ecPrivateKeySpec.getParams());  
        cipher.init(Cipher.DECRYPT_MODE, priKey);
       //System.out.println("解密后的明文new String(data): " + new String(cipher.doFinal(data),"UTF-8"));
       
        return cipher.doFinal(data);  
    }  
  
    //公钥加密 
    public static byte[] encrypt(byte[] data, String publicKey)  
            throws Exception {  
    	
    	Security.addProvider(new BouncyCastleProvider());//动态加入Bouncy Castle Provider，不需要对本地计算机进行相关配置，只需将bouncyCastle的jar包导入工程
    	    
        byte[] keyBytes = decryptBASE64(publicKey);  //对公钥解密
    	
  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);   
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM) ;  
        ECPublicKey pubKey = (ECPublicKey) keyFactory  
                .generatePublic(x509KeySpec);  
  
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(pubKey.getW(),  
                pubKey.getParams());  
  
        // 密码术对象提供加密和解密的功能函数，它构成JCE框架的核心。
        //Cipher cipher = Cipher.getInstance("ECIESwithDESede/NONE/PKCS7Padding", "BC");//借助Bouncy Castle，创建密码术对象，来实现加密和解密
        Cipher cipher = Cipher.getInstance("ECIES/NONE/NoPadding", "BC");
        //cipher.init(Cipher.ENCRYPT_MODE, pubKey, ecPublicKeySpec.getParams());  
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        
        //System.out.println("密文HexBinaryAdapter().marshal: " + new HexBinaryAdapter().marshal(cipher.doFinal(data)));//十六进制编码的二进制数据,marshal从绑定类型转换为值类型
        System.out.println("密文newString(data): " + new String(cipher.doFinal(data),"UTF-8"));
        
		return cipher.doFinal(data);
         
    }  
  
    //获取私钥
    public static String getPrivateKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PRIVATE_KEY);  
  
        return encryptBASE64(key.getEncoded());  
    }  
  
    //获取公钥
    public static String getPublicKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PUBLIC_KEY);  
  
        return encryptBASE64(key.getEncoded());  
    }  
  
   
   
    
    
    /** 
     * 用私钥对信息生成数字签名 
     *  
     * @param data 
     *            加密数据 
     * @param privateKey 
     *            私钥 
     *  
     * @return 
     * @throws Exception 
     */  
    public static String sign(byte[] data, String privateKey) throws Exception {  
        // 解密由base64编码的私钥  
        byte[] keyBytes = decryptBASE64(privateKey);  
  
        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);  
  
        // 取私钥匙对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 用私钥对信息生成数字签名  
        //Signature signature = Signature.getInstance(keyFactory.getAlgorithm());  
        Signature signature = Signature.getInstance("SHA1withECDSA");  
        signature.initSign(priKey); //初始化签名 
        signature.update(data);  
  
        return encryptBASE64(signature.sign());  
    }  
  
    
    /** 
     * 校验数字签名 
     *  
     * @param data 
     *            加密数据 
     * @param publicKey 
     *            公钥 
     * @param sign 
     *            数字签名 
     *  
     * @return 校验成功返回true 失败返回false 
     * @throws Exception 
     *  
     */  
    public static boolean verify(byte[] data, String publicKey, String sign)  
            throws Exception {  
  
        // 解密由base64编码的公钥  
        byte[] keyBytes = decryptBASE64(publicKey);  
  
        // 构造X509EncodedKeySpec对象  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
  
        // ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);  
  
        // 取公钥匙对象  
        PublicKey pubKey = keyFactory.generatePublic(keySpec);  
  
        //Signature signature = Signature.getInstance(keyFactory.getAlgorithm());  
        Signature signature = Signature.getInstance("SHA1withECDSA"); 
        signature.initVerify(pubKey);  //初始化验证
        signature.update(data);  
  
        // 验证签名是否正常  
        return signature.verify(decryptBASE64(sign));  
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
	
	
	
	
	
	 //生成公钥和私钥 
    public static Map<String, Object> initKey() throws Exception { 
    	
    	Security.addProvider(new BouncyCastleProvider());
    	KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC","SunEC");//生成公钥和私钥使用的provider是JDK7自带的SunEC
    	//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC","BC");
    	//KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECIES","BC");
		keyGen.initialize(256);//相当于RSA算法2048位密钥安全强度
		KeyPair keypair = keyGen.genKeyPair();
		PrivateKey privateKey = keypair.getPrivate();
		PublicKey publicKey = keypair.getPublic();

		System.out.println("privateKey:"+privateKey + "\n" + "publicKey:"+publicKey);
  
        Map<String, Object> keyMap = new HashMap<String, Object>(2);  
        keyMap.put(PUBLIC_KEY, publicKey);  
        keyMap.put(PRIVATE_KEY, privateKey);  
        
        return keyMap;  
    } 
    
    
    //重新生成公钥和私钥，并保存到数据库表中
    public static boolean generateAndSavePublicPrivateKeys(){
    	
    	boolean flag=true;
		try {
			Map<String, Object> keyMap= ECC_SHA.initKey();
			String privateKey = ECC_SHA.getPrivateKey(keyMap);
			System.out.println("[generateAndSavePublicPrivateKeys]Web Server端生成的私钥："+privateKey);
			log.info("[generateAndSavePublicPrivateKeys]Web Server端生成的私钥："+privateKey);
			System.out.println("[generateAndSavePublicPrivateKeys]Web Server端生成的私钥长度："+privateKey.length());
			
			
			String publicKey = ECC_SHA.getPublicKey(keyMap);
			System.out.println("[generateAndSavePublicPrivateKeys]Web Server端生成的公钥："+publicKey);
			log.info("[generateAndSavePublicPrivateKeys]Web Server端生成的公钥："+publicKey);
			System.out.println("[generateAndSavePublicPrivateKeys]Web Server端生成的公钥长度："+publicKey.length());
			
			//使用新的Server端公钥加密已经存在的共享密钥（表中原有的共享密钥数据为使用旧的公钥加密后得到的密文数据），并保存到数据库表tab_sharekey中
			if(PhoneDao.useNewServerPublicKeyDealOldShareKey(publicKey)){
				int m=PhoneDao.saveOrUpdatePublicPrivateKey(privateKey, publicKey);
				if(m>=1){
					System.out.println("[generateAndSavePublicPrivateKeys]查询数据表得到Web Server端公钥："+PhoneDao.getServerPublicKey());
					System.out.println("[generateAndSavePublicPrivateKeys]查询数据表得到Web Server端私钥："+PhoneDao.getServerPrivateKey());
				}else{
					System.out.println("[generateAndSavePublicPrivateKeys]PhoneDao.saveOrUpdatePublicPrivateKey(privateKey, publicKey)执行失败");
					flag=false;
				}
				
			}else{
				System.out.println("[generateAndSavePublicPrivateKeys]PhoneDao.useNewServerPublicKeyDealOldShareKey(publicKey)执行失败");
				flag=false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
		
    }
 
    //生成公钥和私钥,并使用公钥进行加密，使用私钥进行解密
   /* public static void main(String[] args){
		
		try {
			Map<String, Object> keyMap=ECC_SHA.initKey(); 
			String privateKey = ECC_SHA.getPrivateKey(keyMap);
			String publicKey = ECC_SHA.getPublicKey(keyMap); 
			System.out.println("Web Server端生成的私钥："+privateKey);
			System.out.println("Server端生成的私钥长度："+privateKey.length());
			System.out.println("Web Server端生成的公钥："+publicKey);
			System.out.println("Server端生成的公钥长度："+publicKey.length());
			
			String testString="welcome to beijing";
			try {
				byte[] testString_encrypted=ECC_SHA.encrypt(testString.getBytes(), publicKey);
				System.out.println("加密后："+new String(testString_encrypted));
				byte[] testString_decrypted=ECC_SHA.decrypt(testString_encrypted, privateKey);
				System.out.println("解密后："+new String(testString_decrypted));
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	/**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        
        return md5.digest();

    }

    //SHA仍然是公认的安全加密算法，较之MD5更为安全。
    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance("SHA");
        sha.update(data);
       
        return sha.digest();

    }
    
    
    
    /** 
     * DES算法，加密 
     * 
     * @param data 待加密字符串 
     * @param key  加密私钥，长度不能够小于8位 
     * @return 加密后的字节数组，一般结合Base64编码使用 
     * @throws CryptException 异常 
     */ 
    public static  byte[] DESEncode(String key,byte[] data) throws Exception  
    {  
        try  
        {  
            DESKeySpec dks = new DESKeySpec(key.getBytes());  
              
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            //key的长度不能够小于8位字节  
            Key secretKey = keyFactory.generateSecret(dks);  
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);  
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());  
            AlgorithmParameterSpec paramSpec = iv;  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);  
              
            byte[] bytes = cipher.doFinal(data);  
              
            return bytes;  
        } catch (Exception e)  
        {  
            throw new Exception(e);  
        }  
    }  
    
    
    
    
    /** 
     * DES算法，解密 
     * 
     * @param data 待解密字符串 
     * @param key  解密私钥，长度不能够小于8位 
     * @return 解密后的字节数组 
     * @throws Exception 异常 
     */  
    public static byte[] DESDecode(String key,byte[] data) throws Exception  
    {  
        try  
        {  
            SecureRandom sr = new SecureRandom();  
            DESKeySpec dks = new DESKeySpec(key.getBytes());  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            //key的长度不能够小于8位字节  
            Key secretKey = keyFactory.generateSecret(dks);  
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);  
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());  
            AlgorithmParameterSpec paramSpec = iv;  
            cipher.init(Cipher.DECRYPT_MODE, secretKey,paramSpec);  
            return cipher.doFinal(data);  
        } catch (Exception e)  
        {  
            throw new Exception(e);  
        }  
    }  
    
    
    public static String generateDESKey(){
    	
    	int mobile_code = (int)((Math.random()*9+1)*10000000);//构建8位手机验证码,Math.random():greater than or equal to 0.0 and less than 1.0.
    	String key=String.valueOf(mobile_code);
    	System.out.println("字节长度："+key.getBytes().length);
    	return key;
    	
    	
    }
    /*
    //使用DES生成密钥,进行加密和解密测试
     public static void main(String[] args){
 		
 		String key=ECC_SHA.generateDESKey();
 		String data="beifanggongyedaxue";
 		try {
			String data_encode=ECC_SHA.encryptBASE64(ECC_SHA.DESEncode(key, data.getBytes()));
			System.out.println("DES加密后："+data_encode);
			String data_decode=new String(ECC_SHA.DESDecode(key, ECC_SHA.decryptBASE64(data_encode)));
			System.out.println("DES解密后："+data_decode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

 	}*/
    
    
    /*public static String generateAESKey(){
    	
    	int mobile_code = (int)((Math.random()*9+1)*10000000);//构建8位手机验证码,Math.random():greater than or equal to 0.0 and less than 1.0.
    	String key=String.valueOf(mobile_code);
    	System.out.println("字节长度："+key.getBytes().length);
    	return key;
    	
    	
    }*/
    
    
    
    /** 
     * 加密 
     *  
     * @param content 需要加密的内容 
     * @param password  加密密码 
     * @return 
     */  
    public static byte[] AESEncrypt(String content, String password) {  
            try {             
                   /* KeyGenerator kgen = KeyGenerator.getInstance("AES");  
                    kgen.init(128, new SecureRandom(password.getBytes()));  
                    SecretKey secretKey = kgen.generateKey();  
                    byte[] enCodeFormat = secretKey.getEncoded();  */
                    
                    
	            	byte[] enCodeFormat=null;
					try {
						enCodeFormat = ECC_SHA.getRawKey(password.getBytes());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    
                    
                    
                    SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
                    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器   
                    byte[] byteContent = content.getBytes("utf-8");  
                    cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
                    byte[] result = cipher.doFinal(byteContent);  
                    return result; // 加密   
            } catch (NoSuchAlgorithmException e) {  
                    e.printStackTrace();  
            } catch (NoSuchPaddingException e) {  
                    e.printStackTrace();  
            } catch (InvalidKeyException e) {  
                    e.printStackTrace();  
            } catch (UnsupportedEncodingException e) {  
                    e.printStackTrace();  
            } catch (IllegalBlockSizeException e) {  
                    e.printStackTrace();  
            } catch (BadPaddingException e) {  
                    e.printStackTrace();  
            }  
            return null;  
    }  
	
    
    
    /**解密 
     * @param content  待解密内容 
     * @param password 解密密钥 
     * @return 
     */  
    public static byte[] AESDecrypt(byte[] content, String password) {  
    	
    		
    	
            try {  
                     /*KeyGenerator kgen = KeyGenerator.getInstance("AES");  
                     kgen.init(128, new SecureRandom(password.getBytes()));  
                     SecretKey secretKey = kgen.generateKey();  
                     byte[] enCodeFormat = secretKey.getEncoded(); */ 
            	
            	
            		 byte[] enCodeFormat=null;
					try {
						enCodeFormat = ECC_SHA.getRawKey(password.getBytes());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	
            	
            	
                     SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
                     Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器   
                    cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
                    byte[] result = cipher.doFinal(content);  
                    return result; // 加密   
            } catch (NoSuchAlgorithmException e) {  
                    e.printStackTrace();  
            } catch (NoSuchPaddingException e) {  
                    e.printStackTrace();  
            } catch (InvalidKeyException e) {  
                    e.printStackTrace();  
            } catch (IllegalBlockSizeException e) {  
                    e.printStackTrace();  
            } catch (BadPaddingException e) {  
                    e.printStackTrace();  
            }  
            return null;  
    }  
    
    
    /**将二进制转换成16进制 
     * @param buf 
     * @return 
     */  
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
    
    
    
    
    /**将16进制转换为二进制 
     * @param hexStr 
     * @return 
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {  
            if (hexStr.length() < 1)  
                    return null;  
            byte[] result = new byte[hexStr.length()/2];  
            for (int i = 0;i< hexStr.length()/2; i++) {  
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                    result[i] = (byte) (high * 16 + low);  
            }  
            return result;  
    }  
    
    
    private static byte[] getRawKey(byte[] seed) throws Exception {
    	
    	Security.addProvider(new BouncyCastleProvider());
    	
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
        SecureRandom sr = null;
        /*if (android.os.Build.VERSION.SDK_INT >=  17) {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
            if(log==1) {
                Log.i("test", "Android操作系统版本：SDK_INT >=  17");
            }

        } else {
            sr = SecureRandom.getInstance("SHA1PRNG");
            if(log==1) {
                Log.i("test", "Android操作系统版本：SDK_INT <  17");
            }
        }*/
        //sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        //sr = SecureRandom.getInstance("SHA1PRNG", "BC");
        sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); //256 bits or 128 bits,192bits
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }
    
    //使用AES生成密钥,进行加密和解密测试
   /* public static void main(String[] args){*/
		
		
		/*String content = "beijingiloveyou";  
		String password = ECC_SHA.generateAESKey();
		
		//加密   
		System.out.println("加密前：" + content);  
		byte[] encryptResult = ECC_SHA.AESEncrypt(content, password);  
		//解密   
		byte[] decryptResult = ECC_SHA.AESDecrypt(encryptResult,password);  
		System.out.println("解密后：" + new String(decryptResult)); */
    	
    	
    	
    	
    	
    	/*String content = "beifanggongyedaxue";  
    	String password="12345678";
    	System.out.println("共享密码：" + password); 
    	//加密   
    	System.out.println("加密前：" + content);  
    	byte[] encryptResult = ECC_SHA.AESEncrypt(content, password);  
    	try {  */
    	        
    		    /* String encryptResultStr = ECC_SHA.encryptBASE64(encryptResult);
    		
    			 //String encryptResultStr="3EjJYvnvy4FLyea0Ix3Avwey86fTRkb3HGo7TQcbiqM=";
    			 
    		
    		     System.out.println("加密后使用Base64编码：" + encryptResultStr); 
    		     byte[] decryptResult = ECC_SHA.AESDecrypt(ECC_SHA.decryptBASE64(encryptResultStr),password); 
    		     System.out.println("解密后：" + new String(decryptResult));  
    		      */
    		       
    	          
    	         
    		     /*String encryptResultStrByte2Hex = parseByte2HexStr(encryptResult);  
   		         System.out.println("加密后Byte2Hex：" + encryptResultStrByte2Hex);
    		     byte[] decryptFrom = parseHexStr2Byte(encryptResultStrByte2Hex);  
    		     byte[] decryptResult = ECC_SHA.AESDecrypt(decryptFrom,password);
    		     System.out.println("解密后：" + new String(decryptResult));  
    	        
    	} catch (Exception e) {  
    	        e.printStackTrace();  
    	}  */
    	
    	
    	
    	//经过测试使用AES分别在Android端或者服务器端进行加密解密时可以实现的，但是如果在Android加密在服务器端解密就会
    	//报异常：javax.crypto.BadPaddingException: Given final block not properly padded
    	
    	/*String encryptByte2HexStr="DC48C962F9EFCB814BC9E6B4231DC0BF07B2F3A7D34646F71C6A3B4D071B8AA3";
    	String password="12345678";
    	String content="beifanggongyedaxue";
    	byte[] encryptResult = ECC_SHA.AESEncrypt(content, password);
    	String encryptByte2HexStr = ECC_SHA.parseByte2HexStr(encryptResult);
    	System.out.println("加密后使用Byte2Hex转为字符串：" + encryptByte2HexStr); 
	     
		try {
			byte[] decryptResult = ECC_SHA.AESDecrypt(ECC_SHA.parseHexStr2Byte(encryptByte2HexStr),password);
			System.out.println("解密后：" + new String(decryptResult));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	       
    	
    	//测试使用对称加密算法DES，在Android端加密在服务器端解密处理成功。
    	/*String key="12345678";
    	String data_encode_base64="uJ7oly16fdAqhkaOigPCCSCGe6aRXmgc";
    	String data_decode_newString=null;
		try {
			data_decode_newString = new String(ECC_SHA.DESDecode( key,ECC_SHA.decryptBASE64(data_encode_base64)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("DES解密new Sring(XX)后："+data_decode_newString);*/
		
    	
    	
    	

	/*}*/
    
    
    //使用AES生成密钥,进行加密和解密测试
    public static void main(String[] args){
    	
    	String content = "beifanggongyedaxue";  
    	String password="12345678";
    	//加密   
    	try {
			System.out.println("加密前原始内容：" + content);  
			byte[] encryptResult = ECC_SHA.AESEncrypt(content, password);
			
			
			String encryptResultStr = ECC_SHA.encryptBASE64(encryptResult);
			//String encryptResultStr="3EjJYvnvy4FLyea0Ix3Avwey86fTRkb3HGo7TQcbiqM=";
			 

			System.out.println("AES加密后使用Base64编码：" + encryptResultStr); 
			byte[] decryptResult = ECC_SHA.AESDecrypt(ECC_SHA.decryptBASE64(encryptResultStr),password); 
			System.out.println("解密后：" + new String(decryptResult));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
    
    
    
    
    
    
    
    
}
