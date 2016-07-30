package cn.ncut.devicedomain;

/**
 * @author wzq
 *GPS定位信息
 *version 1.0 2015-6-10 上午10:47:51
 */
public class Location {

	private Double longitude;
	private Double latitude;
	private String phonenum;
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	
	public String getImei() {
		return imei;
	}
	
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public String getLocationtime() {
		return locationtime;
	}
	
	public void setLocationtime(String locationtime) {
		this.locationtime = locationtime;
	}
	private String imei;
	private String locationtime;
	
	
}
