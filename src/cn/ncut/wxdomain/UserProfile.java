package cn.ncut.wxdomain;

import java.util.List;

/**
 * 个体用户画像包括：基本信息、图片、APP、时间维度、地理位置
 * @author baiyang
 *
 */
public class UserProfile {
	private Basic basic;  //用户基本信息
	private List<Prefer> prefer;  //图片
	private List<App> app;  //APP 
	private List<HabitCount> habit;  //时间维度
	private List<UserMapItem> map;  //地理位置
	public Basic getBasic() {
		return basic;
	}
	public void setBasic(Basic basic) {
		this.basic = basic;
	}
	public List<Prefer> getPrefer() {
		return prefer;
	}
	public void setPrefer(List<Prefer> prefer) {
		this.prefer = prefer;
	}
	public List<App> getApp() {
		return app;
	}
	public void setApp(List<App> app) {
		this.app = app;
	}
	public List<HabitCount> getHabit() {
		return habit;
	}
	public void setHabit(List<HabitCount> habit) {
		this.habit = habit;
	}
	public List<UserMapItem> getMap() {
		return map;
	}
	public void setMap(List<UserMapItem> map) {
		this.map = map;
	}
	
	

}
