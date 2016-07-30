package cn.ncut.devicedomain;

/**
 * @author wzq
 *
 *version 1.0 2015-8-8 下午3:53:20
 */
public class UserClickTime {
	
	private String imei;
	private String phonenum;
	private String url;
	private String clicktime;
	/**
	 * @return the imei
	 */
	public String getImei() {
		return imei;
	}
	/**
	 * @param imei the imei to set
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}
	/**
	 * @param imei
	 * @param phonenum
	 * @param url
	 * @param clicktime
	 */
	public UserClickTime(String imei, String phonenum, String url,
			String clicktime) {
		super();
		this.imei = imei;
		this.phonenum = phonenum;
		this.url = url;
		this.clicktime = clicktime;
	}
	/**
	 * 
	 */
	public UserClickTime() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the phonenum
	 */
	public String getPhonenum() {
		return phonenum;
	}
	/**
	 * @param phonenum the phonenum to set
	 */
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the clicktime
	 */
	public String getClicktime() {
		return clicktime;
	}
	/**
	 * @param clicktime the clicktime to set
	 */
	public void setClicktime(String clicktime) {
		this.clicktime = clicktime;
	}

}
