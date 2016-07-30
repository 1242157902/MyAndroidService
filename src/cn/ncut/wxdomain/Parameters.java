package cn.ncut.wxdomain;

import java.util.Map;

/**
 * 
 * <p>Title：        Parameters<p>
 * <p>Description:  参数类：用于传递参数<p>
 * @date:           2016年3月12日下午6:59:22
 * @author:         ysl
 * @version         1.0
 */
public class Parameters {

	private String phone ;
	private String latitude;
	private String longitude ;
	private int hour;//推送过去之后，显示多长时间
	private String date;
	private Map<String,Object> map;//存储要查询的字段
	
	
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Parameters() {
		super();
	}
	public Parameters(String phone, String latitude, String longitude) {
		super();
		this.phone = phone;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	
}
