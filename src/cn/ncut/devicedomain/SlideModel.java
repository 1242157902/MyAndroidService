package cn.ncut.devicedomain;

import java.sql.ResultSet;
import java.sql.SQLException;

import cn.ncut.devicedao.MobileDao;

public class SlideModel {
	private String id;
	private String imei;
	private String mbno;
	private String picname;
	private String pictitle;
	private String slidetime;
	private String company;
	private String seller;
	private String gender;
	private String birth;
	private String mbos;
	private String area;
	private String provider;
	private String mbtype;
	private String seller_depid;
	private String seller_stuffid;
	private String imsi;
	private String regtime;
	private int score;
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getPicname() {
		return picname;
	}
	public void setPicname(String picname) {
		this.picname = picname;
	}
	public String getPictitle() {
		return pictitle;
	}
	public void setPictitle(String pictitle) {
		this.pictitle = pictitle;
	}
	public String getSlidetime() {
		return slidetime;
	}
	public void setSlidetime(String slidetime) {
		this.slidetime = slidetime;
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
	
	

}