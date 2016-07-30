package cn.ncut.wxdomain;

/**
 * 用户每天查看图片和点击链接  bean
 * @author baiyang
 *
 */
public class Prefer {

	private String device_number;
	private String date;
	private String classify;
	private int scan_num;
	private int click_num;
	public String getDevice_number() {
		return device_number;
	}
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	} 
	
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public int getScan_num() {
		return scan_num;
	}
	public void setScan_num(int scan_num) {
		this.scan_num = scan_num;
	}
	public int getClick_num() {
		return click_num;
	}
	public void setClick_num(int click_num) {
		this.click_num = click_num;
	}
	
	
}
