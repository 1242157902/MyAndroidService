package cn.ncut.deviceservice;

import lombok.extern.slf4j.Slf4j;
import cn.ncut.devicedao.RegisterDao;

/**
 * @author wzq
 *
 *version 1.0 2014-12-13 上午10:48:40
 */
@Slf4j
public class RegisterService {
	RegisterDao dao=new RegisterDao();
	
	/**
	 * @param imei
	 * @param comno
	 * @param shopno
	 * @param empno
	 * @return 
	 */
	public boolean addempinfo(String imei, String comno, String shopno,
			String empno) {
		return dao.addempinfo(imei,comno,shopno,empno);
	}
	/**
	 * @param phoneimei
	 * @param comno
	 * @return
	 */
	public boolean updateunit(String phoneimei, String comno) {
		// TODO Auto-generated method stub
		return dao.updateunit(phoneimei,comno);
	}
	/**
	 * @param phoneimei
	 * @param comno
	 * @param shopno
	 * @param empno
	 * @return
	 */
	public boolean isexist(String phoneimei, String comno, String shopno,
			String empno) {
		return dao.isexist(phoneimei,comno,shopno,empno);
	}
	/**
	 * @param phoneimei
	 * @param comno
	 * @return
	 */
	public boolean isexist(String phoneimei, String comno) {
		// TODO Auto-generated method stub
		return dao.isexist(phoneimei, comno);
	}
	/**
	 * @param imei
	 * @param age
	 * @param sex
	 * @param phonenum
	 * @return
	 * 保存用户注册信息
	 */
	public boolean register(String imei, int age, String sex, String phonenum,String nickname) {
		
		return dao.register( imei, age,  sex, phonenum,nickname);
	}
	/**
	 * @param imei
	 * @param phonenum
	 * @param appnames
	 */
	public void saveappnames(String imei, String phonenum, String appnames) {
		dao.saveappnames(imei,phonenum,appnames);
		
	}



}
