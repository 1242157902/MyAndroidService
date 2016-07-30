package cn.ncut.wxdomain;
/**
 * 用户使用APP的bean
 * @author baiyang
 *
 */
public class App {
	private String device_number;
	private String classify;
	private String app_name;
	private int nums;
	private String use_date;
	
	public String getUse_date() {
		return use_date;
	}
	public void setUse_date(String use_date) {
		this.use_date = use_date;
	}
	public String getDevice_number() {
		return device_number;
	}
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	
}
