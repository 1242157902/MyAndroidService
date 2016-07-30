package cn.ncut.pushservice;

import java.util.List;

import cn.ncut.pushdao.IconDao;
import cn.ncut.pushdomain.IconInfo;

/**
 * @author wzq
 *
 *version 1.0 2015-4-3 下午3:35:11
 */
public class IconService {

	/**
	 * @param currentPage
	 * @param pageSize
	 * @param comno
	 * @return
	 */
	
	IconDao dao=new IconDao();
	
	public List<IconInfo> getlist(int currentPage, int pageSize, String comno) {
		// TODO Auto-generated method stub
		return dao.getlist(currentPage,pageSize,comno);
	}

	/**
	 * @param comno
	 * @return
	 */
	public int getTotal(String comno) {
		// TODO Auto-generated method stub
		return dao.getTotal(comno);
	}

	/**
	 * @param parseInt
	 */
	public void delete(int id) {
	      dao.delete(id);
		
	}

	/**
	 * @param info
	 */
	public void add(IconInfo info) {
	dao.add(info);
		
	}

	/**
	 * @param info
	 */
	public void update(IconInfo info) {
		dao.update(info);
		
	}

}
