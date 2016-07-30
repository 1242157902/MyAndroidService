package cn.ncut.commanageservice;

import java.util.List;
import java.util.Map;

import cn.ncut.commanagedao.EmpDao;
import cn.ncut.commanagedomain.Emp;

/**
 * @author wzq
 *
 *version 1.0 2015-1-11 下午3:41:58
 */
public class EmpService {

	/**
	 * @param emp
	 */
	EmpDao dao=new EmpDao();
	
	public void save(Emp emp) {
      		dao.save(emp);
	}

	/**
	 * @param currentPage
	 * @param pageSize
	 * @param comno
	 * @return
	 */
	public List<Emp> findByPagination(int currentPage, int pageSize,
			Map<String, String> m,String comno) {
		return dao.findByPagination( currentPage,  pageSize, m, comno);
	}

	/**
	 * @param comno
	 * @return
	 */
	public int getTotal(Map<String, String> m,String comno) {
		
		return dao.getTotal(m,comno);
	}

	/**
	 * @param emp
	 */
	public void update(Emp emp) {
		dao.update(emp);
		
	}

	/**
	 * @param string
	 */
	public void zhuxiao(String id) {
		dao.zhuxiao(id);
		
	}

	/**
	 * @param emp
	 * @return
	 */
	public int query(Emp emp) {
		return dao.query(emp);
	}

	/**
	 * @param encoderContent
	 */
	public void updateemppicname(String encoderContent,int id) {
               dao.updateemppicname(encoderContent,id);		
	}

}
