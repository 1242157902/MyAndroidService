package cn.ncut.deviceservice;

import java.util.List;

import cn.ncut.devicedao.LogDao;
import cn.ncut.devicedomain.ApkLog;

/**
 * @author wzq
 *
 *version 1.0 2015-1-28 下午5:38:32
 */
public class LogService {

         LogDao dao=new LogDao();
	public List<ApkLog> getphoneupdateapkinfo(int currentPage, int pageSize,String phonenum,String imei) {
		return dao.getphoneupdateapkinfo(currentPage,pageSize,phonenum,imei);
	}
	/**
	 * @return
	 */
	public int getphoneApkupdateinfoTotal(String phonenum,String imei) {
		
		return dao.getphoneApkupdateinfoTotal(phonenum,imei);
	}

}
