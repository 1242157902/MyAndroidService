package cn.ncut.pushdomain;

/**
 * @author wzq
 *
 *version 1.0 2015-4-3 下午3:24:12
 */
public class IconInfo {
	
	private int id;
	private String title;
	private String iconname;
	private String picurl;
	private String manager;
	private String updatetime;
	private String icon_class;
	private String icon_className;
	
	public String getIcon_class() {
		return icon_class;
	}
	public void setIcon_class(String icon_class) {
		this.icon_class = icon_class;
	}
	public String getIcon_className() {
		return icon_className;
	}
	public void setIcon_className(String icon_className) {
		this.icon_className = icon_className;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param id
	 * @param title
	 * @param iconname
	 * @param picurl
	 * @param manager
	 * @param updatetime
	 */
	public IconInfo(int id, String title, String iconname, String picurl,
			String manager, String updatetime) {
		super();
		this.id = id;
		this.title = title;
		this.iconname = iconname;
		this.picurl = picurl;
		this.manager = manager;
		this.updatetime = updatetime;
	}
	/**
	 * @return the iconname
	 */
	public String getIconname() {
		return iconname;
	}
	/**
	 * @param iconname the iconname to set
	 */
	public void setIconname(String iconname) {
		this.iconname = iconname;
	}
	/**
	 * 
	 */
	public IconInfo() {
		super();
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the picurl
	 */
	public String getPicurl() {
		return picurl;
	}
	/**
	 * @param picurl the picurl to set
	 */
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	/**
	 * @return the manager
	 */
	public String getManager() {
		return manager;
	}
	/**
	 * @param manager the manager to set
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}
	/**
	 * @return the updatetime
	 */
	public String getUpdatetime() {
		return updatetime;
	}
	/**
	 * @param updatetime the updatetime to set
	 */
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

}
