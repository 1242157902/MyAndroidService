package cn.ncut.pushdomain;

/**
 * @author wzq
 *
 *version 1.0 2015-7-26 下午6:10:27
 */
public class AppFlow {

	private String appname;
	private int flow;
	private String imei;
	private String phonenumber;
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
	 * @return the phonenumber
	 */
	public String getPhonenumber() {
		return phonenumber;
	}
	/**
	 * @param phonenumber the phonenumber to set
	 */
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	/**
	 * @return the appname
	 */
	public String getAppname() {
		return appname;
	}
	/**
	 * @param appname the appname to set
	 */
	public void setAppname(String appname) {
		this.appname = appname;
	}
	/**
	 * @return the flow
	 */
	public int getFlow() {
		return flow;
	}
	/**
	 * @param flow the flow to set
	 */
	public void setFlow(int flow) {
		this.flow = flow;
	}
	
}
