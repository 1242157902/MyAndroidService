package cn.ncut.wxdomain;
/**
 * 时间bean
 * @author baiyang
 *
 */
public class HabitCount {

	private String device_number;
	private int dura_time;
	private int times;
	private String date;
	private int num;
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getDevice_number() {
		return device_number;
	}
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	public int getDura_time() {
		return dura_time;
	}
	public void setDura_time(int dura_time) {
		this.dura_time = dura_time;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
