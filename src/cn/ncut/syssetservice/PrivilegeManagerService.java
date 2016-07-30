package cn.ncut.syssetservice;

import java.util.List;

import cn.ncut.syssetdao.PrivilegeManagerDao;
import cn.ncut.syssetdomain.Privilege;

/**
 * @author wzq
 *
 * version 1.0 2014-10-14 下午4:31:01
 */
public class PrivilegeManagerService {

	PrivilegeManagerDao dao=new PrivilegeManagerDao();
	public List<Privilege> getAllPrivilege() {
		return dao.getAllPrivilege();
	}
	
	public void addprivilege(String description, String name) {
		 dao.addprivilege(description,name);
	}

	
	public void deleteprivilege(String id) {
		dao.deleteprivilege(id);
	}

}
