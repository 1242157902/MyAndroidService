package cn.ncut.deviceservice;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;
import cn.ncut.devicedao.LogDao;
import cn.ncut.devicedao.PhoneDao;
import cn.ncut.devicedao.RegisterDao;
import cn.ncut.devicedomain.ApkInfo;
import cn.ncut.devicedomain.ApkLog;
import cn.ncut.devicedomain.Interval;
import cn.ncut.devicedomain.Location;
import cn.ncut.devicedomain.ScreenOnOff;
import cn.ncut.devicedomain.SlideModel;
import cn.ncut.devicedomain.UserClickTime;
import cn.ncut.pushdomain.AppFlow;
import cn.ncut.pushservice.PhService;
import cn.ncut.syssetdao.TimeDao;

/**
 * @author wzq
 * 
 *         version 1.0 2014-9-18 下午10:31:34
 */
public class PhoneService {

	PhoneDao dao = new PhoneDao();
	LogDao logDao = new LogDao();

	/**
	 * 保存设备行为信息
	 * 
	 */
	public String SaveOpenInfo(String json) {
		TimeDao timeDao = new TimeDao();
		PhService service = new PhService();
		Interval interval = timeDao.gettime();
		int intervaltime = 0;
		int location_interval = 0;

		try {
			intervaltime = Integer.parseInt(interval.getIntervaltime());
			location_interval = Integer.parseInt(interval
					.getLocation_interval());
		} catch (Exception e) {
			intervaltime = 30;
			location_interval = 5;
		}

		String phoneimsi = null;
		String phonetype = null;
		String phonenumber = null;
		String phoneimei = null;

		String phoneversion = null;
		String curr_version = null;
		boolean updateapk = false;

		String picstring = null;
		String apk_version = null;
		String entertime = null;
		String receive_time = null;
		String sequence = null;
		int update_key=0;

		int model_flag = 5;
		int pics_lack = 0;
		int screenStart = 0;
		int screenEnd = 0;
		// 方耀耀返回
		String pics[] = null;
		try {
			try {
				/************************************************** 王照清 *******************************************************/
				JSONObject jsonObject = new JSONObject(json);
				try {
					phonenumber = jsonObject.getString("phone_num");
					model_flag = jsonObject.getInt("model_flag");
					pics_lack = jsonObject.getInt("pics_lack");
				} catch (Exception e) {
					phonenumber = null;
					model_flag = 5;
					pics_lack = 0;
				}
				phoneimei = jsonObject.getString("phone_imei");
				RegisterDao registerDao = new RegisterDao();
				phonenumber = registerDao.getnumbyimei(phoneimei);
				
				
				//2016-4-18日添加
				int m=registerDao.getUpdateKey(phoneimei);
				if(m!=1){
					
					update_key=0;
				}else{
					update_key=1;
				}
				
				
				
				
				
				if (phonenumber!=null&&phonenumber.length()>11) {
					
					phonenumber=phonenumber.substring(3);
				}
				

				try {
					phoneversion = jsonObject.getString("phone_version");
					phoneimsi = jsonObject.getString("phone_imsi");
					phonetype = jsonObject.getString("phone_type");
					receive_time = jsonObject.getString("receive_time");
					sequence = jsonObject.getString("sequence");
				} catch (Exception e) {

				}

				curr_version = jsonObject.getString("curr_version");
				// 服务器上apk的版本号
				apk_version = dao.getcurrversion();
				// 判断设备端apk是否需要更新
				updateapk = isupdateapk(apk_version, curr_version);

				// 如果设备信息不存在于设备信息表中则将设备信息保存到设备信息表，如果存在则更新
				dao.savedeviceinfo(phoneimei, phoneimsi, phonenumber,
						phonetype, phoneversion, curr_version,update_key);

				/************************************************** 侯金凤 *******************************************************/

				try {

					JSONArray jsonarray = jsonObject.getJSONArray("location");

					List<Location> locations = new ArrayList<Location>();

					for (int j = 0; j < jsonarray.length(); j++) {

						JSONObject object = (JSONObject) jsonarray.get(j);
						Double longitude = object.getDouble("longitude");
						Double latitude = object.getDouble("latitude");

						if (longitude.compareTo(4.9E-324) == 0
								|| latitude.compareTo(4.9E-324) == 0) {

							continue;

						}
						String time = object.getString("time");

						Location location = new Location();

						location.setImei(phoneimei);
						location.setPhonenum(phonenumber);
						location.setLatitude(latitude);
						location.setLongitude(longitude);
						location.setLocationtime(time);

						locations.add(location);

					}

					dao.savelocationinfo(locations);

					JSONArray array = jsonObject.getJSONArray("screen_onoff");

					List<ScreenOnOff> screenonoffs = new ArrayList<ScreenOnOff>();

					for (int j = 0; j < array.length(); j++) {

						JSONObject object = array.getJSONObject(j);

						String time = null;
						int onoff = 0;
						try {
							time = object.getString("0");
							onoff = 0;
						} catch (Exception e) {
							time = object.getString("1");
							onoff = 1;
						}
						ScreenOnOff screenonoff = new ScreenOnOff();

						screenonoff.setOnoff(onoff);
						screenonoff.setTime(time);
						screenonoff.setPhonenumber(phonenumber);
						screenonoff.setImei(phoneimei);

						screenonoffs.add(screenonoff);

					}
					screenStart = dao.getScreenTotalMint(phoneimei);
					dao.savescreenonoffinfo(screenonoffs);
					screenEnd = dao.getScreenTotalMint(phoneimei);

					// app_flow:{"QQ":500,"易划"：600，"逗地主"：2000} 单位：b

					JSONObject app_flow = jsonObject.getJSONObject("app_flow");

					Iterator<String> it = app_flow.keys();

					List<AppFlow> appFlows = new ArrayList<AppFlow>();

					while (it.hasNext()) {

						String key = it.next();
						int flow = app_flow.getInt(it.next());

						AppFlow appFlow = new AppFlow();

						appFlow.setAppname(key);
						appFlow.setFlow(flow);
						appFlow.setImei(phoneimei);
						appFlow.setPhonenumber(phonenumber);

						appFlows.add(appFlow);

					}

					dao.saveappflows(appFlows);

				} catch (Exception e) {
					
				}

				/************************************* 保存用户点击url时间 ***********************************************************/

				try {
					JSONArray myarray = jsonObject.getJSONArray("icons_url");

					String seq = jsonObject.getString("sequence");
					seq = seq.split("_")[1];

					JSONArray array = dao.getArray(seq);
					System.out.println("icons_url:" + myarray.toString());

					System.out.println("pics_url:" + array.toString());

					List<UserClickTime> userClickTimes = new ArrayList<UserClickTime>();

					for (int i = 0; i < myarray.length(); i++) {

						JSONArray myarray2 = myarray.getJSONArray(i);// 图片
						for (int j = 0; j < myarray2.length(); j++) {
							JSONArray myarray3 = myarray2.getJSONArray(j);// 图标
							for (int k = 0; k < myarray3.length(); k++) {
								String clicktime = myarray3.getString(k);
								String url = null;
								if (array.length() > i) {
									JSONArray array2 = array.getJSONArray(i);
									if (array2.length() > j) {
										url = array2.getString(j);
									}
								}

								// System.err.println("url:"+url+"  time:"+clicktime);

								UserClickTime userClickTime = new UserClickTime(
										phoneimei, phonenumber, url, clicktime);

								userClickTimes.add(userClickTime);

							}

						}
					}

					dao.saveUserClickTime(userClickTimes);
				} catch (Exception e) {
				
					
				}

				/************************************* 保存用户点击url时间 ***********************************************************/

				/************************************************** 侯金凤 *******************************************************/
			} catch (Exception e) {
				e.printStackTrace();
				String errtxt = "A: " + e.getMessage();
				String errid = e.getClass().getName();
				String errmethod = "";
				StackTraceElement[] st = e.getStackTrace();
				for (StackTraceElement stackTraceElement : st) {
					if (stackTraceElement.getClassName().substring(0, 7)
							.equals("cn.ncut."))
						;
					{
						errmethod = stackTraceElement.getClassName();
						break;
					}
				}
				service.InsertErrorLog(errid, errtxt, errmethod, phoneimei,
						json, picstring);
			}
			// 方耀耀
			try {

				// 获取手机的接收序列状态
				try {
					service.ClientRecInfo(phoneimei, sequence, receive_time);

				} catch (Exception e) {
					System.out.println("phoneimei:" + phoneimei + ", sequence:"
							+ sequence + ", receive_time" + receive_time);
				}
				// 返回图片序列
				if (pics_lack == 1 || model_flag == 5)
					service.ResetCurQue(phoneimei);
				if (model_flag == 5)
					dao.appSettingReset(phoneimei, dao.GetSlideTheme(phoneimei));

			   	pics = service.GetPushString(phoneimei); // 根据玩手机的时长自动推送

				// 输出协议信息
				entertime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				picstring = getPushString(intervaltime, phoneimei, updateapk,
						pics,entertime, apk_version, phonenumber, phonetype,
						phoneversion, location_interval,update_key);

				// 修改模式
				if (Pattern.compile("^1\\.2.*").matcher(curr_version).matches()) {
					if (dao.GetSlideThemeStatus(phoneimei).equals("0")) {
						picstring = "{flag:5,theme_unlock:"
								+ dao.GetSlideTheme(phoneimei) + "}";
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				String errtxt = "B " + e.getMessage();
				String errid = e.getClass().getName();
				String errmethod = "";
				StackTraceElement[] st = e.getStackTrace();
				for (StackTraceElement stackTraceElement : st) {
					if (stackTraceElement.getClassName().substring(0, 7)
							.equals("cn.ncut."))
						;
					{
						errmethod = stackTraceElement.getClassName();
						break;
					}
				}
				service.InsertErrorLog(errid, errtxt, errmethod, phoneimei,
						json, picstring);
			}

		} catch (Exception e) {
			e.printStackTrace();
			String errtxt = "B " + e.getMessage();
			String errid = e.getClass().getName();
			String errmethod = "";
			StackTraceElement[] st = e.getStackTrace();
			for (StackTraceElement stackTraceElement : st) {
				if (stackTraceElement.getClassName().substring(0, 7)
						.equals("cn.ncut."))
					;
				{
					errmethod = stackTraceElement.getClassName();
					break;
				}
			}
			service.InsertErrorLog(errid, errtxt, errmethod, phoneimei, json,
					picstring);
		}
		return picstring;
	}

	/**
	 * @param apk_version
	 * @param curr_version
	 * @return
	 */
	private boolean isupdateapk(String apk_version, String curr_version) {
		String[] apk_versions = apk_version.split("\\.");

		String[] curr_versions = curr_version.split("\\.");

		int len = apk_versions.length < curr_versions.length ? apk_versions.length
				: curr_versions.length;
		for (int j = 0; j < len; j++) {

			int a = Integer.parseInt(apk_versions[j]);
			int b = Integer.parseInt(curr_versions[j]);

			if (a > b) {
				return true;

			}

		}
		return false;
	}

	/**
	 * 判断图片以及apk是否需要更新 传给方耀耀的为imsi 方耀耀返回为pics序列 是否为节假日图片特殊图片 需要方耀耀返回参数
	 */

	public String getPushString(int intervaltime, String phoneimei,
			boolean updateapk, String[] pics, String entertime,
			String apkversion, String phonenum, String phonetype,
			String phoneversion, int location_interval,int update_key) {

		String picString = null;

		ApkLog apkLog = new ApkLog();

		String apkname = "yihua" + dao.getcurrversion() + ".apk";

		// 测试预备
		// pics="pics_name:['pao1.jpg','pao2.jpg','pao3.jpg','pao4.jpg'],pics_time:[1,1.5,2,1.2],display_order:['pao1.jpg','pao2.jpg','pao3.jpg','pao4.jpg','pao1.jpg','pao2.jpg','pao3.jpg','pao4.jpg']";

		// 指定节日
		boolean isholiday = false;

		if ("true".equals(pics[1])) {

			isholiday = true;

		}

		if (isholiday) {

			StringBuilder sbBuilder = new StringBuilder();

			if (updateapk == true) {

				picString = "{flag:2,interval_time:" + intervaltime
						+ ",location_interval:" + location_interval
						+ ",update_key:"+update_key
						+ ",update_app:\"" + apkname + "\",unlock_total_score:"
						+ pics[2] + "}";

				apkLog.setApkversion(apkversion);
				apkLog.setPhoneimei(phoneimei);
				apkLog.setPhonenumber(phonenum);
				apkLog.setPhonetype(phonetype);
				apkLog.setPhoneversion(phoneversion);
				apkLog.setUpdatetime(entertime);

				logDao.saveupdateapkinfo(apkLog);

			} else {

				sbBuilder.append("{");
				sbBuilder.append("flag:4,");
				sbBuilder.append("update_key:" + update_key + ",");
				sbBuilder.append("interval_time:" + intervaltime + ",").append(
						"location_interval:" + location_interval + ",");
				sbBuilder.append(pics[0]);
				sbBuilder.append(",");
				sbBuilder.append("unlock_total_score:" + pics[2]);
				sbBuilder.append("}");
				picString = sbBuilder.toString();

			}

			return picString;
		}

		if (!"".equals(pics[0]) && pics[0] != null) {
			// 需要更新图片需要更新apk
			if (updateapk == true) {
				StringBuilder sbBuilder = new StringBuilder();
				sbBuilder.append("{");
				sbBuilder.append("flag:3,");
				sbBuilder.append("interval_time:" + intervaltime + ",").append(
						"location_interval:" + location_interval + ",");
				sbBuilder.append("update_key:" + update_key + ",");
				sbBuilder.append(pics[0]).append(",");
				sbBuilder.append("update_app:").append("\"" + apkname + "\"");
				sbBuilder.append(",");
				sbBuilder.append("unlock_total_score:" + pics[2]);
				sbBuilder.append("}");

				picString = sbBuilder.toString();

				apkLog.setApkversion(apkversion);
				apkLog.setPhoneimei(phoneimei);
				apkLog.setPhonenumber(phonenum);
				apkLog.setPhonetype(phonetype);
				apkLog.setPhoneversion(phoneversion);
				apkLog.setUpdatetime(entertime);

				logDao.saveupdateapkinfo(apkLog);

				return picString;

			} else {
				// 需要更新图片不需要更新apk
				StringBuilder sbBuilder = new StringBuilder();
				sbBuilder.append("{");
				sbBuilder.append("flag:1,");
				sbBuilder.append("interval_time:" + intervaltime + ",").append(
						"location_interval:" + location_interval + ",");
				sbBuilder.append("update_key:" + update_key + ",");
				sbBuilder.append(pics[0]);
				sbBuilder.append(",");
				sbBuilder.append("unlock_total_score:" + pics[2]);
				sbBuilder.append("}");
				picString = sbBuilder.toString();
				return picString;

			}

		} else {
			// 不需要更新图片需要版本更新
			if (updateapk == true) {
				StringBuilder sbBuilder = new StringBuilder();
				sbBuilder.append("{");
				sbBuilder.append("flag:2,");
				sbBuilder.append("interval_time:" + intervaltime + ",").append(
						"location_interval:" + location_interval + ",");
				sbBuilder.append("update_key:" + update_key + ",");
				sbBuilder.append("update_app:").append("\"" + apkname + "\"");
				sbBuilder.append(",");
				sbBuilder.append("unlock_total_score:" + pics[2]);
				sbBuilder.append("}");
				picString = sbBuilder.toString();

				apkLog.setApkversion(apkversion);
				apkLog.setPhoneimei(phoneimei);
				apkLog.setPhonenumber(phonenum);
				apkLog.setPhonetype(phonetype);
				apkLog.setPhoneversion(phoneversion);
				apkLog.setUpdatetime(entertime);

				logDao.saveupdateapkinfo(apkLog);

				return picString;
			} else {
				// 不需要更新图片不需要版本更新
				StringBuilder sbBuilder = new StringBuilder();
				sbBuilder.append("{");
				sbBuilder.append("flag:0,");
				sbBuilder.append("interval_time:" + intervaltime).append(
						",location_interval:" + location_interval + ",");
				sbBuilder.append("update_key:" + update_key + ",");
				sbBuilder.append("unlock_total_score:" + pics[2]);
				sbBuilder.append("}");
				picString = sbBuilder.toString();
				return picString;

			}

		}
	}

	/**
	 * apk版本更新
	 */
	public void updateapk(HttpServletRequest request,
			HttpServletResponse response, String savepath) {
		ApkInfo apkInfo = new ApkInfo();
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {

					String name = item.getFieldName();
					String value = item.getString("utf-8");
					BeanUtils.setProperty(apkInfo, name, value);

				} else {
					String filename = item.getName();

					String savefilename = filename;// 得到保存在硬盘的文件名

					InputStream in = item.getInputStream();
					FileOutputStream out = new FileOutputStream(savepath + "\\"
							+ savefilename);
					int len = 0;
					byte buffer[] = new byte[1024];
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					in.close();
					out.close();
					item.delete();

				}

			}

			dao.updateapk(apkInfo);

		} catch (Exception e) {

			throw new RuntimeException();
		}
	}

	/**
	 * @return
	 */
	public int getApkTotal() {
		return dao.getApkTotal();
	}

	/**
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<ApkInfo> getapkinfo(int currentPage, int pageSize) {
		return dao.getapkinfo(currentPage, pageSize);
	}

	public void appSettingConfirm(String imei, String setting) {
		if (setting.equals("0") || setting.equals("1"))
			dao.appSettingConfirm(imei, setting);
		dao.InsertThmChgLogByClient(imei, setting);
	}

	/**
	 * @param newlockinfo
	 *            保存滑屏信息
	 */
	public String saveslideinfo(String json) {

		String phoneimsi = null;
		String phonenumber = null;
		String phoneimei = null;
		String opentime = null;

		int count = 0;

		try {

			JSONObject jsonObject = new JSONObject(json);
			try {
				phonenumber = jsonObject.getString("phone_num");
			} catch (Exception e) {
				phonenumber = null;
			}

			phoneimei = jsonObject.getString("phone_imei");

			RegisterDao registerDao = new RegisterDao();

			phonenumber = registerDao.getnumbyimei(phoneimei);

			try {
				count = jsonObject.getInt("record_count");
			} catch (Exception e1) {

			}

			phoneimsi = jsonObject.getString("phone_imsi");

			JSONArray pics_name = jsonObject.getJSONArray("pics_name");

			int timecount = 0;
			for (int j = 0; j < pics_name.length(); j++) {
				String pic_name = pics_name.getString(j);
				JSONArray unlocktimes = jsonObject.getJSONArray(pic_name);

				timecount += unlocktimes.length();

			}
			// 如果数量不相等则返回错误信息要求重传
			if (timecount != count) {
				return "{success:0}";
			}

			// 查看设备行为信息记录表是否存在如果不存在则创建
			SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
			Date d1 = new Date();
			String tablename = format.format(d1);
			tablename = "tab_slideinfo_" + tablename;
			boolean isexisttab = dao.isexisttab(tablename);

			// 如果不存在则 创建
			if (!isexisttab) {
				int id = dao.getpretabmaxid();
				boolean createsuccess = dao.createtable(tablename, id);
				if (!createsuccess) {
					return "{success:0}";
				}

			}
			// 保存滑屏记录
			List<SlideModel> slideModels = new ArrayList<SlideModel>();
			for (int j = 0; j < pics_name.length(); j++) {
				String pic_name = pics_name.getString(j);
				JSONArray unlocktimes = jsonObject.getJSONArray(pic_name);
				for (int k = 0; k < unlocktimes.length(); k++) {
					JSONObject jsObject = unlocktimes.getJSONObject(k);
					Iterator<String> keys = jsObject.keys();
					while (keys.hasNext()) {
						String key = keys.next();
						opentime = jsObject.getString(key);
						SlideModel slideModel = new SlideModel();
						slideModel.setImei(phoneimei);
						slideModel.setMbno(phonenumber);
						slideModel.setPicname(pic_name);
						slideModel.setSlidetime(opentime);
						slideModels.add(slideModel);
					}
				}
			}
			dao.SaveOpenTime(slideModels);

		} catch (Exception e) {

			e.printStackTrace();
			return "{success:0}";

		}

		return "{success:1}";
	}

	/**
	 * @param imei
	 * @return
	 */
	public String getUnitno(String imei) {
		// TODO Auto-generated method stub
		return dao.getUnitno(imei);
	}

}
