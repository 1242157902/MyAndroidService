package cn.ncut.wxdomain;
/**
 * 用户的基本信息
 * @author hjf
 *
 */
public class Basic {

	private int sex;  //性别：男女 1男2 nv 
	private int age;  //年龄：青年、中年、老年   1 2 3 4 0未知
	private String operator;  //运营商：中国联通、移动、电信
	private String mobile;  //客户端：客户端类型
	private String area;  //地区分布
	private String device_number;//手机号
	
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	
}
