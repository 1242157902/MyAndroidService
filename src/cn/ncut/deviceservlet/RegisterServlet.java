package cn.ncut.deviceservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.ncut.devicedao.PhoneDao;
import cn.ncut.deviceservice.RegisterService;
import cn.ncut.pushservice.PhService;
import cn.ncut.utils.ECC_SHA;
import cn.ncut.utils.TestUtil;

/**
 * @author wzq
 * 
 *         version 1.0 2014-12-13 上午10:29:51
 */
@Slf4j
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	RegisterService service = new RegisterService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if (method.equals("register")) {
			register(request, response);
		} else if (method.equals("decodetwodim")) {
			decodetwodim(request, response);
		} else if(method.equals("registerEncryptSign")){
			registerEncryptSign(request, response);
		}

	}

	/**
	 * @param request
	 * @param response
	 */
	private void decodetwodim(HttpServletRequest request,
			HttpServletResponse response) {
		String param = null;
		String phoneimei = null;
		String performance = null;
		String comno = null;
		String shopno = null;
		String empno = null;
		String unit = null;
		PrintWriter writer = null;
		
		try {
			writer = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		if (request.getParameter("performance_info") != null
				&& !"".equals(request.getParameter("performance_info"))) {

			param = request.getParameter("performance_info");

			String json = "[" + param + "]";
			System.out.println(param);

			try {
				
				
				
				JSONArray array = new JSONArray(json);

				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);

					phoneimei = jsonObject.getString("phone_imei");
					// 员工二维码信息格式为yihua_comno_departno_empno
					performance = jsonObject.getString("performance");

					String[] strs = performance.split("-");
					comno = strs[1];
					shopno = strs[2];
					empno = strs[3];

				}
				
         	/*	PhService phservice=new PhService();
				phservice.RegisterAccepter(phoneimei, null, null, comno, null, null, null, null,null,null,shopno,empno);*/

				
				boolean isexist = service.isexist(phoneimei, comno, shopno,
						empno);
				if (isexist) {
					writer.write("{success:2}");
				} else {

					boolean issuccess = service.addempinfo(phoneimei, comno,
							shopno, empno);

					if (issuccess) {
						writer.write("{success:1}");
					} else {
						service.addempinfo(phoneimei, comno, shopno, empno);
					}
				}

			} catch (Exception e) {
				writer.write("{success:0}");
			}

		} else if (request.getParameter("unit_info") != null
				&& !"".equals(request.getParameter("unit_info"))) {

			
			param = request.getParameter("unit_info");

			String json = "[" + param + "]";
			
			System.out.println("json:"+json);
		

			try {
				
				JSONArray array = new JSONArray(json);

				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);

					phoneimei = jsonObject.getString("phone_imei");
					
					
					// 归属单位信息格式为unit-comno
					unit = jsonObject.getString("unit");

					String[] strs = unit.split("-");
					comno = strs[1];
					
				/*	PhService phservice=new PhService();
					phservice.RegisterAccepter(phoneimei, null, comno, null, null, null, null, null,null,null,null,null);
					*/

				}

				boolean isexist = service.isexist(phoneimei, comno);
				if (isexist) {

					writer.write("{success:2}");
					
				} else {

					boolean issuccess = service.updateunit(phoneimei, comno);
					if (issuccess) {
						writer.write("{success:1}");
					} else {
						service.updateunit(phoneimei, comno);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
				writer.write("{success:0}");
			}

		} else {
			
			writer.write("{success:0}");
		}

	}

	/**
	 * @param request
	 * @param response
	 */
	private void register(HttpServletRequest request,
			HttpServletResponse response) {
		RegisterService service=new RegisterService();
		try {
			String param = request.getParameter("register");
			
			log.info("用户注册信息:"+param);

			
			int age = 0;
			String sex = null;
			String imsi = null;
			String phonenum = null;
			String imei = null;
			String nickname=null;

			

		
				JSONObject jsonObject = new JSONObject(param);
				age = jsonObject.getInt("age");
				sex = jsonObject.getString("sex");
				imei = jsonObject.getString("phone_imei");
				imsi = jsonObject.getString("phone_imsi");
				phonenum = jsonObject.getString("phone_num");
				nickname=jsonObject.getString("nickname");
				
				try {
					//app_stat:["QQ","微信"，“逗地主”]  侯金凤
					JSONArray	app_starts=jsonObject.getJSONArray("app_stat");

					StringBuilder sbBuilder=new StringBuilder();
					for (int j = 0; j < app_starts.length(); j++) {
						String app_name=(String)app_starts.get(j);
						sbBuilder.append(app_name).append(",");
						
					}
					
					sbBuilder.deleteCharAt(sbBuilder.lastIndexOf(","));
					
					
					String appnames=sbBuilder.toString();
          
                  service.saveappnames(imei,phonenum,appnames);
				} catch (Exception e) {
					
					e.printStackTrace();
				}

			

			if (sex.equals("girl")) {
				sex = "2";
			} else if (sex.equals("boy")) {
				sex = "1";
			}
			
			
		   
		    boolean success =service.register(imei,age,sex,phonenum,nickname);
             PrintWriter writer = response.getWriter();
			if (success) {
				writer.write("{flag:11}");
			} else {
				writer.write("{flag:10}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//2016-3-18添加，接收用户注册信息，抓取相关信息，保存到数据库中
		private void registerEncryptSign(HttpServletRequest request,
				HttpServletResponse response) {
			RegisterService service=new RegisterService();
			try {
				String param = request.getParameter("register");
				
				
				System.out.println("[registerEncryptSign]服务器端接收到json串:"+param);
				log.info("[registerEncryptSign]服务器端接收到json串:"+param);

				
				int birth_year = 0;
				String sex = null;
				String phone_imsi = null;
				String phone_num = null;
				String phone_imei = null;
				String nick_name=null;

				boolean flag=false;
				String register_encrypt=null;
				String register_encrypt_sign=null;
				String androidPublicKey=null;
				PrintWriter writer = response.getWriter();
				
				JSONObject jsonObject = new JSONObject(param);
				register_encrypt=jsonObject.getString("register_encrypt");
				register_encrypt_sign=jsonObject.getString("register_encrypt_sign");
				phone_imei=jsonObject.getString("phone_imei");
				System.out.println("[registerEncryptSign]服务器端接收到的注册信息密文数据："+register_encrypt);
				log.info("[registerEncryptSign]服务器端接收到的注册信息密文数据："+register_encrypt);
				
				System.out.println("[registerEncryptSign]服务器端接收到的签名数据："+register_encrypt_sign);
				log.info("[registerEncryptSign]服务器端接收到的签名数据："+register_encrypt_sign);
				
				if(PhoneDao.isExistMobilePublicKeyForCertainImei(phone_imei)){
					try {
						androidPublicKey=PhoneDao.getPublicKeyForCertainImei(phone_imei);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("[registerEncryptSign]表tab_mobilepublickey中"+phone_imei+"对应的移动客户端公钥是:"+androidPublicKey);
					log.info("[registerEncryptSign]表tab_mobilepublickey中"+phone_imei+"对应的移动客户端公钥是:"+androidPublicKey);
					
					
					/*验证签名*/
					try {
						flag= ECC_SHA.verify(ECC_SHA.decryptBASE64(register_encrypt), androidPublicKey, register_encrypt_sign);
						if(flag){
							System.out.println("[registerEncryptSign]签名验证成功");
							log.info("[registerEncryptSign]签名验证成功");
							
						}else{
							System.out.println("[registerEncryptSign]签名验证失败！");
							log.info("[registerEncryptSign]签名验证失败！");
							System.out.println("[registerEncryptSign]服务器端返回给mobile的json信息：{success:-1}");
							log.info("[registerEncryptSign]服务器端返回给mobile的json信息：{success:-1}");
							writer.write("{success:-1}");
							
							
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
					
					/*验签成功则解密数据*/
					
					if(flag){
						
						try {
							
							//String initialData=new String(ECC_SHA.decrypt(ECC_SHA.decryptBASE64(register_encrypt), ECC_SHA.SERVER_PRIVATE_KEY));
							//使用DES算法解密用户注册信息
							String initialData_a=TestUtil.decrypt(PhoneDao.getShareKeyForCertainImei(phone_imei),new String(ECC_SHA.decryptBASE64(register_encrypt))); 
							String initialData=new String(initialData_a.getBytes(),"UTF-8");
							System.out.println("[registerEncryptSign]解密后的用户注册信息："+initialData);
							log.info("[registerEncryptSign]解密后的用户注册信息："+initialData);
							
							
							
							JSONObject jsonRegister = new JSONObject(initialData);
							birth_year = jsonRegister.getInt("birth_year");
							sex = jsonRegister.getString("sex");
							phone_imsi = jsonRegister.getString("phone_imsi");
							phone_num = jsonRegister.getString("phone_num");
							nick_name=jsonRegister.getString("nick_name");
							
							
							try {
									//app_stat:["QQ","微信"，“逗地主”]  侯金凤
									JSONArray	app_starts=jsonRegister.getJSONArray("app_stat");

									StringBuilder sbBuilder=new StringBuilder();
									for (int j = 0; j < app_starts.length(); j++) {
										String app_name=(String)app_starts.get(j);
										sbBuilder.append(app_name).append(",");
										
									}
									
									//sbBuilder.deleteCharAt(sbBuilder.lastIndexOf(","));
									
									//为解决：若app_stat:[]则存在异常String index out of range: -1。（2016-3-22修改）
									if(app_starts.length()>=1){
										sbBuilder.deleteCharAt(sbBuilder.lastIndexOf(","));
									}
									
									
									String appnames=sbBuilder.toString();
				          
				                  service.saveappnames(phone_imei,phone_num,appnames);
								} catch (Exception e) {
									
									e.printStackTrace();
								}

							
							 if (sex.equals("girl")) {
									sex = "2";
								} else if (sex.equals("boy")) {
									sex = "1";
								}
								
								
							   
							    boolean success =service.register(phone_imei,birth_year,sex,phone_num,nick_name);
					            
								if (success) {
									writer.write("{success:1}");
									System.out.println("[registerEncryptSign]用户注册信息保存成功");
									log.info("[registerEncryptSign]用户注册信息保存成功");
									
									System.out.println("[registerEncryptSign]服务器端返回给mobile的json信息：{success:1}");
									log.info("[registerEncryptSign]服务器端返回给mobile的json信息：{success:1}");
									
								} else {
									writer.write("{success:0}");
								}
							
					
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}
					
				}else{
					System.out.println("[registerEncryptSign]数据库表tab_mobilepublickey中不存在"+phone_imei+"对应的记录!");
					log.info("[registerEncryptSign]数据库表tab_mobilepublickey中不存在"+phone_imei+"对应的记录!");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
