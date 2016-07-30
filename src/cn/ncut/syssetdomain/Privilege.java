package cn.ncut.syssetdomain;
/**
 * 
 * @author wzq
 *
 * version 1.0 2014-10-14 下午3:10:28
 */
public class Privilege {
private String id;
private String name;
private String description;
public String getId() {
	return id;
}
public Privilege(String name) {
	super();
	this.name = name;
}
public Privilege() {
	super();

}

/**
 * @param name2
 */

public void setId(String id) {
	this.id = id;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Privilege other = (Privilege) obj;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	return true;
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

	
	

}
