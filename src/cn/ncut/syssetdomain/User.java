package cn.ncut.syssetdomain;

import java.util.HashSet;
import java.util.Set;
/**
 * 
 * @author wzq
 *
 * version 1.0 2014-10-14 下午3:10:57
 */
public class User {
	private String id;
	private String username;
	private String password;
	private String comno;
	private String comname;
	private int isseller;
	private int userstatus;
	/**
	 * @return the isseller
	 */
	public int getIsseller() {
		return isseller;
	}
	/**
	 * @param isseller the isseller to set
	 */
	public void setIsseller(int isseller) {
		this.isseller = isseller;
	}
	/**
	 * @return the userstatus
	 */
	public int getUserstatus() {
		return userstatus;
	}
	/**
	 * @param userstatus the userstatus to set
	 */
	public void setUserstatus(int userstatus) {
		this.userstatus = userstatus;
	}
	/**
	 * @return the comname
	 */
	public String getComname() {
		return comname;
	}
	/**
	 * @param comname the comname to set
	 */
	public void setComname(String comname) {
		this.comname = comname;
	}
	/**
	 * @return the cmpno
	 */
	public String getComno() {
		return comno;
	}
	/**
	 * @param cmpno the cmpno to set
	 */
	public void setComno(String cmpno) {
		this.comno = cmpno;
	}
	private Set<Role>roles=new HashSet<Role>();
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
