package cn.ncut.wxdomain;

/**
 * 用户群实体
 * @author hjf
 *
 */
public class UsersPrefer {
	private String gender;
	private String age;
	private String prefer;//分类
	private float weight;//偏好对应的权重
	private String pushclassisy;//推送的类别
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPrefer() {
		return prefer;
	}
	public void setPrefer(String prefer) {
		this.prefer = prefer;
	}
	 
	public String getPushclassisy() {
		return pushclassisy;
	}
	public void setPushclassisy(String pushclassisy) {
		this.pushclassisy = pushclassisy;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}

}
