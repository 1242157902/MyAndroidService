package cn.ncut.commanageservice;

import java.util.List;
import java.util.Map;

import cn.ncut.commanagedao.OrgDao;
import cn.ncut.commanagedomain.Org;
/**
 * @author wzq
 *
 *version 1.0 2015-1-9 上午11:49:28
 */
public class OrgService {

	private OrgDao dao= new OrgDao();
	public List<Org> findList(String id,String comno){
		return dao.findList(id,comno);
	}

	
	public void save(Org org){
		dao.save(org);
	}

	
	public Org findById(int id) {
		return dao.findById(id);
	}

	
	public void update(Org org) {
		dao.update(org);
	}

	
	public List<Org> getChildren(int id)  {
		return dao.getChildren(id);
	}

	
	public void delete(int cid,String comno) {
		dao.delete(cid,comno);
	}


	/**
	 * @param orgname
	 * @return
	 */
	public int getorgid(String orgname) {
		return dao.getorgid(orgname);
	}


	/**
	 * @param orgarea
	 * @return
	 */
	public List<String> getorgregions(String orgarea) {
		return dao.getorgregions(orgarea);
	}


	/**
	 * @param comno
	 * @return
	 */
	public List<String> getorgarea(String comno) {
		return dao.getorgarea(comno);
	}


	/**
	 * @param id
	 * @return
	 */
	public int getTotal(String comno,Map<String, String>m) {
		
		return dao.getTotal(comno,m);
	}


	/**
	 * @param id
	 * @param comno
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public List<Org> findList(String id, String comno, int currentPage,
			int pageSize,Map<String, String> m) {
		return dao.findList(id,comno,currentPage,pageSize,m);
	}

}
