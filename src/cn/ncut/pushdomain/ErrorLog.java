package cn.ncut.pushdomain;

public class ErrorLog {
	private int eid;
    private String errtype; 
	private String errtxt;
	private String errmethod;
	private String imei;
	private String inmsg;
	private String outmsg;
	private String errtime;
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
	public String getErrtype() {
		return errtype;
	}
	public void setErrtype(String errtype) {
		this.errtype = errtype;
	}
	public String getErrtxt() {
		return errtxt;
	}
	public void setErrtxt(String errtxt) {
		this.errtxt = errtxt;
	}
	public String getErrmethod() {
		return errmethod;
	}
	public void setErrmethod(String errmethod) {
		this.errmethod = errmethod;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getInmsg() {
		return inmsg;
	}
	public void setInmsg(String inmsg) {
		this.inmsg = inmsg;
	}
	public String getOutmsg() {
		return outmsg;
	}
	public void setOutmsg(String outmsg) {
		this.outmsg = outmsg;
	}
	public String getErrtime() {
		return errtime;
	}
	public void setErrtime(String errtime) {
		this.errtime = errtime;
	}
	
}
