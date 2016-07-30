package cn.ncut.autotask.domain;

/**
 * @author wzq
 *
 *version 1.0 2014-12-12 下午10:29:03
 */
public class SIMinfo {
    private int id;
	private String mobilenum;
	private String mobilearea;
	private String  mobiletype;
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
	 * @return the mobilenum
	 */
	public String getMobilenum() {
		return mobilenum;
	}
	/**
	 * @param mobilenum the mobilenum to set
	 */
	public void setMobilenum(String mobilenum) {
		this.mobilenum = mobilenum;
	}
	/**
	 * @return the mobilearea
	 */
	public String getMobilearea() {
		return mobilearea;
	}
	/**
	 * @param mobilearea the mobilearea to set
	 */
	public void setMobilearea(String mobilearea) {
		this.mobilearea = mobilearea;
	}
	/**
	 * @return the mobiletype
	 */
	public String getMobiletype() {
		return mobiletype;
	}
	/**
	 * @param mobiletype the mobiletype to set
	 */
	public void setMobiletype(String mobiletype) {
		this.mobiletype = mobiletype;
	}
	/**
	 * @return the areacode
	 */
	public String getAreacode() {
		return areacode;
	}
	/**
	 * @param areacode the areacode to set
	 */
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	private String areacode;
	
}
