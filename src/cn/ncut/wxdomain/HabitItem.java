package cn.ncut.wxdomain;

public class HabitItem {
	private String device_number;
	private String date;
	private String time;
	private int nums; 
	private int time_id;
	
	
	public int getTime_id() {
		return time_id;
	}
	public void setTime_id(int time_id) {
		this.time_id = time_id;
	}
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	} 
	
}
