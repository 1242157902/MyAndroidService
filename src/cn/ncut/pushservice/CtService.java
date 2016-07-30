package cn.ncut.pushservice;
import java.io.File;
import java.util.List;

import cn.ncut.pushdao.CtDao;
import cn.ncut.pushdao.PhDao;
import cn.ncut.pushdao.JsonDao;
import cn.ncut.pushdomain.PushContent;

public class CtService {
	
	CtDao dao = new CtDao();
	PhDao phdao=new PhDao();
	public List<PushContent> GetContentList(int currentPage, int pageSize,String operId) {
		return dao.GetContentList(currentPage, pageSize,operId);
	}

	public int GetCtTotal(String operId) {
		return dao.GetCtTotal(operId);
	}

	public void AddContent(PushContent newCt) {
		dao.AddContent(newCt);
	}

	public String DeleteContent(int cid,String savepath) {
		String msg="删除成功";
		if(dao.CheckCtInAtom(cid)==0){
		String filename=dao.GetContentName(cid);
		String orifilepath=savepath+ "\\"+filename;
		File  file = new File(orifilepath); 
		if (file.isFile() && file.exists()) file.delete();
		dao. DeleteContent(cid);
		}
		else msg="推送序列中引用了该内容，不能删除！";
		return msg;
	}

	public void UpdateContent(PushContent theCt) {
		dao.UpdateContent(theCt);
		phdao.UpdateRecStatusCt(theCt.getCid());
	}

}
