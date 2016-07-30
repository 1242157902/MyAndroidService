package cn.ncut.pushservice;
import java.util.ArrayList;
import java.util.List;

import cn.ncut.pushdao.PhDao;
import cn.ncut.pushdao.QueDao;
import cn.ncut.pushdomain.AutoPushLog;
import cn.ncut.pushdomain.ContentClass;
import cn.ncut.pushdomain.PushAtom;
import cn.ncut.pushdomain.PushQue;
import cn.ncut.pushdomain.ComBox;

public class QueService {
	
	QueDao dao = new QueDao();
	PhDao phdao=new PhDao();

	public List<PushQue> GetQueList(int currentPage, int pageSize,String operId) {
		 List<PushQue>  pushQueue=dao.GetQueList(currentPage,pageSize, operId);
		 for(PushQue pq : pushQueue){ 
			 String queInfo="";
			 String newQue="";
			 int length=pq.getQue().length()-1;
			 String[] que=pq.getQue().substring(0,length).split(",");
			 for(int i=0;i<que.length;i++)
			 {
				 String x=dao.getFileNamex(Integer.parseInt(que[i]));
				 String y="<IMG width=60px height=80px  src=images/pics/"+x+" /> ";
			     newQue+=y;
			     queInfo+=dao.getQueAtom(Integer.parseInt(que[i]))+"*"+x+",";
			 }
			 pq.setOper(queInfo.substring(0,queInfo.length()-1));
             pq.setQue(newQue);
	        }
		 return pushQueue;
	}

	public int GetQueTotal(String operId) {
		return dao.GetQueTotal(operId);
	}

	public void AddQueue(String title, String queue,String operId) {
		String[] que=queue.split(",");
        int n=que.length;
        List<PushAtom> atoms=new ArrayList<PushAtom>(n);
        for(int i=0;i<n;i++)
        {
        	String[] x=que[i].split("\\*");
        	PushAtom y = new PushAtom();
        	y.setCid(Integer.parseInt(x[0]));
        	y.setShowtime(Float.parseFloat(x[1]));
        	y.setEnddate(x[2]);
        	atoms.add(y);
        }
        String queinfo=dao.StoreAtoms(atoms);
        PushQue pq=new PushQue();
        pq.setTitle(title);
        pq.setQue(queinfo);
        pq.setOper(operId);        
        dao.InsertQueue(pq);
	}

	public void UpdateQueue(String qid, String title, String queue) {
		String[] que=queue.split(",");
        int n=que.length;
        List<PushAtom> atoms=new ArrayList<PushAtom>(n);
        for(int i=0;i<n;i++)
        {
        	String[] x=que[i].split("\\*");
        	PushAtom y = new PushAtom();
        	y.setAid(Integer.parseInt(x[0]));
        	y.setCid(Integer.parseInt(x[1]));
        	y.setShowtime(Float.parseFloat(x[2]));
        	y.setEnddate(x[3]);
        	atoms.add(y);
        }      
        String queinfo=dao.UpdateAtoms(atoms);
        PushQue pq=new PushQue();
        pq.setQid(Integer.parseInt(qid));
        pq.setTitle(title);
        pq.setQue(queinfo);       
        dao.UpdateQueue(pq);
        phdao.UpdateRecStatusQue(Integer.parseInt(qid));
	}

	public void SortQueue(String qid, String sortque) {
		dao.SortQueue(qid,sortque);
        phdao.UpdateRecStatusQue(Integer.parseInt(qid));
	}

	public String DeleteQueue(int qid) {
		String msg="删除成功";
		if(dao.CheckQueInPhlist(qid)==0) dao.DeleteQueue(qid);
		else msg="有推送项引用了该序列，不能删除！";
		return msg;
	}

	public void SetDefaultQueId(int com_no, int qid) {
		dao.SetDefaultQueId(com_no,qid);
	}
	
	public List<ContentClass> GetContClasslist(String id) {	
		 return dao.GetContClasslist(id);
	}
	public List<ContentClass> GetContClasslistX() {	
		 return dao.GetContClasslistX();
	}
	public void DeleteContClass(String code) {
		dao.DeleteContClass(code);		
	}

	public void EditContClass(ContentClass item) {   
        dao.EditContClass(item);
	}

	public void AddContClass(ContentClass item) {
		dao.AddContClass(item);
	}

	public void AddAutoPushLog(AutoPushLog item) {
		String temp="";
		String[] xx=item.getImeis().split(",");
		for(int i=0;i<xx.length;i++)
		{
          if(!xx[i].equals("")) temp=temp+",'"+xx[i]+"'";
		}
		if(!temp.equals("")) {item.setImeis(temp.substring(1));dao.AddAutoPushLog(item);	}	
	}

	public List<AutoPushLog> GetPushItemlist(int currentPage,int pageSize) {		
		return dao.GetPushItemlist(currentPage, pageSize);
	}

	public List<ComBox> GetPushLogUserList(int currentPage,int pageSize,String logid) {
		return dao.GetPushLogUserList(currentPage,pageSize,logid);
	}

	public int GetPushItemTotal() {
		return dao.GetPushItemTotal();
	}

	public int GetPushLogUserTotal(String logid) {
		return dao.GetPushLogUserTotal(logid);
	}

	public String GetWeiDuCode(String code) {
		return dao.GetWeiDuCode(code);
	}

}
