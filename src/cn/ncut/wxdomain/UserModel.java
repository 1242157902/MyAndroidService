package cn.ncut.wxdomain;

/**
 * 用户查询条件  用户基本信息
 * @author hjf
 *
 */
public class UserModel {

	private String device_number;
	private int sex;
	private int age;
	private String operator;
	private String area;
	public String getDevice_number() {
		return device_number;
	}
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	
}
