package cn.ncut.syssetservice;

import java.util.ArrayList;
import java.util.List;

import cn.ncut.syssetdao.PrivilegeManagerDao;
import cn.ncut.syssetdao.RoleManagerDao;
import cn.ncut.syssetdomain.Privilege;
import cn.ncut.syssetdomain.Role;

/**
 * @author wzq
 * 
 *         version 1.0 2014-10-14 下午5:12:56
 */
public class RoleManagerService {

	RoleManagerDao dao = new RoleManagerDao();

	public List<Role> getAllRole() {

		return dao.getAllRole();
	}

	public void addrole(String name, String description) {

		dao.addrole(name, description);

	}

	public void deleterole(String id) {
		dao.deleterole(id);
	}

	public Role findrolebyid(String id) {
		return dao.findrolebyid(id);
	}

	public List<Privilege> getroleprivilege(String id) {
		return dao.getroleprivileges(id);
	}

	public void updateroleprivilege(String role_id, String[] privilege_ids) {

		List<Privilege> privileges = new ArrayList<Privilege>();

		PrivilegeManagerDao privilegeManagerDao = new PrivilegeManagerDao();

		
			try {
				for (int i = 0; i < privilege_ids.length && privilege_ids != null; i++) {
					Privilege privilege = privilegeManagerDao
							.getprivilegebyid(privilege_ids[i]);

					privileges.add(privilege);

				}
			} catch (Exception e) {
				
			}
		

		dao.updateroleprivilege(role_id, privileges);

	}

}
