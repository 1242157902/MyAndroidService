package cn.ncut.pushdomain;

public class PushItem {
	private int pid;
	private String title;
	private int queid;
	private int ptype;
	private String pushtime;
	private int priori;
	private String enddate;
	private String oper;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getQueid() {
		return queid;
	}
	public void setQueid(int queid) {
		this.queid = queid;
	}
	public int getPtype() {
		return ptype;
	}
	public void setPtype(int ptype) {
		this.ptype = ptype;
	}
	public String getPushtime() {
		return pushtime;
	}
	public void setPushtime(String pushtime) {
		this.pushtime = pushtime;
	}
	public int getPriori() {
		return priori;
	}
	public void setPriori(int priori) {
		this.priori = priori;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	
}
