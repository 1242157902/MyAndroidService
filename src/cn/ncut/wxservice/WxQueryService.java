package cn.ncut.wxservice;

import java.util.Map;

import cn.ncut.wxdao.WxQueryDao;

/**
 * @author wzq
 * 
 *version 1.0 2014-12-2 下午8:16:12
 */
public class WxQueryService {

	WxQueryDao dao = new WxQueryDao();

	/**
	 * @param num
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String, String> countbyday(String num, String year, String month) {
		return dao.countbyday(num, year, month);

	}

}
