package cn.ncut.syssetservice;

import cn.ncut.syssetdao.UrlManagerDao;
import cn.ncut.syssetdomain.UrlInfo;

public class UrlService {
	
	UrlManagerDao dao = new UrlManagerDao();
	
	public UrlInfo getUrlById(){
		
		return dao.getUrlById();
	
	}

	public void updateUrl(String oldurl, String newurl) {
		// TODO Auto-generated method stub
		dao.updateurl(oldurl, newurl);
	}
}
