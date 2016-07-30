package cn.ncut.autotask.countProfile;

/**
 * 
 * <p>Title：        DayClassifys<p>
 * <p>Description:  每天用户所有浏览的所有标签<p>
 * @date:           2016年3月7日下午4:43:33的
 * @author:         ysl
 * @version         1.0
 */
public class DayClassifys {

	private int id ;
	private String phoneNumber ;
	private String date;
	private String classifys;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getClassifys() {
		return classifys;
	}
	public void setClassifys(String classifys) {
		this.classifys = classifys;
	}
	
	
}
