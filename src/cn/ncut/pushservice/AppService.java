package cn.ncut.pushservice;
import java.io.File;
import java.util.List;

import cn.ncut.pushdao.AppDao;
import cn.ncut.pushdomain.Appstore;
import cn.ncut.pushdomain.ClientAppstore;
import cn.ncut.pushdomain.ComBox;
import cn.ncut.pushdomain.PushContent;


public class AppService {
	AppDao dao=new AppDao();	
	public void AddApp(Appstore app) {
		 dao.AddApp(app);
    }
	public void EditApp(Appstore app) {
		if(app.getApp_size()==0)  dao.EditApp(app);
		else {
			ComBox files=new ComBox();
			files=dao.GetApkFileIcon(app.getApp_id());
		    File iconfile = new File(files.getId());   
		    if (iconfile.isFile() && iconfile.exists())   iconfile.delete();  
		    File apkfile = new File(files.getName()); 
		    if (apkfile.isFile() && apkfile.exists())   apkfile.delete();  
			dao.EditAppFile(app);
		}
	}
	public List<Appstore> GetAppList(int currentPage, int pageSize,String operId) {
		return dao.GetAppList(currentPage, pageSize,operId);
	}

	public int GetAppTotal(String operId) {
		return dao.GetAppTotal(operId);
	}
	public void DeleteApp(String appid,String icon,String apk) {
	    dao.DeleteApp(appid);		
	    File iconfile = new File(icon);   
	    if (iconfile.isFile() && iconfile.exists())   iconfile.delete();  
	    File apkfile = new File(apk); 
	    if (apkfile.isFile() && apkfile.exists())   apkfile.delete();  
	}
	public List<ClientAppstore> GetClientAppList(int appid, String keyword) {
		return dao.GetClientAppList(appid,keyword);
	}
	public void CommitDownum(String apkid) {
		dao.CommitDownum(apkid);
	}


}
