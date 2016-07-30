package cn.ncut.deviceservlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import cn.ncut.devicedao.PhoneDao;
import cn.ncut.devicedao.RegisterDao;
import cn.ncut.devicedomain.ApkInfo;
import cn.ncut.deviceservice.PhoneService;
import cn.ncut.pushdao.JsonDao;
import cn.ncut.utils.ECC_SHA;
/**
 * @author wzq
 * 
 *         version 1.0 2014-9-19 上午9:41:14
 */
@Slf4j
public class PhoneServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	PhoneService service = new PhoneService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");
		if ("SaveOpenInfo".equals(method)) {
			SaveOpenInfo(request, response);
		} else if ("updateapk".equals(method)) {
			updateapk(request, response);
		} else if ("getapkinfo".equals(method)) {
			getapkinfo(request, response);
		} else if ("picDownloadConfirm".equals(method)) {
			picDownloadConfirm(request, response);
		} else if ("appSettingConfirm".equals(method)) {
			appSettingConfirm(request, response);
		}else if ("SingleSaveUnlockInfo".equals(method)) {
			SingleSaveUnlockInfo(request,response);
		}else if ("ToIndex".equals(method)) {
			
			ToIndex(request,response);
		}else if("mobilePublicKey".equals(method)){
			
			saveMobilePublicKey(request,response);
		}else if("sharedKey".equals(method)){
			saveSharedKey(request,response);
		}else if("getNewServerPublicKey".equals(method)){
			getNewServerPublicKey(request,response);
		}

	}

	/*
	 * ********************************
	 * 云平台服务器自用函数**************************************************************
	 */

	/**
	 * @param request
	 * @param response
	 */
	private void ToIndex(HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			String imei=request.getParameter("imei");
			
			String  unitno=service.getUnitno(imei);
			
			if ("100002".equals(unitno)) {
				
				request.getRequestDispatcher("/dxt.jsp").forward(request, response);
				
			}else {
				request.getRequestDispatcher("/default.jsp").forward(request, response);
			}
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 显示apk版本变化信息
	 * 
	 * @param request
	 * @param response
	 */
	private void getapkinfo(HttpServletRequest request,
			HttpServletResponse response) {
		int currentPage;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e1) {
			currentPage = 1;
		}
		int pageSize;
		try {
			pageSize = Integer.parseInt(request.getParameter("rows"));
		} catch (NumberFormatException e1) {
			pageSize = 10;
		}

		List<ApkInfo> apkInfos = service.getapkinfo(currentPage, pageSize);

		int total = service.getApkTotal();

		response.setContentType("text/html;charset=utf-8");

		String json = "{\"total\":" + total + " , \"rows\":"
				+ JSONArray.fromObject(apkInfos).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param request
	 * @param response
	 */
	private void updateapk(HttpServletRequest request,
			HttpServletResponse response) {
		String savepath = this.getServletContext().getRealPath("/client_app");
		service.updateapk(request, response, savepath);

		try {
			request.getRequestDispatcher("/pages/success.jsp").forward(request,
					response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * *************************************************客户端接口函数******************
	 * *******************************
	 */
	
	
	/*保存手机端公钥到数据表中*/
	private void saveMobilePublicKey(HttpServletRequest request,
			HttpServletResponse response){
		
		String returnResult=null;
		PrintWriter printWriter = null;
		
		try {
			printWriter = response.getWriter();
			String receivedInfo = request.getParameter("publicKeyInfo");
			System.out.println("[saveMobilePublicKey]服务器端接收到json串："+receivedInfo);
			log.info("[saveMobilePublicKey]服务器端接收到json串："+receivedInfo);
			
			JSONObject jsonobject = new JSONObject(receivedInfo);
			String imei=jsonobject.getString("phone_imei");
			String base64MobilePublicKey=jsonobject.getString("public_key");
			String base64MobilePublicKeySha=jsonobject.getString("public_key_sha");
			System.out.println("[saveMobilePublicKey]手机设备号imei："+imei);
			log.info("[saveMobilePublicKey]手机设备号imei："+imei);
			System.out.println("[saveMobilePublicKey]接收到的mobile公钥密文："+base64MobilePublicKey);
			log.info("[saveMobilePublicKey]接收到的mobile公钥密文:"+base64MobilePublicKey);
			System.out.println("[saveMobilePublicKey]接收到的mobile公钥SHA摘要经过加密后的密文："+base64MobilePublicKeySha);
			log.info("[saveMobilePublicKey]接收到的mobile公钥SHA摘要经过加密后的密文："+base64MobilePublicKeySha);

			String mobilePublicKeyBASE64=null;
			String mobilePublicKeyNewSha=null;
			String mobilePublicKeyNewSHADigest=null;
			String oldMessageDigestReceive=null;
			String oldMessageDigest=null;//存放接收到的摘要密文解密后得到的旧摘要信息
			
			try {
				String serverPrivateKey=null;
				if(PhoneDao.isExistPublicPrivateKey()){

					serverPrivateKey=PhoneDao.getServerPrivateKey();//得到tab_serverkeys表中的服务器端私钥
				}else {

					serverPrivateKey=ECC_SHA.SERVER_PRIVATE_KEY;
				}

				
				
				//对接收的公钥密文使用Server端私钥进行解密
				mobilePublicKeyBASE64=ECC_SHA.encryptBASE64(ECC_SHA.decrypt(ECC_SHA.decryptBASE64(base64MobilePublicKey), serverPrivateKey));
				System.out.println("[saveMobilePublicKey]经过解密后得到的mobile端的公钥,经过Base64编码后："+mobilePublicKeyBASE64);
				log.info("[saveMobilePublicKey]经过解密后得到的mobile端的公钥,经过Base64编码后："+mobilePublicKeyBASE64);
				//重新生成摘要信息
				mobilePublicKeyNewSHADigest=new BigInteger(ECC_SHA.encryptSHA(ECC_SHA.decryptBASE64(mobilePublicKeyBASE64))).toString(16);
				System.out.println("[saveMobilePublicKey]经过解密后得到的mobile端的公钥,使用SHA生成的新的摘要信息："+mobilePublicKeyNewSHADigest);
				log.info("[saveMobilePublicKey]经过解密后得到的mobile端的公钥,使用SHA生成的新的摘要信息："+mobilePublicKeyNewSHADigest);
				
				//对接收到的摘要密文使用Server端私钥进行解密
				//oldMessageDigest=new BigInteger(ECC_SHA.decrypt(ECC_SHA.decryptBASE64(base64MobilePublicKeySha), ECC_SHA.SERVER_PRIVATE_KEY)).toString(16);
				oldMessageDigest=new String(ECC_SHA.decrypt(ECC_SHA.decryptBASE64(base64MobilePublicKeySha), serverPrivateKey));
				System.out.println("[saveMobilePublicKey]经过解密后得到的mobile端传送过来的摘要信息："+oldMessageDigest);
				log.info("[saveMobilePublicKey]经过解密后得到的mobile端传送过来的摘要信息："+oldMessageDigest);
				

				
				//比较接收到的解密后的摘要信息与公钥密文解密后重新生成的摘要信息
				if(mobilePublicKeyNewSHADigest.equals(oldMessageDigest)){
				

					//说明移动客户端公钥在传输过程中没有被修改过
					int rowCount=PhoneDao.saveOrUpdateMobilePublicKey(imei,mobilePublicKeyBASE64 );//保存或更新此imei对应的手机端公钥
					if(rowCount>=1){
						//反馈给移动客户端的信息.表示Web服务器端接收到手机端公钥，公钥数据在传输过程中未发生篡改，并且公钥已经成功保存
						printWriter.write("{success:1}");
						System.out.println("[saveMobilePublicKey]mobile公钥保存或者更新成功");
						log.info("[[saveMobilePublicKey]mobile公钥保存或者更新成功");
						System.out.println("[saveMobilePublicKey]服务器端返回给mobile的json信息：{success:1}");
						log.info("[saveMobilePublicKey]服务器端返回给mobile的json信息：{success:1}");
						
					}else{
						printWriter.write("{success:-1}");//公钥数据在传输过程中未发生篡改，但是保存公钥未成功
						System.out.println("[saveMobilePublicKey]服务器端返回给mobile的json信息：{success:-1}");
						log.info("[saveMobilePublicKey]服务器端返回给mobile的json信息：{success:-1}");
					}
					
				}else{
					
					printWriter.write("{success:0}");//公钥数据在传输过程中被篡改了!
					System.out.println("[saveMobilePublicKey]公钥数据在传输过程中被篡改,服务器端返回给mobile的json信息：{success:0}");
					log.info("[saveMobilePublicKey]公钥数据在传输过程中被篡改,服务器端返回给mobile的json信息：{success:0}");
				}


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		} catch (Exception e) {

			e.printStackTrace();
		}
		

	}
	
	
	private void saveSharedKey(HttpServletRequest request,HttpServletResponse response){
		
		String sharedKey_encrypt=null;
		String sharedKey_encrypt_sign=null;
		String imei=null;	
		String sharedKeyInfo = request.getParameter("sharedKeyInfo");
		System.out.println("[saveSharedKey]服务器端接收到json串："+sharedKeyInfo);
		log.info("[saveSharedKey]服务器端接收到json串："+sharedKeyInfo);
		
		JSONObject jsonobject;
		String androidPublicKey=null;
		boolean flag=false;
		PrintWriter printWriter = null;
		
		/*接收数据*/
		try {
			printWriter = response.getWriter();
			jsonobject = new JSONObject(sharedKeyInfo);
			sharedKey_encrypt=jsonobject.getString("sharedKey_encrypt");
			sharedKey_encrypt_sign=jsonobject.getString("sharedKey_encrypt_sign");
			imei=jsonobject.getString("phone_imei");
			System.out.println("[saveSharedKey]服务器端接收到的移动客户端共享密钥密文："+sharedKey_encrypt);
			log.info("[saveSharedKey]服务器端接收到的移动客户端共享密钥密文："+sharedKey_encrypt);
			
			System.out.println("[saveSharedKey]服务器端接收到的移动客户端签名数据："+sharedKey_encrypt_sign);
			log.info("[saveSharedKey]服务器端接收到的移动客户端签名数据："+sharedKey_encrypt_sign);
			
			if(PhoneDao.isExistMobilePublicKeyForCertainImei(imei)){
				try {
					androidPublicKey=PhoneDao.getPublicKeyForCertainImei(imei);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("[saveSharedKey]表tab_mobilepublickey中"+imei+"对应的移动客户端公钥:"+androidPublicKey);
				log.info("[saveSharedKey]表tab_mobilepublickey中"+imei+"对应的移动客户端公钥:"+androidPublicKey);
				
				/*验证签名*/
				try {
					flag= ECC_SHA.verify(ECC_SHA.decryptBASE64(sharedKey_encrypt), androidPublicKey, sharedKey_encrypt_sign);
					if(flag){
						
						System.out.println("[saveSharedKey]签名验证成功");
						log.info("[saveSharedKey]签名验证成功");
						
						
						/*验签成功则解密数据*/
						try {
							String serverPrivateKey=null;
							if(PhoneDao.isExistPublicPrivateKey()){

								serverPrivateKey=PhoneDao.getServerPrivateKey();//得到tab_serverkeys表中的服务器端私钥
							}else {

								serverPrivateKey=ECC_SHA.SERVER_PRIVATE_KEY;
							}
							
							
							String initialData=new String(ECC_SHA.decrypt(ECC_SHA.decryptBASE64(sharedKey_encrypt), serverPrivateKey));
							String newStr = new String(initialData.getBytes(), "UTF-8");  
							System.out.println("[saveSharedKey]解密得到的共享密钥："+newStr);
							log.info("[saveSharedKey]解密得到的共享密钥："+newStr);
							
							
							
							
							//sharedKey_encrypt为对共享密钥使用ECIES算法借助于Server端公钥进行加密，并使用Base64编码的结果
							
							int rowCount=PhoneDao.saveOrUpdateShareKey(imei,sharedKey_encrypt);//保存或更新此imei对应的共享密钥
							if(rowCount>=1){
								
								printWriter.write("{success:1}");
								//反馈给移动客户端的信息.表示Web服务器端接收到共享密钥，签名验证成功，并且公钥已经成功保存
								System.out.println("[saveSharedKey]共享密钥保存成功");
								log.info("[saveSharedKey]共享密钥保存成功");
								System.out.println("[saveSharedKey]服务器端返回给mobile的json信息：{success:1}");
								log.info("[saveSharedKey]服务器端返回给mobile的json信息：{success:1}");
								
							}else{
								printWriter.write("{success:-1}");//验签成功，但是保存公钥未成功
								System.out.println("{success:-1}");
							}
							
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
						}
						
						
					}else{
						
						printWriter.write("{success:0}");
						System.out.println("[saveSharedKey]签名验证失败！");
						log.info("[saveSharedKey]签名验证失败！");
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	

				
			}else{
				System.out.println("[saveSharedKey]数据库表tab_mobilepublickey中不存在"+imei+"对应的移动客户端公钥!");
				log.info("[saveSharedKey]数据库表tab_mobilepublickey中不存在"+imei+"对应的移动客户端公钥!");
				
			}
	
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	//查询并返回Server端公钥给移动客户端，若 更改成功则设置update_key=0
	private void getNewServerPublicKey(HttpServletRequest request,HttpServletResponse response){
		
		
		String json_string = request.getParameter("setting_info");
		System.out.println("[getNewServerPublicKey]服务器端接收到json串："+json_string);
		log.info("[getNewServerPublicKey]服务器端接收到json串："+json_string);
		
		JSONObject jsonobject;
		PrintWriter printWriter = null;
		
		String phone_imei=null;
		try {
			printWriter = response.getWriter();
			jsonobject = new JSONObject(json_string);
			phone_imei = jsonobject.getString("phone_imei");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String public_key=PhoneDao.getServerPublicKey();
		

		
		RegisterDao rd=new RegisterDao();
		int m=rd.ModifyUpdateKey(phone_imei, 0);
		if(m==1){
			
			System.out.println("[getNewServerPublicKey]数据库表tab_deviceinfo中"+phone_imei+"对应的移动客户端update_key值设置为：0");
			log.info("[getNewServerPublicKey]数据库表tab_deviceinfo中"+phone_imei+"对应的移动客户端update_key值设置为：0");
			
			printWriter.write("{public_key:\""+public_key+"\"}");
			
		}else{
			
			System.out.println("[getNewServerPublicKey]数据库表tab_deviceinfo中"+phone_imei+"对应的移动客户端update_key值更改失败!");
			log.info("[getNewServerPublicKey]数据库表tab_deviceinfo中"+phone_imei+"对应的移动客户端update_key值更改失败!");
			
		}
		
		
	}
	
	
	
	
	/**
	 * 保存开锁时间
	 * 
	 * @param request
	 * @param response
	 */
	
	private void SingleSaveUnlockInfo(HttpServletRequest request,
			HttpServletResponse response) {
		
		PrintWriter printWriter = null;
		 String ret=null;
		
      try {
			printWriter = response.getWriter();
			String newlockinfo = request.getParameter("newlockinfo");
			
		    ret=service.saveslideinfo(newlockinfo);
           

		} catch (Exception e) {

			e.printStackTrace();
		}
		printWriter.write(ret);

	}
	
	/*
	 * 保存地址位置信息 设备信息  网址信息
	 */
	
	public void SaveOpenInfo(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter printWriter = null;
		String picString = null;

		try {
			printWriter = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			String param = request.getParameter("screenlock");

			picString = service.SaveOpenInfo(param);

			log.info("手机端提交:screenlock"+param);

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
		if (picString == null||"".equals(picString.trim())) {
			picString = "{flag:500}";
		}
      	log.info("服务器返回:"+picString);
        printWriter.write(picString);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	private void picDownloadConfirm(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			String re = "1";
			String picString = request.getParameter("confirm_info");
			if (picString != null
					&& picString
							.matches("\\{phone_imei:\"[A-Za-z0-9]+\",pics_name:\\[\"P_[0-9]+_[0-9]+_[0-9]+.jpg\"(,\"P_[0-9]+_[0-9]+_[0-9]+.jpg\")*\\]\\}")) {
				String[] picstr = picString.split("\"");
				String imei = picstr[1];
				String pics = "";
				for (int i = 3; i < picstr.length; i++) {
					if (picstr[i].contains("P_")) pics += picstr[i] + ",";
				}
				JsonDao x = new JsonDao();
				re = x.ConfirmPics(imei, pics);
			}
			if (re.equals("0")) {
				writer.write("{success:0}");
			} else {
				writer.write("{success:1}");
			}
			;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void appSettingConfirm(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String picString = request.getParameter("setting_info");
			String imei = null;
			String setting = null;
			String json = "[" + picString + "]";
			System.out.println(picString);
			org.json.JSONArray array = new org.json.JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				imei = jsonObject.getString("phone_imei");
				setting = jsonObject.getString("setting");

			}

			service.appSettingConfirm(imei, setting);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
