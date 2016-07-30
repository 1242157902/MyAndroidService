package cn.ncut.pushdomain;
public class Employee {
	private String emp_no;//数据库编号
	private String emp_id;//工号
	private String emp_name;//姓名
	private String com_name;//所属单位
	private String depart_name;//所属部门
	private String emp_duty;//职位
	private String emp_sale;//业绩
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public String getDepart_name() {
		return depart_name;
	}
	public void setDepart_name(String depart_name) {
		this.depart_name = depart_name;
	}
	public String getEmp_duty() {
		return emp_duty;
	}
	public void setEmp_duty(String emp_duty) {
		this.emp_duty = emp_duty;
	}
	public String getEmp_sale() {
		return emp_sale;
	}
	public void setEmp_sale(String emp_sale) {
		this.emp_sale = emp_sale;
	}
}
