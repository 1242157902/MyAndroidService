package cn.ncut.syssetservice;

import java.util.ArrayList;
import java.util.List;

import cn.ncut.syssetdao.RoleManagerDao;
import cn.ncut.syssetdao.UserManagerDao;
import cn.ncut.syssetdomain.Role;
import cn.ncut.syssetdomain.User;

/**
 * @author wzq
 * 
 *         version 1.0 2014-10-14 下午3:32:35
 */
public class UserManagerService {
	UserManagerDao dao = new UserManagerDao();

	public List<User> getAllUser(String comno) {

		return dao.getAllUser(comno);
	}

	public void adduser( String username, String password,String cmpno) {
		dao.adduser(username, password,cmpno);
	}

	public void deleteuser(String id) {
		dao.deleteuser(id);
	}

	public User finduserbyid(String id) {
		return dao.finduserbyid(id);
	}

	public List<Role> getUserRoles(String id) {
		return dao.getUserRoles(id);
	}

	public void updateuserrole(String user_id, String[] role_ids) {

		List<Role> roles = new ArrayList<Role>();
		RoleManagerDao roleManagerDao = new RoleManagerDao();
		try {
			for (int i = 0; i < role_ids.length && role_ids != null; i++) {

				Role role = roleManagerDao.findrolebyid(role_ids[i]);
				roles.add(role);
			}
		} catch (Exception e) {

		}
		dao.updatesuser(user_id, roles);
	}

	
	public User finduser(String username, String password) {
		
		return dao.finduser(username,password);
	}

	public void updateuser(String id, String password) {
	          dao.updateuser(id,password);
		
	}

	/**
	 * @param comname
	 * @return
	 */
	public String addcompany(String comname) {
		
		return dao.addcompany(comname);
	}

	
}
