package com.bdyc.model;

import java.io.Serializable;

public class Resource implements Serializable{

	private Integer resourceId;	//资源ID
	private Integer parentId;	//父ID
	private String resourceName; //资源名称
	private String url;			//	资源路径
	private String iconCls;		//资源图片
	private Integer type;		//资源类型  1:菜单  0:按钮
	private Integer orederno;	//排序
	
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getOrederno() {
		return orederno;
	}
	public void setOrederno(Integer orederno) {
		this.orederno = orederno;
	}
	public Resource(Integer resourceId, Integer parentId, String resourceName,
			String url, String iconCls, Integer type, Integer orederno) {
		super();
		this.resourceId = resourceId;
		this.parentId = parentId;
		this.resourceName = resourceName;
		this.url = url;
		this.iconCls = iconCls;
		this.type = type;
		this.orederno = orederno;
	}
	public Resource() {
		super();
	}
	@Override
	public String toString() {
		return "Resource [resourceId=" + resourceId + ", parentId=" + parentId
				+ ", resourceName=" + resourceName + ", url=" + url
				+ ", iconCls=" + iconCls + ", type=" + type + ", orederno="
				+ orederno + "]";
	}
	
}
