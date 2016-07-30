package cn.ncut.wxdomain;

import java.util.List;

/**
 * 
 * <p>Title：        LoctionClassify<p>
 * <p>Description: 地址标签实体类 <p>
 * @date:           2016年3月20日下午1:50:38
 * @author:         hjf
 * @version         1.0
 */
public class LoctionClassify {

	private String code;
	private String value;
	private String name;
	private int 	que;
	private int 	duration;
 
	private List<LoctionClassify> children;
	
 
	
	public List<LoctionClassify> getChildren() {
		return children;
	}
	public void setChildren(List<LoctionClassify> children) {
		this.children = children;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQue() {
		return que;
	}
	public void setQue(int que) {
		this.que = que;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
