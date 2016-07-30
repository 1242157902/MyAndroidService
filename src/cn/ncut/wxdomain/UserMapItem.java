package cn.ncut.wxdomain;

/**
 * 用户每月每天轨迹
 * @author hjf
 *
 */
public class UserMapItem {

	private String phone_num;
	private String date;
	private String address;
	private int count;
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
