package cn.ncut.devicedomain;

public class MobileModel {
	private String imei;//设备号
	private String mbno;//手机号
	private String company;//用户所属单位
	private String seller;//销售商
	private String gender;//用户性别
	private String birth;//用户年龄
	private String mbos;//设备操作系统号
	private String area;//归属地
	private String provider;//运营商
	private String mbtype;//设备类型
	private String seller_depid;//销售部门号
	private String seller_stuffid;//销售员工号
	private String imsi;//卡号
	private String regtime;//用户注册时间
	private String recque;//当前序列
	private int theme;//当前模式
	private int themestatus;//模式切换状态
	private String entertime;//首次进入系统时间
	private int update_key;//
	
	
	public String getEntertime() {
		return entertime;
	}


	public void setEntertime(String entertime) {
		this.entertime = entertime;
	}
	public int getTheme() {
		return theme;
	}
	
	public void setTheme(int theme) {
		this.theme = theme;
	}
	
	public int getThemestatus() {
		return themestatus;
	}
	
	public void setThemestatus(int themestatus) {
		this.themestatus = themestatus;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getMbno() {
		return mbno;
	}
	public void setMbno(String mbno) {
		this.mbno = mbno;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getMbos() {
		return mbos;
	}
	public void setMbos(String mbos) {
		this.mbos = mbos;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getMbtype() {
		return mbtype;
	}
	public void setMbtype(String mbtype) {
		this.mbtype = mbtype;
	}
	public String getSeller_depid() {
		return seller_depid;
	}
	public void setSeller_depid(String seller_depid) {
		this.seller_depid = seller_depid;
	}
	public String getSeller_stuffid() {
		return seller_stuffid;
	}
	public void setSeller_stuffid(String seller_stuffid) {
		this.seller_stuffid = seller_stuffid;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	public String getRecque() {
		return recque;
	}
	public void setRecque(String recque) {
		this.recque = recque;
	}


	public int getUpdate_key() {
		return update_key;
	}


	public void setUpdate_key(int update_key) {
		this.update_key = update_key;
	}
	
	
}