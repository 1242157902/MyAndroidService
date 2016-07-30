package cn.ncut.syssetdomain;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author wzq
 *
 * version 1.0 2014-10-14 下午3:10:48
 */
public class Role {
	private Set<Privilege> Privilege=new HashSet<Privilege>();
	private String id;
	private String name;
	private String privileges;
	
	

	public String getPrivileges() {
		return privileges;
	}
	
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}
	public Set<Privilege> getPrivilege() {
		return Privilege;
	}
	public void setPrivilege(Set<Privilege> privilege) {
		Privilege = privilege;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private String description;

}
