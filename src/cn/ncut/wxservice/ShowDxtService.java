package cn.ncut.wxservice;

 
import java.sql.SQLException;
import java.util.List;
 
import cn.ncut.wxdao.ShowDxtDao;
import cn.ncut.wxdomain.DXT; 

public class ShowDxtService {
	ShowDxtDao as=new ShowDxtDao(); 
	public List<DXT> getDxt(int score_limit,int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return as.getDxt(score_limit,currentPage,pageSize);
	}
	public int getDxtTotal(int score_limit) {
		// TODO Auto-generated method stub
		return as.getDxtTotal(score_limit);
	}
	public boolean savePrint(int score_limit, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return as.savePrint(score_limit,currentPage,pageSize);
	}
	public List<DXT> getWeekDxt(String start_date, String end_date) {
		// TODO Auto-generated method stub
		return as.getWeekDxt(start_date,end_date);
	}
	public int getDxtTotal(String start_date, String end_date) {
		// TODO Auto-generated method stub
		return as.getDxtTotal(start_date, end_date);
	}
	public List<DXT> getDxtPrinted(String start_date, String end_date,
			int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return as.getDxtPrinted(start_date, end_date,
				currentPage, pageSize);
	}
	public int getDxtPrintedTotal(String start_date, String end_date) {
		// TODO Auto-generated method stub
		return as.getDxtPrintedTotal(start_date,  end_date);
	} 
}
