package cn.ncut.commanagedomain;
/**
 * 
 * @author wzq
 *
 *version 1.0 2015-1-9 上午11:54:15
 */
/**
 * @author wzq
 *
 *version 1.0 2015-1-9 下午5:23:07
 */
public class Org {
	private int id ;
	private String orgname ; 
	private String orglinkmanname ; 
	private String orglinkmanphonenum ; 		
	private String orglinkmanemail ;				
	private String description;		//描述
	private String orgstatus;	//状态
	private int parentsid;			//父id
	private String orgaddress;
	private String state = "open";	//treegrid 状态
	private String comno;
	
	/**
	 * @return the comno
	 */
	public String getComno() {
		return comno;
	}






	/**
	 * @param comno the comno to set
	 */
	public void setComno(String comno) {
		this.comno = comno;
	}


	private String orgarea;
	private String orgregion;
	
	private String orgidcard;

	
	/**
	 * @return the orgidcard
	 */
	public String getOrgidcard() {
		return orgidcard;
	}






	/**
	 * @param orgidcard the orgidcard to set
	 */
	public void setOrgidcard(String orgidcard) {
		this.orgidcard = orgidcard;
	}






	/**
	 * @return the orgarea
	 */
	public String getOrgarea() {
		return orgarea;
	}






	/**
	 * @param orgarea the orgarea to set
	 */
	public void setOrgarea(String orgarea) {
		this.orgarea = orgarea;
	}






	/**
	 * @return the orgregion
	 */
	public String getOrgregion() {
		return orgregion;
	}






	/**
	 * @param orgregion the orgregion to set
	 */
	public void setOrgregion(String orgregion) {
		this.orgregion = orgregion;
	}






	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}






	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}






	/**
	 * @param orgid
	 * @param orgname
	 * @param orglinkmanname
	 * @param orglinkmanphonenum
	 * @param orglinkmanemail
	 * @param description
	 * @param orgstatus
	 * @param parentsid
	 * @param orgaddress
	 */
	public Org(int id, String orgname, String orglinkmanname,
			String orglinkmanphonenum, String orglinkmanemail,
			String description, String orgstatus, int parentsid,
			String orgaddress) {
		super();
		this.id = id;
		this.orgname = orgname;
		this.orglinkmanname = orglinkmanname;
		this.orglinkmanphonenum = orglinkmanphonenum;
		this.orglinkmanemail = orglinkmanemail;
		this.description = description;
		this.orgstatus = orgstatus;
		this.parentsid = parentsid;
		this.orgaddress = orgaddress;
	}


	
	


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}






	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}






	/**
	 * @return the orgname
	 */
	public String getOrgname() {
		return orgname;
	}


	/**
	 * @param orgname the orgname to set
	 */
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}


	/**
	 * @return the orglinkmanname
	 */
	public String getOrglinkmanname() {
		return orglinkmanname;
	}


	/**
	 * @param orglinkmanname the orglinkmanname to set
	 */
	public void setOrglinkmanname(String orglinkmanname) {
		this.orglinkmanname = orglinkmanname;
	}


	/**
	 * @return the orglinkmanphonenum
	 */
	public String getOrglinkmanphonenum() {
		return orglinkmanphonenum;
	}


	/**
	 * @param orglinkmanphonenum the orglinkmanphonenum to set
	 */
	public void setOrglinkmanphonenum(String orglinkmanphonenum) {
		this.orglinkmanphonenum = orglinkmanphonenum;
	}


	/**
	 * @return the orglinkmanemail
	 */
	public String getOrglinkmanemail() {
		return orglinkmanemail;
	}


	/**
	 * @param orglinkmanemail the orglinkmanemail to set
	 */
	public void setOrglinkmanemail(String orglinkmanemail) {
		this.orglinkmanemail = orglinkmanemail;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the orgstatus
	 */
	public String getOrgstatus() {
		return orgstatus;
	}


	/**
	 * @param orgstatus the orgstatus to set
	 */
	public void setOrgstatus(String orgstatus) {
		this.orgstatus = orgstatus;
	}


	/**
	 * @return the parentsid
	 */
	public int getParentsid() {
		return parentsid;
	}


	/**
	 * @param parentsid the parentsid to set
	 */
	public void setParentsid(int parentsid) {
		this.parentsid = parentsid;
	}


	/**
	 * @return the orgaddress
	 */
	public String getOrgaddress() {
		return orgaddress;
	}


	/**
	 * @param orgaddress the orgaddress to set
	 */
	public void setOrgaddress(String orgaddress) {
		this.orgaddress = orgaddress;
	}


	public Org(){
		
		
		
	}
	

	
	

	
}
